package com.opensell.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.opensell.entities.Customer;
import com.opensell.repository.LoginRepository;

@SpringBootTest(classes = {com.opensell.backend.services.LoginTest.class})
@TestInstance(Lifecycle.PER_CLASS)
public class LoginTest {

    @Autowired
    private LoginRepository rep;

    private Customer user;

    @BeforeAll
    void setup() {
        user = new Customer();
        Date date = new Date(System.currentTimeMillis());
        user.setJoinedDate(date);
        user.setUsername("John");
        user.setPersonalEmail("johndoe@gmail.com");
        user.setPwd("password");
        user.setIsDeleted(false);
        user.setIsVerified(false);
        user.setIsActivated(false);
        user.setLink("link");
        rep.save(user);
    }

    @AfterAll
    void clean() {
        rep.delete(rep.findCustomerByLink("link"));
    }

    @Test
    public void testExistingUser() {
        //assertEquals(1, rep.checkLogin("John", "password"));
    }
}
