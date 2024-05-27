package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.security.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    User getUserByResetToken(String token);
}
