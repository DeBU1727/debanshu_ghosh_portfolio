package com.example.demo.config;

import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void run(String... args) throws Exception {
        // Ensure Profile ID 1 exists on startup to prevent race conditions in controllers
        if (profileRepository.findById(1L).isEmpty()) {
            Profile profile = new Profile();
            profile.setId(1L);
            profile.setName("Admin");
            profile.setResumeUrl("");
            profileRepository.save(profile);
            System.out.println("DEBUG_INITIALIZER: Default profile created.");
        } else {
            System.out.println("DEBUG_INITIALIZER: Profile already exists.");
        }
    }
}
