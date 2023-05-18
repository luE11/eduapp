package pra.luis.eduapp.eduapp.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import pra.luis.eduapp.eduapp.auth.model.RefreshToken;
import pra.luis.eduapp.eduapp.auth.model.User;
import pra.luis.eduapp.eduapp.auth.repository.RefreshTokenRepository;
import pra.luis.eduapp.eduapp.utils.TokenRefreshException;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserService userService;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = user.getRefreshToken();
        if(refreshToken==null)
            refreshToken = new RefreshToken(generateToken(), new Date(System.currentTimeMillis()+refreshTokenDurationMs), user);
        return refreshTokenRepository.save(refreshToken);
    }

    public void verifyExpiration(RefreshToken token) throws TokenRefreshException {
        if (token.getExpirationDate().compareTo(new Date()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException("Refresh token was expired. Please make a new signin request");
        }
    }

    @Transactional
    public int deleteByUserId(int userId) {
        return refreshTokenRepository.deleteByUser(userService.findById(userId));
    }

    private String generateToken(){
        return UUID.randomUUID().toString();
    }
}
