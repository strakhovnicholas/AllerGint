package org.allergint.gigachatproxy.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.allergit.diary.kafka.AiDiaryNoteResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiDiaryNoteProducer {

    private final KafkaTemplate<String, AiDiaryNoteResponse> kafkaTemplate;

    public void sendAiResponse(AiDiaryNoteResponse response) {
        kafkaTemplate.send("diary-notes-ai-response", response.getDiaryPageId().toString(), response);
    }
}
