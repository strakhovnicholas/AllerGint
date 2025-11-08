package org.allergit.symptom;

import lombok.RequiredArgsConstructor;
import org.allergit.DiaryServiceApplication;
import org.allergit.diary.dto.UserSymptomDto;
import org.allergit.diary.enums.SymptomState;
import org.allergit.symptom.repository.UserSymptomRepository;
import org.allergit.symptom.service.UserSymptomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
        properties = {
                "spring.cloud.config.enabled=false",
                "spring.cloud.discovery.enabled=false",
                "eureka.client.enabled=false",
                "eureka.client.register-with-eureka=false",
                "eureka.client.fetch-registry=false"
        },
        classes = DiaryServiceApplication.class
)
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class UserSymptomServiceTest {
    @Autowired
    private UserSymptomService service;

    @Autowired
    private UserSymptomRepository repository;

    private UserSymptomDto dto;

    @BeforeEach
    void setUp() {
        dto = new UserSymptomDto();
        dto.setSymptomName("Headache");
        dto.setTimestamp(ZonedDateTime.now());
        dto.setSymptomState(SymptomState.MEDIUM);
    }

    @Test
    void testCreateAndGetById() {
        UserSymptomDto saved = service.create(dto);

        Optional<UserSymptomDto> found = service.getById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getSymptomName()).isEqualTo("Headache");
    }

    @Test
    void testGetAll() {
        service.create(dto);
        UserSymptomDto dto2 = new UserSymptomDto();
        dto2.setSymptomName("Fever");
        dto2.setTimestamp(ZonedDateTime.now());
        dto2.setSymptomState(SymptomState.MEDIUM);
        service.create(dto2);

        List<UserSymptomDto> all = service.getAll();
        assertThat(all).hasSize(2);
        assertThat(all).extracting("symptomName").containsExactlyInAnyOrder("Headache", "Fever");
    }

    @Test
    void testUpdate() {
        UserSymptomDto saved = service.create(dto);
        saved.setSymptomName("Migraine");
        saved.setSymptomState(SymptomState.MEDIUM);

        UserSymptomDto updated = service.update(saved.getId(), saved);
        assertThat(updated.getSymptomName()).isEqualTo("Migraine");
        assertThat(updated.getSymptomState()).isEqualTo(SymptomState.MEDIUM);
    }

    @Test
    void testUpdateNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> service.update(randomId, dto));
    }

    @Test
    void testDelete() {
        UserSymptomDto saved = service.create(dto);
        service.delete(saved.getId());

        Optional<UserSymptomDto> found = service.getById(saved.getId());
        assertThat(found).isEmpty();
    }

    @Test
    void testDeleteNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> service.delete(randomId));
    }
}
