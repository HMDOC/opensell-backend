package com.opensell.entities.dto.adCreation;

public record AdCreationData(int adTypeId,
                             int customerId,
                             double price,
                             int shape,
                             int visibility,
                             String title,
                             String description,
                             String address,
                             String reference,
                             int[] tagIds,
                             AdCreationImageData[] imageData) {

}
