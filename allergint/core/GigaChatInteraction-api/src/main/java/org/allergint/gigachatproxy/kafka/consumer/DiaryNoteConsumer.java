package org.allergint.gigachatproxy.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allergint.gigachatproxy.kafka.KafkaConfig;
import org.allergint.gigachatproxy.service.GigaChatService;
import org.allergit.diary.kafka.AiDiaryNoteResponse;
import org.allergit.diary.kafka.DiaryNoteMessage;
import org.allergit.feign.UserClient;
import org.allergit.user.dto.UserDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryNoteConsumer {
    private final GigaChatService aiService;
    private final KafkaTemplate<String, AiDiaryNoteResponse> kafkaTemplate;
    private final UserClient userClient;

    @KafkaListener(topics = KafkaConfig.DIARY_NOTES_TOPIC,
            groupId = "diary-note-group",
            containerFactory = "diaryNoteKafkaListenerFactory"
    )
    public void consume(DiaryNoteMessage message) {
        log.info("Received DiaryNoteMessage={}", message);

        UserDto user = userClient.getById(message.getUserId());

        StringBuilder prompt = new StringBuilder();
        prompt.append("Ты — опытный врач-аллерголог. Проанализируй дневник самочувствия пациента и составь краткий, полезный отчет.\n\n");

        prompt.append("ДАННЫЕ ИЗ ДНЕВНИКА:\n");
        prompt.append("• Общее состояние: ").append(message.getHealthState()).append("\n");

        if (message.getUserNotes() != null && !message.getUserNotes().isBlank()) {
            prompt.append("• Заметки пациента: ").append(message.getUserNotes()).append("\n");
        }

        if (message.getUserSymptoms() != null && !message.getUserSymptoms().isEmpty()) {
            prompt.append("• Симптомы:\n");
            message.getUserSymptoms().forEach(s ->
                    prompt.append("  - ").append(s.getSymptomName()).append(" (").append(s.getSymptomState()).append(")\n")
            );
        }

        if (message.getWeathers() != null && !message.getWeathers().isEmpty()) {
            prompt.append("• Погодные условия:\n");
            message.getWeathers().forEach(w ->
                    prompt.append("  - ").append(w.getWeatherCondition()).append(", температура: ").append(w.getTemperature()).append("°C\n")
            );
        }

        if (message.getMedicines() != null && !message.getMedicines().isEmpty()) {
            prompt.append("• Принятые лекарства:\n");
            message.getMedicines().forEach(m ->
                    prompt.append("  - ").append(m.getMedicineName()).append(" (").append(m.getDose()).append(" ").append(m.getDoseMeasureType()).append(")\n")
            );
        }

        if (user.getAllergens() != null && !user.getAllergens().isEmpty()) {
            prompt.append("• Аллергены пациента: ");
            user.getAllergens().forEach(allergen -> prompt.append(allergen).append(", "));
            // убираем последнюю запятую и пробел
            prompt.setLength(prompt.length() - 2);
            prompt.append("\n");
        }

        if (user.getSymptomPreferences() != null && !user.getSymptomPreferences().isEmpty()) {
            prompt.append("• Важные для пациента симптомы: ");
            user.getSymptomPreferences().forEach(symptom -> prompt.append(symptom).append(", "));
            prompt.setLength(prompt.length() - 2);
            prompt.append("\n");
        }


        prompt.append("""
                ЗАДАНИЕ:
                На основе этих данных:
                1. Выяви ВОЗМОЖНЫЕ причины ухудшения/улучшения состояния
                (например, связь симптомов с погодой, прием лекарства, реакция на аллергены).
                2. Оцени эффективность принятых лекарств (если они указаны).
                3. Дай 1 - 2 краткие, практические рекомендации на ближайшее время
                (например, 'при сохранении симптомов обратиться к врачу', 'избегать аллергенов').
                ТРЕБОВАНИЯ К ОТВЕТУ:
                Ответ предназначен для пациента, пиши простым и понятным языком.
                Будь конкретен и опирайся ТОЛЬКО на предоставленные данные.
                Не используй маркдаун, списки или символы. Только сплошной текст.
                Начни сразу с сути.
                Объем: 2 - 4 предложения. Максимум 2000 символов.\s""");

        String aiResponse = aiService.sendPrompt(prompt.toString());

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
