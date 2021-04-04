package com.castravet.market.dto.dto_converter;

import com.castravet.market.dto.UserDto;
import com.castravet.market.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDto entityToDto(User user){
        UserDto dto = new UserDto();

        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());

        return dto;
    }

    public List<UserDto> entityToDto(List<User> users){
        return users.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }

    public User dtoToEntity(UserDto userDto){
        User user = new User();

        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        return user;
    }

    public List<User> dtoToEntity(List<UserDto> usersDto){
        return usersDto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
    }
}
