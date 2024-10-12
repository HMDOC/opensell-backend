package com.opensell.controller;

import com.opensell.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileController {
    public final FileUploadService fileUploadService;

    @GetMapping("/ad-image/{fileName}")
    public ResponseEntity<?> getAdImage(@PathVariable String fileName) {
        return fileUploadService.readFileFromUri("/ad-image", fileName);
    }

    @GetMapping("/customer-profile/{fileName}")
    public ResponseEntity<?> getCustomerProfile(@PathVariable String fileName) {
        return fileUploadService.readFileFromUri("/customer-profile", fileName);
    }
}
