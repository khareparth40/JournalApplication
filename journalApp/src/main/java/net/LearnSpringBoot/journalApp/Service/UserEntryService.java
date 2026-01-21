package net.LearnSpringBoot.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import net.LearnSpringBoot.journalApp.Entity.User;
import net.LearnSpringBoot.journalApp.Repository.UserEntryRepo;
import net.LearnSpringBoot.journalApp.dto.UserDTO;
import net.LearnSpringBoot.journalApp.mapper.UserMapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserEntryService {

    private static final PasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    @Autowired
    public UserEntryRepo userEntryRepo;

    // SAME METHOD NAME
    public void savenewUser(UserDTO users) {
        try {
            User user = UserMapper.toEntity(users);

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));

            userEntryRepo.save(user);
        } catch (Exception e) {
            log.error("error occurred", e);
        }
    }

    // SAME METHOD NAME
    public void saveAdmin(UserDTO users) {
        User user = UserMapper.toEntity(users);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));

        userEntryRepo.save(user);
    }

    // SAME METHOD NAME
    public void saveuser(UserDTO users) {
        User user = UserMapper.toEntity(users);
        userEntryRepo.save(user);
    }

    // SAME METHOD NAME
    public List<UserDTO> getall() {
        return userEntryRepo.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // SAME METHOD NAME (ObjectId → String)
    public Optional<UserDTO> findbyId(String id) {
        return userEntryRepo.findById(new ObjectId(id))
                .map(UserMapper::toDTO);
    }

    // SAME METHOD NAME
    public void deleteById(String id) {
        userEntryRepo.deleteById(new ObjectId(id));
    }

    // SAME METHOD NAME
    public UserDTO findByUserName(String userName) {
        User user = userEntryRepo.findByUserName(userName);
        return user != null ? UserMapper.toDTO(user) : null;
    }
}

