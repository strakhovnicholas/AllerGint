package org.allergint.gigachatproxy.kafka;


import org.allergint.gigachatproxy.kafka.payload.AiDiaryNoteResponse;
import org.allergint.gigachatproxy.kafka.payload.DiaryNoteMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    public static final String DIARY_NOTES_TOPIC = "diary-notes";
    public static final String DIARY_NOTES_AI_RESPONSE_TOPIC = "diary-notes-ai-response";

    @Bean
    public ProducerFactory<String, DiaryNoteMessage> diaryNoteProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, DiaryNoteMessage> diaryNoteKafkaTemplate() {
        return new KafkaTemplate<>(diaryNoteProducerFactory());
    }

    @Bean
    public ProducerFactory<String, AiDiaryNoteResponse> aiResponseProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, AiDiaryNoteResponse> aiResponseKafkaTemplate() {
        return new KafkaTemplate<>(aiResponseProducerFactory());
    }

    @Bean
    public NewTopic diaryNotesTopic() {
        return new NewTopic(DIARY_NOTES_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic diaryNotesAiResponseTopic() {
        return new NewTopic(DIARY_NOTES_AI_RESPONSE_TOPIC, 1, (short) 1);
    }
}
