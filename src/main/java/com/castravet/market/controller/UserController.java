package com.castravet.market.controller;


import com.castravet.market.dto.UserDto;
import com.castravet.market.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    @ApiOperation(value="find all Users From Db",
            response =List.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = List.class)})
    public List<UserDto> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value="find user by Id",
            notes = "id should be a positive Long",
            response =UserDto.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = UserDto.class)})
    public UserDto findUserById(@ApiParam(value="Id of user",required = true)
                                @PathVariable Long userId){
        return userService.findUserById(userId);
    }

//    @ApiOperation(value="Checks if Email already exists",
//            response = boolean)
//    @PostMapping("/users/checkEmail")
//    public boolean checkEmailAvailability(@RequestParam String email){
//        return userService.checkEmailAvailability(email);
//    }
//    @ApiOperation(value="Checks if Username already exists",
//
//            response = boolean)
//    @PostMapping("/users/checkUsername")
//    public boolean checkUsernameAvailability(@RequestParam String username){
//        return userService.checkUsernameAvailability(username);
//    }


    @DeleteMapping("/user/delete/{userId}")
    @ApiOperation(value="Delete user by Id",
            notes = "returns status 200 if succes and 406 if fails",
            response =ResponseEntity.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = ResponseEntity.class)})

    public ResponseEntity<Object> deleteUser(@ApiParam(value="Id of user",required = true)
                                             @PathVariable Long userId){
        return userService.deleteUser(userId);
    }
}
