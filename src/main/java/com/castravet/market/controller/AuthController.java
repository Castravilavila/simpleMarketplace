package com.castravet.market.controller;


import com.castravet.market.model.User;
import com.castravet.market.payload.LoginRequest;
import com.castravet.market.service.SecurityUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private SecurityUserService userService;

    @PostMapping("/signin")
    @ApiOperation(value="authenticate existing user with username or email and password",
            response = ResponseEntity.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "SUCCES",response = User.class)})
    public ResponseEntity<?> authenticateUser(@ApiParam(value="Login request object that contains" +
            "2 fields: emailOrUsername and password",required = true)
                                              @RequestBody LoginRequest loginRequest){
        return userService.authenticateUser(loginRequest);
        }

//     TODO Implement DTO
    @PostMapping("/signup")
    @ApiOperation(value="register inexisting user",
            response = ResponseEntity.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "SUCCES",response = User.class)})
    public ResponseEntity<?> registerUser(@ApiParam(value="User body with username, password and email",required = true)
                                          @RequestBody User user){
        return userService.registerUser(user);
    }
}
