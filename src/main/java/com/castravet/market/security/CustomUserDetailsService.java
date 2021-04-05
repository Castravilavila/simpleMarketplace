package com.castravet.market.security;

import com.castravet.market.exceptions.AppException;
import com.castravet.market.model.Role;
import com.castravet.market.model.User;
import com.castravet.market.payload.ApiResponse;
import com.castravet.market.payload.JwtAuthenticationResponse;
import com.castravet.market.payload.LoginRequest;
import com.castravet.market.repository.RoleRepository;
import com.castravet.market.repository.UserRepository;
import com.castravet.market.service.SecurityUserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService, SecurityUserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(
                ()->new UsernameNotFoundException("User not found with usernameOrEmail or email: " + usernameOrEmail)
        );

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found with the following id: "+id));
        return UserPrincipal.create(user);
    }

    @Override
    public ResponseEntity<?> authenticateUser( LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @Override
    public ResponseEntity<?> registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(()->new AppException("User role was not set"));
        user.setRoles(Collections.singleton(role));
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true,"User registered successfully"));
    }


}
