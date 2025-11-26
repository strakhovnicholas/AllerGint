package org.allergit.diary.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiDiaryNoteResponse {
    private UUID userId;
    private UUID diaryPageId;
    private String aiAnswer;
}
