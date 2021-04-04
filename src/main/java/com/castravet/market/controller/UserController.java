package com.castravet.market.controller;


import com.castravet.market.dto.UserDto;
import com.castravet.market.dto.dto_converter.UserConverter;
import com.castravet.market.service.UserService;
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
    public List<UserDto> findAll(){
        return userService.findAllUsers();
    }

    @GetMapping("/user/{userId}")
    public UserDto findUserById(@PathVariable Long userId){
        return userService.findUserById(userId);
    }

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

//    @PostMapping("user/update")
//    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto){
//
//    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }
}
