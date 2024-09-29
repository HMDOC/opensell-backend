package com.opensell.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opensell.ad.catalog.dto.AdViewDto;
import com.opensell.model.Ad;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class BackendApplicationTests {
    public static final String RESOURCES_PATH = "./src/test/resources";
    public static final ObjectMapper MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    private static <T> T readJsonFromFile(String filePath, Class<T> type) throws IOException {
        return MAPPER.readValue(new File(RESOURCES_PATH + filePath), type);
    }

    public static AdViewDto getAdViewDtoExample() throws IOException {
        return readJsonFromFile("/ad/AdViewDto.json", AdViewDto.class);
    }

    public static Ad getAdExample() throws IOException {
        return readJsonFromFile("/ad/Ad.json", Ad.class);
    }
}
