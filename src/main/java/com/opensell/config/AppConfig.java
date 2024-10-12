package com.opensell.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppConfig(List<String> allowedUrls, String serverUrl, String uploadPath, String supportEmail) {

}
