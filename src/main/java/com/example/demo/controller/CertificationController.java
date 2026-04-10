package com.example.demo.controller;

import com.example.demo.model.Certification;
import com.example.demo.repository.CertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
@CrossOrigin(origins = "*")
public class CertificationController {

    @Autowired
    private CertificationRepository certificationRepository;

    @GetMapping
    public List<Certification> getAllCertifications() {
        return certificationRepository.findAll();
    }

    @PostMapping
    public Certification createCertification(@RequestBody Certification certification) {
        return certificationRepository.save(certification);
    }

    @PutMapping("/{id}")
    public Certification updateCertification(@PathVariable Long id, @RequestBody Certification certificationDetails) {
        Certification certification = certificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Certification not found with id: " + id));
        
        certification.setTitle(certificationDetails.getTitle());
        certification.setOrganization(certificationDetails.getOrganization());
        certification.setIssueDate(certificationDetails.getIssueDate());
        certification.setCredentialUrl(certificationDetails.getCredentialUrl());
        
        return certificationRepository.save(certification);
    }

    @DeleteMapping("/{id}")
    public void deleteCertification(@PathVariable Long id) {
        certificationRepository.deleteById(id);
    }
}
