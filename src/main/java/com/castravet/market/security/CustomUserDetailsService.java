package com.castravet.market.security;

import com.castravet.market.model.User;
import com.castravet.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

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


}
