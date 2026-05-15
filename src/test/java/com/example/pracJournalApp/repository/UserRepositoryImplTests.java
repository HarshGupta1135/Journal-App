package com.example.pracJournalApp.repository;

import com.example.pracJournalApp.repo.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    public void testFindUser(){
        Assertions.assertNotNull(userRepository.getUserForSA());
    }

}
