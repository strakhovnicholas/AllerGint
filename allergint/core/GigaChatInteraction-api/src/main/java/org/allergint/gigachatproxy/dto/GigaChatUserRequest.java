package org.allergint.gigachatproxy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class GigaChatUserRequest {
    private UUID clientId;
    private String message;
}
