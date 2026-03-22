package com.example.pracJournalApp.repo;

import com.example.pracJournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,ObjectId> {

     User findByUsername(String username);

     void deleteByUsername(String name);
}
