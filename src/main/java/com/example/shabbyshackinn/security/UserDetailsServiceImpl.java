package com.example.shabbyshackinn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public List<UserDetails> getAllUsers() {
        Iterable<User> users = userRepo.findAll();

        return StreamSupport.stream(users.spliterator(), false)
                .map(ConcreteUserDetails::new)
                .collect(Collectors.toList());
    }

    public String addUser(String mail, String password, ArrayList<Role> roles) {
        try {
            loadUserByUsername(mail);
            return "User already exists";
        } catch (UsernameNotFoundException e) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode(password);
            User user = User.builder().enabled(true).password(hash).username(mail).roles(roles).build();
            userRepo.save(user);
            return "New user added";
        }
    }

    public String updateUser(String mail, String password, List<Role> roles) {
        try {
            User user = (User) loadUserByUsername(mail);

            if (password != null && !password.isEmpty()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String hash = encoder.encode(password);
                user.setPassword(hash);
            }
            if (roles != null && !roles.isEmpty()) {
                user.setRoles(roles);
            }
            userRepo.save(user);
            return "User updated successfully";
        } catch (UsernameNotFoundException e) {
            return "User not found";
        }
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
