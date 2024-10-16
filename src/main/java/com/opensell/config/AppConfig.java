package com.opensell.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@ConfigurationProperties(prefix = "app")
public record AppConfig(List<String> allowedUrls, String imageServerPath, String supportEmail) {
}
