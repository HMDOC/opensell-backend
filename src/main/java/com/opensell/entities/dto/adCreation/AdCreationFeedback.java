package com.opensell.entities.dto.adCreation;

import com.opensell.entities.verification.HtmlCode;

public record AdCreationFeedback(int code, int inserts, String errorMessage) {
}
