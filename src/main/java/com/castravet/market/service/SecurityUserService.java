package com.castravet.market.service;

import com.castravet.market.model.User;
import com.castravet.market.payload.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface SecurityUserService {
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(User user);
}
