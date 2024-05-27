package com.example.shabbyshackinn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user: " + username);
        }
        return new ConcreteUserDetails(user);
    }

    
    public User getUserByResetToken(String token) {
        return userRepo.findUserByResetToken(token);
    }
    
    public User getUserByEmail(String email) {
        return userRepo.findUserByUsername(email);
    }
    
    public void saveUserToken(User user){
        userRepo.save(user);
    }

    public void updatePassword(String token, String newPassword) {
        // Hämta användaren baserat på token
        User user = userRepo.findUserByResetToken(token);

        if (user != null) {
          
            String hashedPassword = passwordEncoder.encode(newPassword);

            
            user.setPassword(hashedPassword);
            user.setResetToken(null); 
            userRepo.save(user);
        } else {
           
            throw new RuntimeException("Kunde inte hitta användaren med token: " + token);
        }
    }
}
