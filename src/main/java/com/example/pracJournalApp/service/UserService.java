package com.example.pracJournalApp.service;

import com.example.pracJournalApp.entity.User;
import com.example.pracJournalApp.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean saveNewUser(User user) {
        try{
            user.setRoles(List.of("USER"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.error("Error occured for {} : ",user.getUsername(),e);
        }
        return false;
    }
    public void saveNewAdmin(User user) {
        user.setRoles(List.of("USER","ADMIN"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public Optional<User> getEntryById(ObjectId myid) {
        return userRepository.findById(myid);
    }

    public boolean deleteEntryById(ObjectId myid) {
        userRepository.deleteById(myid);
        return true;
    }

    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }
}
