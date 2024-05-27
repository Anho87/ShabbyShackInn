package com.example.shabbyshackinn.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user: " + username);
        }
        return new ConcreteUserDetails(user);
    }

    public List<User> getAllUsers(){
        return userRepo.findAll().forEach(user -> use);
    }

}
