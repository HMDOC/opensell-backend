package com.opensell.entities.dto;

import org.springframework.web.multipart.MultipartFile;

public record AdImgSave(boolean isPath, MultipartFile object) {

}
