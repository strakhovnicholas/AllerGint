package org.allergit.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allergit.diary.kafka.AiDiaryNoteResponse;
import org.allergit.diarypage.repository.DiaryPageRepository;
import org.allergit.kafka.config.KafkaConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiDiaryNoteConsumer {

    private final DiaryPageRepository diaryRepository;

    @KafkaListener(topics = KafkaConfig.DIARY_NOTES_AI_RESPONSE_TOPIC,
            groupId = "ai-diary-group",
            containerFactory = "aiDiaryKafkaListenerFactory")
    @Transactional
    public void consume(AiDiaryNoteResponse response) {
        log.info("Received AI DiaryNote response for diaryPageId={}", response.getDiaryPageId());

        diaryRepository.findById(response.getDiaryPageId()).ifPresentOrElse(diary -> {
            String aiAnswer = response.getAiAnswer();
            if (aiAnswer != null && aiAnswer.length() > 2000) {
                aiAnswer = aiAnswer.substring(0, 2000);
            }
            diary.setAiNotes(aiAnswer);
            diaryRepository.save(diary);
            log.info("Saved AI notes for DiaryPage id={}", diary.getId());
        }, () -> log.warn("DiaryPage not found for id={}", response.getDiaryPageId()));

    }

}


