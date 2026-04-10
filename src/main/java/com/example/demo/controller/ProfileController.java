package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolio-profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private com.example.demo.service.CloudinaryService cloudinaryService;

    @PostMapping("/resume/upload")
    public Profile uploadResume(@RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            java.util.Map uploadResult = cloudinaryService.uploadResource(file, "resumes", "raw");
            String secureUrl = (String) uploadResult.get("secure_url");

            Profile profile = getProfile();
            profile.setResumeUrl(secureUrl);
            return profileRepository.save(profile);
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to upload resume", e);
        }
    }

    @GetMapping
    public Profile getProfile() {
        return profileRepository.findById(1L).orElseGet(() -> {
            Profile defaultProfile = new Profile();
            defaultProfile.setId(1L);
            defaultProfile.setName("Default");
            defaultProfile.setResumeUrl("");
            return profileRepository.save(defaultProfile);
        });
    }

    @PutMapping
    public Profile updateProfile(@RequestBody Profile profileDetails) {
        Profile profile = profileRepository.findById(1L).orElseGet(() -> {
            Profile newProfile = new Profile();
            newProfile.setId(1L);
            newProfile.setName("Default");
            return newProfile;
        });

        profile.setResumeUrl(profileDetails.getResumeUrl());
        return profileRepository.save(profile);
    }

    @GetMapping("/resume/download")
    public org.springframework.http.ResponseEntity<org.springframework.core.io.Resource> downloadResume() {
        return getResumeResponse("attachment");
    }

    @GetMapping("/resume/view")
    public org.springframework.http.ResponseEntity<org.springframework.core.io.Resource> viewResume() {
        return getResumeResponse("inline");
    }

    private org.springframework.http.ResponseEntity<org.springframework.core.io.Resource> getResumeResponse(
            String disposition) {
        try {
            Profile profile = getProfile();
            if (profile.getResumeUrl() == null || profile.getResumeUrl().isEmpty()) {
                return org.springframework.http.ResponseEntity.notFound().build();
            }

            java.net.URL url = new java.net.URL(profile.getResumeUrl());
            java.io.InputStream in = url.openStream();
            byte[] data = in.readAllBytes();
            in.close();

            org.springframework.core.io.ByteArrayResource resource = new org.springframework.core.io.ByteArrayResource(
                    data);

            return org.springframework.http.ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                            disposition + "; filename=\"Resume_Debanshu_Ghosh.pdf\"")
                    .body(resource);
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.internalServerError().build();
        }
    }
}
