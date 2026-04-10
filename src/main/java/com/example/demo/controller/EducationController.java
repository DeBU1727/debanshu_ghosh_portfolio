package com.example.demo.controller;

import com.example.demo.model.Education;
import com.example.demo.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
@CrossOrigin(origins = "*")
public class EducationController {

    @Autowired
    private EducationRepository educationRepository;

    @GetMapping
    public List<Education> getAllEducation() {
        return educationRepository.findAll();
    }

    @PostMapping
    public Education createEducation(@RequestBody Education education) {
        return educationRepository.save(education);
    }

    @PutMapping("/{id}")
    public Education updateEducation(@PathVariable Long id, @RequestBody Education educationDetails) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));

        education.setSchool(educationDetails.getSchool());
        education.setDegree(educationDetails.getDegree());
        education.setDuration(educationDetails.getDuration());
        education.setDetails(educationDetails.getDetails());

        return educationRepository.save(education);
    }

    @DeleteMapping("/{id}")
    public void deleteEducation(@PathVariable Long id) {
        educationRepository.deleteById(id);
    }
}
