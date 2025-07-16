package com.google.legal_sales_sidekick.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Entity
@Table(name = "user_sidekick")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_picture_link")
    private String profileLink;

    @Column(name = "about")
    private String about;

    @Column(name = "user_preference")
    private String userPreference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_persona_id")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_region_id")
    private Region region;

    @Column(name = "dark_mode")
    private Boolean darkMode;

    @Column(name = "google_drive_consent")
    private String googleDriveConsent;

    @Column(name = "access_requested_date")
    private LocalDateTime accessRequestedDate;

    @Column(name = "last_active_date")
    private LocalDateTime lastActiveDate;

    @Column(name = "onboarded_date")
    private LocalDateTime onboardedDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUserPreference() {
        return userPreference;
    }

    public void setUserPreference(String userPreference) {
        this.userPreference = userPreference;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Boolean getDarkMode() {
        return darkMode;
    }

    public void setDarkMode(Boolean darkMode) {
        this.darkMode = darkMode;
    }

    public String getGoogleDriveConsent() {
        return googleDriveConsent;
    }

    public void setGoogleDriveConsent(String googleDriveConsent) {
        this.googleDriveConsent = googleDriveConsent;
    }

    public LocalDateTime getAccessRequestedDate() {
        return accessRequestedDate;
    }

    public void setAccessRequestedDate(LocalDateTime accessRequestedDate) {
        this.accessRequestedDate = accessRequestedDate;
    }

    public LocalDateTime getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(LocalDateTime lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public LocalDateTime getOnboardedDate() {
        return onboardedDate;
    }

    public void setOnboardedDate(LocalDateTime onboardedDate) {
        this.onboardedDate = onboardedDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
