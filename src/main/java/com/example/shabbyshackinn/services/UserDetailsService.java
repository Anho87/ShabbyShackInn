package com.example.shabbyshackinn.services;

import org.springframework.security.core.userdetails.User;
//import com.example.shabbyshackinn.security.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    List<User> getAllUsers();

    User getUserByResetToken(String token);
}
