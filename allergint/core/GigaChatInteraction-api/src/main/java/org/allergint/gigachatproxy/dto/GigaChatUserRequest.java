package org.allergint.gigachatproxy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GigaChatUserRequest {
    private UUID clientId;
    private String message;
}
