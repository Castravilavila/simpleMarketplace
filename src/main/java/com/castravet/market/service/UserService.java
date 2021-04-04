package com.castravet.market.service;

import com.castravet.market.dto.UserDto;
import com.castravet.market.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();

    UserDto findUserById(Long id);

    ResponseEntity<Object> createUser(UserDto userDto);

//    ResponseEntity<Object> updateUser(UserDto userDto, Long id);

    ResponseEntity<Object> deleteUser(Long id);
}
