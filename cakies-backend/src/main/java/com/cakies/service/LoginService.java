package com.cakies.service;

import com.cakies.dto.LoginRequest;
import com.cakies.dto.LoginResponse;
import com.cakies.entity.User;
import com.cakies.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.cakies.security.JwtUtil;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                // Tạo JWT token (giả sử có JwtUtil)
                String token = JwtUtil.generateToken(user.getUsername());
                return new LoginResponse(token, "Đăng nhập thành công");
            }
        }
        return new LoginResponse(null, "Sai tên đăng nhập hoặc mật khẩu");
    }
}
