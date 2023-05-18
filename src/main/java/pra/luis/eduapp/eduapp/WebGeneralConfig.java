package pra.luis.eduapp.eduapp;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Argument resolver for injecting specification object as request query parameter
 */
@Configuration
@EnableJpaRepositories
public class WebGeneralConfig implements WebMvcConfigurer {

    /**
     * Argument resolver for injecting specification object as request query parameter
     * @param argumentResolvers initially an empty list
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
    }

    /**
     * Serve uploaded images from project root directory
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/files-upload/**")
                .addResourceLocations("file:files-upload/");
    }
    // TODO: commit and create refresh token on cookie https://www.bezkoder.com/spring-boot-refresh-token-jwt/#Refresh_Token_Request_and_Response
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // TODO: Test refresh token service
                .allowCredentials(true) // auth from cookie ... Used for refresh token cookie
                .allowedOrigins("http://localhost:4200");
    }
}
