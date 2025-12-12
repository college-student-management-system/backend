package site.studentManagement.backend.auth.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import site.studentManagement.backend.auth.repository.UserRepository;

import java.io.IOException;
import java.util.Collections;

public class JwtFilter extends OncePerRequestFilter {



    private final UserRepository userRepository;
    public JwtFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final String SECRET = "Ktu3kpZLuOuE3N8kElB2FHdCo2PJTgCL";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // Decode token WITHOUT validating expiry
            var decoded = JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token);

            String email = decoded.getSubject();
            String role = decoded.getClaim("role").asString();

            if (email != null) {
                var auth = new UsernamePasswordAuthenticationToken(
                        email, null,
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
