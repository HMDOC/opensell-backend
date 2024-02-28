package com.opensell.entities.dto;

import java.util.List;

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
        List<String> socials) {}
