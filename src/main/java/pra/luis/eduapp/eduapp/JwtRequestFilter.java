package pra.luis.eduapp.eduapp;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsServiceImpl jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtTokenUtil.validateJwtToken(jwt)) {
				String username = jwtTokenUtil.getUsernameFromToken(jwt);
				UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (ExpiredJwtException e) {
			logger.error("Cannot set user authentication.");
			resolver.resolveException(request, response, null, e);
		}

		if(!response.isCommitted())
			chain.doFilter(request, response);
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}

		return null;
	}

}