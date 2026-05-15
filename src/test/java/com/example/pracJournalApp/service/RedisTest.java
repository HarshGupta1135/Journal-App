package com.example.pracJournalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    public RedisTemplate redisTemplate;

    @Test
    public void testSendMail(){
        redisTemplate.opsForValue().set("email","harsh@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        int i = 1;
    }

}
