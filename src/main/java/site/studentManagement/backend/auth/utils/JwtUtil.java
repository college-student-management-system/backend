package site.studentManagement.backend.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import site.studentManagement.backend.auth.entity.User;

import java.util.Date;


public class JwtUtil {


    private final String SECRET = "Ktu3kpZLuOuE3N8kElB2FHdCo2PJTgCL";


    private final long EXPIRATION = 24 * 60 * 60 * 1000;

    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("role", user.getRole().name())
                .withClaim("userId", user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .sign(algorithm);
    }

    public String extractEmail(String token) {
        return JWT.decode(token).getSubject();
    }


}
