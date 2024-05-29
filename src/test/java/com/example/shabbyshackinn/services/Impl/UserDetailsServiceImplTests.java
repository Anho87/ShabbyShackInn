package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.security.Role;
import com.example.shabbyshackinn.security.RoleRepo;
import com.example.shabbyshackinn.security.UserDetailsServiceImpl;
import com.example.shabbyshackinn.security.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDetailsServiceImplTests {
    @Mock
    private UserRepo userRepo;
    @Mock
    private RoleRepo roleRepo;
    @InjectMocks
    UserDetailsServiceImpl sut;

//    Role roleReceptionist = Role.builder().name("Receptionist").build();
//    Role roleAdmin = Role.builder().name("Admin").build();
//    Collection<Role> user1roles = new ArrayList<>(Arrays.asList(roleAdmin,roleReceptionist));
//    User user1 = (User) User.builder().password("123").username("user1@mail.test").roles("Receptionist","Admin").build();
//
//    Collection<Role> user2roles = new ArrayList<>(Arrays.asList(roleAdmin));
//    UserDetails user2 = User.builder().password("123").username("user2@mail.test").roles(String.valueOf(user2roles)).build();
//
//    Collection<Role> user3roles = new ArrayList<>(Arrays.asList(roleReceptionist));
//    UserDetails user3 = User.builder().password("123").username("user3@mail.test").roles(String.valueOf(user3roles)).build();

    private User user1;
    private User user2;
    @BeforeEach
    void setUp() {
        Role roleReceptionist = Role.builder().name("Receptionist").build();
        Role roleAdmin = Role.builder().name("Admin").build();

        when(roleRepo.save(any(Role.class))).thenReturn(roleReceptionist).thenReturn(roleAdmin);

        roleRepo.save(roleReceptionist);
        roleRepo.save(roleAdmin);

        user1 = CreateUser("test1@test.test","Receptionist","123");
        user2 = CreateUser("test2@test.test","Admin","456");
    }
    private User CreateUser(String mail, String group, String password) {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName(group));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(password);
        return User.builder().password(hash).username(mail).roles(roles).build();
    }
    @Test
    void loadUserByUsername(){
        when(userRepo.getByUsername("user1@mail.test")).thenReturn(user1);

        Assertions.assertEquals(user1, sut.loadUserByUsername("user1@mail.test"));

    }

}

