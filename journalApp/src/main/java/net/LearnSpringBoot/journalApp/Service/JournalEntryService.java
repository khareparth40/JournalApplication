package net.LearnSpringBoot.journalApp.Service;

import net.LearnSpringBoot.journalApp.Entity.JournalEntry;
import net.LearnSpringBoot.journalApp.Entity.User;
import net.LearnSpringBoot.journalApp.Repository.JournalEntryRepo;
import net.LearnSpringBoot.journalApp.dto.UserDTO;
import net.LearnSpringBoot.journalApp.mapper.UserMapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    public JournalEntryRepo JournalEntryRepo;

    @Autowired
    private UserEntryService UserEntryService;

    // SAME METHOD NAME
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {

        // UserDTO instead of User
        UserDTO userDTO = UserEntryService.findByUserName(userName);

        // Convert DTO → Entity (ONLY for internal use)
        User user = UserMapper.toEntity(userDTO);

        JournalEntry saved = JournalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);

        // Save back using SAME service method
        UserEntryService.saveuser(UserMapper.toDTO(user));
    }

    // UNCHANGED
    public void saveEntry(JournalEntry journalEntry) {
        JournalEntryRepo.save(journalEntry);
    }

    // UNCHANGED
    public List<JournalEntry> getall() {
        return JournalEntryRepo.findAll();
    }

    // UNCHANGED (JournalEntry still uses ObjectId)
    public Optional<JournalEntry> findbyId(ObjectId id) {
        return JournalEntryRepo.findById(id);
    }

    // SAME METHOD NAME
    @Transactional
    public void deleteById(ObjectId id, String userName) {

        UserDTO userDTO = UserEntryService.findByUserName(userName);
        User user = UserMapper.toEntity(userDTO);

        boolean removed =
                user.getJournalEntries().removeIf(x -> x.getId().equals(id));

        if (removed) {
            UserEntryService.saveuser(UserMapper.toDTO(user));
            JournalEntryRepo.deleteById(id);
        }
    }
}

