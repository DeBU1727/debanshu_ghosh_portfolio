package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "testimonials")
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientName;

    private String clientRole;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String avatarUrl;

    private String company;

    private Integer rating;

    // Default Constructor
    public Testimonial() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getClientRole() { return clientRole; }
    public void setClientRole(String clientRole) { this.clientRole = clientRole; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
}
