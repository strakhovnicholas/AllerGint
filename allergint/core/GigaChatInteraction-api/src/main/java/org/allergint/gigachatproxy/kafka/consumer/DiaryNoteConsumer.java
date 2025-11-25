package org.allergint.gigachatproxy.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allergint.gigachatproxy.kafka.KafkaConfig;
import org.allergint.gigachatproxy.kafka.payload.AiDiaryNoteResponse;
import org.allergint.gigachatproxy.kafka.payload.DiaryNoteMessage;
import org.allergint.gigachatproxy.service.GigaChatService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryNoteConsumer {

    private final GigaChatService aiService;
    private final KafkaTemplate<String, AiDiaryNoteResponse> kafkaTemplate;

    @KafkaListener(topics = KafkaConfig.DIARY_NOTES_TOPIC, groupId = "diary-note-group")
    public void consume(DiaryNoteMessage message) {
        log.info("Received DiaryNoteMessage={}", message);

        StringBuilder prompt = new StringBuilder();
        prompt.append("На основе дневника пользователя:\n");
        prompt.append("Health state: ").append(message.getHealthState()).append("\n");

        if (message.getUserNotes() != null && !message.getUserNotes().isBlank()) {
            prompt.append("User notes: ").append(message.getUserNotes()).append("\n");
        }

        if (message.getUserSymptoms() != null && !message.getUserSymptoms().isEmpty()) {
            prompt.append("Symptoms:\n");
            message.getUserSymptoms().forEach(s ->
                    prompt.append("- ").append(s.getSymptomName()).append(" (").append(s.getSymptomState()).append(")\n")
            );
        }

        if (message.getWeathers() != null && !message.getWeathers().isEmpty()) {
            prompt.append("Weather:\n");
            message.getWeathers().forEach(w ->
                    prompt.append("- ").append(w.getWeatherCondition()).append(", temp: ").append(w.getTemperature()).append("\n")
            );
        }

        if (message.getMedicines() != null && !message.getMedicines().isEmpty()) {
            prompt.append("Medicines:\n");
            message.getMedicines().forEach(m ->
                    prompt.append("- ").append(m.getMedicineName()).append(" (").append(m.getDose()).append(" ").append(m.getDoseMeasureType()).append(")\n")
            );
        }

        prompt.append("\nОпредели возможные аллергические риски и дай рекомендации.\n");

        var aiResponse = aiService.sendPrompt(prompt.toString());

        AiDiaryNoteResponse response = new AiDiaryNoteResponse(
                message.getUserId(),
                message.getDiaryPageId(),
                aiResponse
        );

        kafkaTemplate.send(KafkaConfig.DIARY_NOTES_AI_RESPONSE_TOPIC,
                message.getDiaryPageId().toString(), response);

        log.info("Sent AI response to Kafka: {}", response);
    }
}
