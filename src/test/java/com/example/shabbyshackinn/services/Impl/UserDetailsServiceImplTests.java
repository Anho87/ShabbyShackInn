package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.security.Role;
import com.example.shabbyshackinn.security.User;
import com.example.shabbyshackinn.security.UserDetailsServiceImpl;
import com.example.shabbyshackinn.security.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserDetailsServiceImplTests {
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    UserDetailsServiceImpl sut;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Role roleReceptionist = Role.builder().name("Receptionist").build();
        Role roleAdmin = Role.builder().name("Admin").build();

        user1 = mock(User.class);
        user2 = mock(User.class);

        Collection<Role> roles1 = new ArrayList<>(Arrays.asList(roleReceptionist, roleAdmin));
        Collection<Role> roles2 = new ArrayList<>(Arrays.asList(roleAdmin));

        when(user1.getId()).thenReturn(UUID.randomUUID());
        when(user1.getUsername()).thenReturn("user1@mail.test");
        when(user1.getPassword()).thenReturn(new BCryptPasswordEncoder().encode("123"));
        when(user1.isEnabled()).thenReturn(true);
        when(user1.getRoles()).thenReturn(roles1);

        when(user2.getId()).thenReturn(UUID.randomUUID());
        when(user2.getUsername()).thenReturn("user2@mail.test");
        when(user2.getPassword()).thenReturn(new BCryptPasswordEncoder().encode("456"));
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getRoles()).thenReturn(roles2);

        when(userRepo.getByUsername("user1@mail.test")).thenReturn(user1);
        when(userRepo.getByUsername("user2@mail.test")).thenReturn(user2);

        List<User> users = new ArrayList<>(Arrays.asList(user1, user2));
        when(userRepo.findAll()).thenReturn(users);

        when(userRepo.save(user1)).thenReturn(user1);

    }

    @Test
    void loadUserByUsername() {
        assertEquals(user1.getUsername(), sut.loadUserByUsername("user1@mail.test").getUsername());
        assertEquals(user1.getPassword(), sut.loadUserByUsername("user1@mail.test").getPassword());
        assertEquals(user1.getRoles().size(), sut.loadUserByUsername("user1@mail.test").getAuthorities().size());
    }

    @Test
    void getAllUsers() {
        List<User> expectedUsers = new ArrayList<>(Arrays.asList(user1, user2));

        assertEquals(expectedUsers.size(), sut.getAllUsers().size());
    }

    @Test
    void addUser() {
        String feedBackUserExistInDb = sut.addUser(user1.getUsername(), user1.getPassword(), (ArrayList<Role>) user1.getRoles());
        System.out.println("feedback" + feedBackUserExistInDb);
        String feedBackUserDoesNotExistInDb = sut.addUser("feedBackUserDoesNotExistInDb", "asd123", (ArrayList<Role>) user2.getRoles());
        System.out.println("feedback" + feedBackUserDoesNotExistInDb);

        assertTrue(feedBackUserDoesNotExistInDb.equalsIgnoreCase("New user added"));
        assertTrue(feedBackUserExistInDb.equalsIgnoreCase("User already exists"));
    }

    @Test
    void updateUser() {

        String feedBackUserExistInDb = sut.updateUser(user1.getUsername(), user1.getUsername(), user1.getPassword(), (ArrayList<Role>) user2.getRoles());
        System.out.println("feedback" + feedBackUserExistInDb);
        String feedBackUserDoesNotExistInDb = sut.updateUser("feedBackUserDoesNotExistInDb", "feedBackUserDoesNotExistInDb", "asd123", (ArrayList<Role>) user2.getRoles());
        System.out.println("feedback" + feedBackUserDoesNotExistInDb);
        assertTrue(feedBackUserDoesNotExistInDb.equalsIgnoreCase("User not found"));
        assertTrue(feedBackUserExistInDb.equalsIgnoreCase("User updated successfully"));
    }

    @Test
    void deleteUser() {
        when(userRepo.getByUsername("user1@mail.test")).thenReturn(user1);
        String feedback = sut.deleteUser("user1@mail.test");
        assertEquals("User deleted successfully", feedback);
        verify(userRepo, times(1)).delete(user1);
    }

    @Test
    void deleteUser_UserNotFound() {
        when(userRepo.getByUsername("nonexistent@mail.test")).thenReturn(null);
        String feedback = sut.deleteUser("nonexistent@mail.test");
        assertEquals("User not found", feedback);
        verify(userRepo, never()).delete(any(User.class));
    }

}

