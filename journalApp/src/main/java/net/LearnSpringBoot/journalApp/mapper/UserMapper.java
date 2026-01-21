package net.LearnSpringBoot.journalApp.mapper;

import net.LearnSpringBoot.journalApp.Entity.User;
import net.LearnSpringBoot.journalApp.dto.UserDTO;
import org.bson.types.ObjectId;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();

        // String → ObjectId
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            user.setId(new ObjectId(dto.getId()));
        }

        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());

        // IMPORTANT: correct boolean name
        user.setSentimentalAnalysis(dto.isSentimentAnalysis());

        return user;
    }

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();

        // ObjectId → String
        if (user.getId() != null) {
            dto.setId(user.getId().toHexString());
        }

        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());

        // IMPORTANT: correct boolean name
        dto.setSentimentAnalysis(user.isSentimentalAnalysis());


        return dto;
    }
}



