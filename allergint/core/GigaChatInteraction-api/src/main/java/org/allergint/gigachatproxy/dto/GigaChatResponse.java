package org.allergint.gigachatproxy.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GigaChatResponse {
    private UUID clientId;
    private String answer;
}
