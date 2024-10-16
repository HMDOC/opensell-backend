package com.opensell.files;

import com.opensell.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
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
