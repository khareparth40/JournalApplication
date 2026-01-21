package net.LearnSpringBoot.journalApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.LearnSpringBoot.journalApp.ApiResponse.weatherResponse;
import net.LearnSpringBoot.journalApp.Service.UserEntryService;
import net.LearnSpringBoot.journalApp.Service.WeatherApiService;
import net.LearnSpringBoot.journalApp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs")
public class UserEntryController {

    @Autowired
    private UserEntryService UserEntryService;

    @Autowired
    private weatherResponse weatherResponse;

    @Autowired
    private WeatherApiService weatherApiService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // DTO instead of Entity
        UserDTO userInDb = UserEntryService.findByUserName(userName);

        if (userInDb != null) {
            userInDb.setUserName(userDTO.getUserName());
            userInDb.setPassword(userDTO.getPassword());

            UserEntryService.savenewUser(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @GetMapping
    public ResponseEntity<?> greeting() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        weatherResponse weatherResponse =
                weatherApiService.getWeather("Mumbai");

        String greeting = "";

        if (weatherResponse != null) {
            greeting = " Weather feels like "
                    + weatherResponse.getCurrent().getFeelslike();
        }

        return new ResponseEntity<>(
                "Hi " + authentication.getName() + greeting,
                HttpStatus.OK
        );
    }
}
