package net.LearnSpringBoot.journalApp.Service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {


    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testemail(){
        redisTemplate.opsForValue().set("email","gmail@email.com");
        redisTemplate.opsForValue().get("email");
    }
}
