package com.opensell.entities.dto;

import java.util.List;

/**
 * Record for data meant to be used has placeholder in the modification page form
 * @author Olivier Mansuy
 */
public record CustomerModificationPagePlaceholder(
        String customerUsername,
        String customerFistName,
        String customerLastName,
        String customerExposedEmail,
        String customerPrimaryAddress,
        String customerBio,
        String customerIconPath,
        List<String> customerSocials) {}
