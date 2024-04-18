package com.opensell.entities.dto;

/**
 * Record for data meant to be used has placeholder in the modification page form
 * @author Olivier Mansuy
 */
public record CustomerModificationView(
        String username,
        String firstName,
        String lastName,
        String exposedEmail,
        String primaryAddress,
        String bio,
        String iconPath,
        String link1,
        String link2,
        String link3,
        String link4,
        String link5) {}
