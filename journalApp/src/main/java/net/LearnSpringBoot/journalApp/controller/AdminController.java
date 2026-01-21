package net.LearnSpringBoot.journalApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.LearnSpringBoot.journalApp.Cache.AppCache;
import net.LearnSpringBoot.journalApp.Service.UserEntryService;
import net.LearnSpringBoot.journalApp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
@Tag(name = "Admin APIs")
public class AdminController {

    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getall() {

        // DTO instead of Entity
        List<UserDTO> users = userEntryService.getall();

        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-newAdmin")
    public void createAdmin(@RequestBody UserDTO userDTO) {

        // Service already handles DTO → Entity
        userEntryService.saveAdmin(userDTO);
    }

    @GetMapping("clear-app-cache")
    public void clearappcache() {
        appCache.init();
    }
}

