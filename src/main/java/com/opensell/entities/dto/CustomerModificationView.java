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
        String bio,
        String iconPath) {}
