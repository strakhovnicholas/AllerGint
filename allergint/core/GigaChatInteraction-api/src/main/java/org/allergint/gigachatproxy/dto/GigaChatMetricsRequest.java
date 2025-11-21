package org.allergint.gigachatproxy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GigaChatMetricsRequest {
    private UUID clientId;
    private List<String> metrics;
}