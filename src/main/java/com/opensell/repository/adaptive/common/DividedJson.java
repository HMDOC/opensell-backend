package com.opensell.repository.adaptive.common;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DividedJson {
    private Map<String, Object> jpaJson;
    private Map<String, Object> filteredJson;
}
