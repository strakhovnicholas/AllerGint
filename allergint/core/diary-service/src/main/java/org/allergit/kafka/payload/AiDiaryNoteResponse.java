package org.allergit.kafka.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AiDiaryNoteResponse {
    private UUID userId;
    private UUID diaryPageId;
    private String aiAnswer;
}
