package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.PasswordResetToken;
import com.example.shabbyshackinn.repos.PasswordResetTokenRepo;
import com.example.shabbyshackinn.security.User;
import com.example.shabbyshackinn.services.UserDetailsService;
import com.example.shabbyshackinn.security.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordResetTokenRepo passwordTokenRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepo.save(myToken);
    }

    @Override
    public User findByUsername(final String email) {
        return userRepo.findByUsername(email);
    }
}
