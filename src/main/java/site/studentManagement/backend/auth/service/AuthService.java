package site.studentManagement.backend.auth.service;

import site.studentManagement.backend.auth.dto.LoginRequest;
import site.studentManagement.backend.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

}
