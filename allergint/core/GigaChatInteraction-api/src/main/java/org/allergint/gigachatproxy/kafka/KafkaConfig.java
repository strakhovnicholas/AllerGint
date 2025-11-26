package org.allergint.gigachatproxy.kafka;

import org.allergit.diary.kafka.AiDiaryNoteResponse;
import org.allergit.diary.kafka.DiaryNoteMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    public static final String DIARY_NOTES_TOPIC = "diary-notes";
    public static final String DIARY_NOTES_AI_RESPONSE_TOPIC = "diary-notes-ai-response";

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> baseProducerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    @Bean
    public ProducerFactory<String, DiaryNoteMessage> diaryNoteProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseProducerProps());
    }

    @Bean
    public KafkaTemplate<String, DiaryNoteMessage> diaryNoteKafkaTemplate() {
        return new KafkaTemplate<>(diaryNoteProducerFactory());
    }

    @Bean
    public ProducerFactory<String, AiDiaryNoteResponse> aiResponseProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseProducerProps());
    }

    @Bean
    public KafkaTemplate<String, AiDiaryNoteResponse> aiResponseKafkaTemplate() {
        return new KafkaTemplate<>(aiResponseProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, DiaryNoteMessage> diaryNoteConsumerFactory() {
        JsonDeserializer<DiaryNoteMessage> deserializer =
                new JsonDeserializer<>(DiaryNoteMessage.class, false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "diary-note-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DiaryNoteMessage> diaryNoteKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DiaryNoteMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(diaryNoteConsumerFactory());
        return factory;
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


