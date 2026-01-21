package net.LearnSpringBoot.journalApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.LearnSpringBoot.journalApp.Entity.JournalEntry;
import net.LearnSpringBoot.journalApp.Entity.User;
import net.LearnSpringBoot.journalApp.Service.JournalEntryService;
import net.LearnSpringBoot.journalApp.Service.UserEntryService;
import net.LearnSpringBoot.journalApp.dto.UserDTO;
import net.LearnSpringBoot.journalApp.mapper.UserMapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs")
public class journalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserEntryService UserEntryService;

    @GetMapping
    public ResponseEntity<?> getAllEntriesByUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // DTO instead of Entity
        UserDTO userDTO = UserEntryService.findByUserName(userName);

        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Convert DTO → Entity only for access to journalEntries
        User user = UserMapper.toEntity(userDTO);

        List<JournalEntry> all = user.getJournalEntries();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry MyEntry) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            MyEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(MyEntry, userName);

            return new ResponseEntity<>(MyEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntrybyId(@PathVariable ObjectId myid) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserDTO userDTO = UserEntryService.findByUserName(userName);
        User user = UserMapper.toEntity(userDTO);

        List<JournalEntry> collect =
                user.getJournalEntries()
                        .stream()
                        .filter(x -> x.getId().equals(myid))
                        .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findbyId(myid);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        journalEntryService.deleteById(id, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry) {

        JournalEntry oldEntry = journalEntryService.findbyId(id).orElse(null);

        if (oldEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
            oldEntry.setTitle(newEntry.getTitle());
        }

        if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
            oldEntry.setContent(newEntry.getContent());
        }

        journalEntryService.saveEntry(oldEntry);
        return new ResponseEntity<>(oldEntry, HttpStatus.OK);
    }
}


