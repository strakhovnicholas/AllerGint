package org.allergit.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allergit.diarypage.repository.DiaryPageRepository;
import org.allergit.kafka.payload.AiDiaryNoteResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiDiaryNoteConsumer {

    private final DiaryPageRepository diaryRepository;

    @KafkaListener(topics = "diary-ai-responses", groupId = "ai-diary-group")
    public void consume(AiDiaryNoteResponse response) {
        log.info("Received AI DiaryNote response: {}", response);

        diaryRepository.findById(response.getDiaryPageId()).ifPresent(diary -> {
            diary.setAiNotes(response.getAiAnswer());
            diaryRepository.save(diary);
            log.info("Saved AI notes for DiaryPage id={}", diary.getId());
        });
    }
}

