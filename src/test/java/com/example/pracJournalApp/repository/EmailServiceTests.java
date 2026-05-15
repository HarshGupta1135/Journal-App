package com.example.pracJournalApp.repository;

import com.example.pracJournalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail(){
        emailService.sendEmail("janurajput2005@gmail.com","Sending Mail Through Java","Hi, Harsh here!");
    }

}
