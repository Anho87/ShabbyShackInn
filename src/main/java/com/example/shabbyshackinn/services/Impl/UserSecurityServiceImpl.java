package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.PasswordResetToken;
import com.example.shabbyshackinn.repos.PasswordResetTokenRepo;
import com.example.shabbyshackinn.security.User;
import com.example.shabbyshackinn.security.UserRepo;
import com.example.shabbyshackinn.services.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Calendar;
import java.util.Optional;

@Service
@Transactional
public class UserSecurityServiceImpl implements UserSecurityService {

    @Autowired
    private PasswordResetTokenRepo passwordTokenRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepo.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(final String token) {
        System.out.println("getUserByPasswordResetToken");
        return Optional.ofNullable(passwordTokenRepo.findByToken(token) .getUser());
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        //user.setPassword(passwordEncoder.encode(password));
        System.out.println("changeUserPassword");
        user.setPassword(password);
        userRepo.save(user);
    }
}
