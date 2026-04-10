package com.example.demo.controller;

import com.example.demo.model.Experience;
import com.example.demo.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experience")
@CrossOrigin(origins = "*")
public class ExperienceController {

    @Autowired
    private ExperienceRepository experienceRepository;

    @GetMapping
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    @GetMapping("/count")
    public long getExperienceCount() {
        return experienceRepository.count();
    }

    @PostMapping
    public Experience createExperience(@RequestBody Experience experience) {
        return experienceRepository.save(experience);
    }

    @PutMapping("/{id}")
    public Experience updateExperience(@PathVariable Long id, @RequestBody Experience experienceDetails) {
        Experience experience = experienceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Experience not found with id: " + id));
        
        experience.setCompany(experienceDetails.getCompany());
        experience.setRole(experienceDetails.getRole());
        experience.setDetails(experienceDetails.getDetails());
        experience.setDuration(experienceDetails.getDuration());
        experience.setCertificateUrl(experienceDetails.getCertificateUrl());
        
        return experienceRepository.save(experience);
    }

    @DeleteMapping("/{id}")
    public void deleteExperience(@PathVariable Long id) {
        experienceRepository.deleteById(id);
    }
}
