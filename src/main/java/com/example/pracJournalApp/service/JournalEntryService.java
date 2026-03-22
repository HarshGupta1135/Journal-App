package com.example.pracJournalApp.service;

import com.example.pracJournalApp.entity.JournalEntry;
import com.example.pracJournalApp.entity.User;
import com.example.pracJournalApp.repo.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String username) {
        journalEntry.setDate(LocalDateTime.now());
        User user = userService.findByUserName(username);
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
        return saved;
    }
    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> getEntryById(ObjectId myid) {
        return journalEntryRepository.findById(myid);
    }

    @Transactional
    public boolean deleteEntryById(ObjectId myid, String username) {
        try {
            User user = userService.findByUserName(username);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myid));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(myid);
                return true;
            }
            return false;
        }catch (Exception e){
            log.error("Error",e);
            throw new RuntimeException("An error occured while deleting the entry",e);
        }
    }

}
