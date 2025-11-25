package org.allergit.kafka.service;

import lombok.RequiredArgsConstructor;
import org.allergit.kafka.payload.DiaryNoteMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryNoteService {

    private final KafkaTemplate<String, DiaryNoteMessage> kafkaTemplate;

    public void sendNoteMessage(DiaryNoteMessage message) {
        kafkaTemplate.send("diary-notes", message.getDiaryPageId().toString(), message);
    }
}

