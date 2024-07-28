package com.opensell.enums;

public enum FileType {
    AD_IMAGE("/ad-image/"),
    CUSTOMER_PROFILE("/customer-profile/");

    public final String folder;

    FileType(String folder) {
        this.folder = folder;
    }
}