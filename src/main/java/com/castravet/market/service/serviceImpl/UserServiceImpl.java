package com.castravet.market.service.serviceImpl;

import com.castravet.market.dto.UserDto;
import com.castravet.market.model.User;
import com.castravet.market.repository.UserRepository;
import com.castravet.market.dto.dto_converter.UserConverter;
import com.castravet.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    @Override
    public List<UserDto> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return userConverter.entityToDto(allUsers);

    }

    @Override
    public UserDto findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userConverter::entityToDto).orElse(null);
    }

    @Override
    public ResponseEntity<Object> createUser(UserDto userDto) {
        User user = userConverter.dtoToEntity(userDto);
        userRepository.save(user);

        Optional<User> sameUserButFromDb = userRepository.findByEmail(user.getEmail());
        if (sameUserButFromDb.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<Object> updateUser(UserDto userDto, Long id) {
//
//    }


    @Override
    public boolean checkEmailAvailability(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return !user.isPresent();
    }

    @Override
    public boolean checkUsernameAvailability(String username) {
        Optional<User> user = userRepository.getUserByUsername(username);
        return !user.isPresent();
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long id) {
        User user = userRepository.getOne(id);
        if (user==null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
