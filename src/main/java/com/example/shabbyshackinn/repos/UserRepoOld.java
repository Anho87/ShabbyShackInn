package com.example.shabbyshackinn.repos;

import com.example.shabbyshackinn.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepoOld extends JpaRepository<User, Long> {
//    User findByEmail(String email);
//
//    @Override
//    void delete(User user);

}