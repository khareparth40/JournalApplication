package net.LearnSpringBoot.journalApp.Repository;

import net.LearnSpringBoot.journalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepo extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);

 void deleteByuserName(String userName);
}
