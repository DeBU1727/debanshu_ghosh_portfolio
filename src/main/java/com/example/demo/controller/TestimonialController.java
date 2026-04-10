package com.example.demo.controller;

import com.example.demo.model.Testimonial;
import com.example.demo.repository.TestimonialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
public class TestimonialController {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @GetMapping
    public List<Testimonial> getAllTestimonials() {
        return testimonialRepository.findAll();
    }

    @PostMapping
    public Testimonial createTestimonial(@RequestBody Testimonial testimonial) {
        return testimonialRepository.save(testimonial);
    }

    @PutMapping("/{id}")
    public Testimonial updateTestimonial(@PathVariable Long id, @RequestBody Testimonial testimonialDetails) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Testimonial not found with id: " + id));

        testimonial.setClientName(testimonialDetails.getClientName());
        testimonial.setClientRole(testimonialDetails.getClientRole());
        testimonial.setContent(testimonialDetails.getContent());
        testimonial.setAvatarUrl(testimonialDetails.getAvatarUrl());
        testimonial.setCompany(testimonialDetails.getCompany());
        testimonial.setRating(testimonialDetails.getRating());

        return testimonialRepository.save(testimonial);
    }

    @DeleteMapping("/{id}")
    public void deleteTestimonial(@PathVariable Long id) {
        testimonialRepository.deleteById(id);
    }
}
