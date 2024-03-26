package com.opensell.entities.dto.adCreation;

import com.opensell.entities.verification.HtmlCode;

public record AdCreationFeedback(int code, int result, String errorMessage) {
}
