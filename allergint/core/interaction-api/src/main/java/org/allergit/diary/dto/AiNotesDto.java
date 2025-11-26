package org.allergit.diary.dto;

import java.util.UUID;

public record AiNotesDto(
        UUID diaryPageId,
        String aiNotes
) {
}
