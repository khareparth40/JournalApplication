package net.LearnSpringBoot.journalApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.LearnSpringBoot.journalApp.Service.UserDetailServiceImpl;
import net.LearnSpringBoot.journalApp.Service.UserEntryService;
import net.LearnSpringBoot.journalApp.Utilis.JWTUtilis;
import net.LearnSpringBoot.journalApp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs")
public class PublicController {

    @Autowired
    private UserEntryService UserEntryService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtilis jwtUtilis;

    @PostMapping("/sign-up")
    public void signup(@RequestBody UserDTO userDTO) {

        // DTO passed to service
        UserEntryService.savenewUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUserName(),
                            userDTO.getPassword()
                    )
            );

            UserDetails userDetails =
                    userDetailService.loadUserByUsername(userDTO.getUserName());

            String jwt = jwtUtilis.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception occurred during Authentication", e);
            return new ResponseEntity<>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/health-check")
    public String HealthCheck() {
        return "ok";
    }
}

