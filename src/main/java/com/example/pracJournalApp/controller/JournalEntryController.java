package com.example.pracJournalApp.controller;

import com.example.pracJournalApp.entity.JournalEntry;
import com.example.pracJournalApp.entity.User;
import com.example.pracJournalApp.service.JournalEntryService;
import com.example.pracJournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            User user = userService.findByUserName(name);
            List<JournalEntry> journalEntries = user.getJournalEntries();
            if (journalEntries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            JournalEntry createdEntry = journalEntryService.saveEntry(journalEntry, name);
            return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myid) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            User user = userService.findByUserName(name);
            List<JournalEntry> collect = user.getJournalEntries()
                    .stream()
                    .filter(x -> x.getId().equals(myid))
                    .toList();
            if (!collect.isEmpty()) {
                return new ResponseEntity<>(collect.getFirst(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myid) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            boolean isDeleted = journalEntryService.deleteEntryById(myid, name);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("id/{myid}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable ObjectId myid,
                                                        @RequestBody JournalEntry journalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userService.findByUserName(name);
        List<JournalEntry> collect = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myid))
                .toList();
        if (!collect.isEmpty()) {
            JournalEntry myEntry = collect.getFirst();
            myEntry.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : myEntry.getTitle());
            myEntry.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : myEntry.getContent());
            journalEntryService.saveEntry(myEntry);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}