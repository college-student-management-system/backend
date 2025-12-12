package site.studentManagement.backend.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.StandardClaimAccessor;
import org.springframework.stereotype.Service;
import site.studentManagement.backend.auth.dto.LoginRequest;
import site.studentManagement.backend.auth.dto.LoginResponse;
import site.studentManagement.backend.auth.entity.User;
import site.studentManagement.backend.auth.repository.UserRepository;
import site.studentManagement.backend.auth.service.AuthService;
import site.studentManagement.backend.auth.utils.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService  {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private JwtUtil jwtUtil ;



    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));


        if (user.getPassword() == null) {
            throw new RuntimeException("Please set your password first.");
        }


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user);

        return new LoginResponse(token, user.getRole().name());
    }


}
