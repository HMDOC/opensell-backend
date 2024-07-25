package com.opensell.service.customermodification;

@Deprecated(forRemoval = true)
public record ModificationFeedback (
        Integer code,
        Integer result,
        String value) {}
