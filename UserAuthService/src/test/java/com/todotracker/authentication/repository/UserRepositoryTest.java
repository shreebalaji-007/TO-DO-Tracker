package com.todotracker.authentication.repository;

import com.todotracker.authentication.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        user = new User("123L", "U1234", "JOHN123");
    }
    @AfterEach
    public void tearDown() throws Exception {
        user = null;
    }

    @Test
    public void testSaveUserSuccess() {
        userRepository.save(user);
        User object = userRepository.findById(user.getUserId().toString()).get();
        assertEquals(user.getUserId(), object.getUserId());
    }

    @Test
    public void testLoginUserSuccess() {
        userRepository.save(user);
        User object = userRepository.findById(user.getUserId().toString()).get();
        assertEquals(user.getUserId(), object.getUserId());
    }
}