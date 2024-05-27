package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.security.User;

import java.util.Optional;

public interface UserSecurityService {
    String validatePasswordResetToken(String token);
    Optional<User> getUserByPasswordResetToken(String token);
    void changeUserPassword(User user, String password);



}
