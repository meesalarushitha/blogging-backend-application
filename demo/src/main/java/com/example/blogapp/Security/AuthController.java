package com.example.blogapp.Security;

import com.example.blogapp.Dto.JwtAuthRequest;
import com.example.blogapp.Dto.JwtAuthResponse;
import com.example.blogapp.Security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(
            @RequestBody JwtAuthRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        String token =
                jwtTokenHelper.generateToken(
                        authentication.getName()
                );

        JwtAuthResponse response =
                new JwtAuthResponse();

        response.setToken(token);
        response.setUsername(authentication.getName());

        return ResponseEntity.ok(response);
    }
}