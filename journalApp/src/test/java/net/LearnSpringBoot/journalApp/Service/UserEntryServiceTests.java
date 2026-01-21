package net.LearnSpringBoot.journalApp.Service;

import net.LearnSpringBoot.journalApp.Repository.UserEntryRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class UserEntryServiceTests {
    @Autowired
    private UserEntryRepo userEntryRepo;

    @Test
    public void findByUserName(){
assertNotNull(userEntryRepo.findByUserName("ram"));
    }
}
