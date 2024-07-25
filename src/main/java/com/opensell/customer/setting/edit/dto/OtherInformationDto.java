package com.opensell.customer.setting.edit.dto;

import jakarta.validation.constraints.NotNull;

public record OtherInformationDto(
    @NotNull String username,
    String firstName,
    String lastName,
    String bio
) {
}
