package org.allergit.trigger;

import lombok.RequiredArgsConstructor;
import org.allergit.DiaryServiceApplication;
import org.allergit.diary.dto.AllergyTriggerDto;
import org.allergit.diary.enums.TriggerState;
import org.allergit.trigger.entity.AllergyTrigger;
import org.allergit.trigger.mapper.AllergyTriggerMapper;
import org.allergit.trigger.repository.AllergyTriggerRepository;
import org.allergit.trigger.service.AllergyTriggerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

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
class AllergyTriggerServiceTest {

    private final AllergyTriggerMapper mapper;
    private final AllergyTriggerRepository allergyTriggerRepository;
    private AllergyTriggerService allergyTriggerService;

    @BeforeEach
    void setUp() {
        allergyTriggerService = new AllergyTriggerService(allergyTriggerRepository, mapper);
    }

    @Test
    void testCreateAndGetById() {
        AllergyTriggerDto dto = new AllergyTriggerDto();
        dto.setTriggerName("Pollen");
        dto.setTriggerState(TriggerState.STRONG);

        AllergyTriggerDto saved = allergyTriggerService.create(dto);
        assertNotNull(saved.getId());

        Optional<AllergyTriggerDto> found = allergyTriggerService.getById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Pollen", found.get().getTriggerName());
        assertEquals(TriggerState.STRONG, found.get().getTriggerState());
    }

    @Test
    void testGetAll() {
        AllergyTriggerDto dto1 = new AllergyTriggerDto();
        dto1.setTriggerName("Dust");
        dto1.setTriggerState(TriggerState.STRONG);

        AllergyTriggerDto dto2 = new AllergyTriggerDto();
        dto2.setTriggerName("Mold");
        dto2.setTriggerState(TriggerState.WEAK);

        allergyTriggerService.create(dto1);
        allergyTriggerService.create(dto2);

        List<AllergyTriggerDto> all = allergyTriggerService.getAll();
        assertEquals(2, all.size());
    }

    @Test
    void testUpdate() {
        AllergyTrigger entity = new AllergyTrigger();
        entity.setTriggerName("Old Name");
        entity.setTriggerState(TriggerState.WEAK);
        entity = allergyTriggerRepository.save(entity);

        AllergyTriggerDto updateDto = new AllergyTriggerDto();
        updateDto.setTriggerName("New Name");
        updateDto.setTriggerState(TriggerState.STRONG);

        AllergyTriggerDto updated = allergyTriggerService.update(entity.getId(), updateDto);
        assertEquals("New Name", updated.getTriggerName());
        assertEquals(TriggerState.STRONG, updated.getTriggerState());
    }

    @Test
    void testDelete() {
        AllergyTrigger entity = new AllergyTrigger();
        entity.setTriggerName("Smoke");
        entity.setTriggerState(TriggerState.STRONG);
        entity = allergyTriggerRepository.save(entity);

        UUID id = entity.getId();
        allergyTriggerService.delete(id);

        assertFalse(allergyTriggerRepository.existsById(id));
    }

    @Test
    void testDeleteThrowsWhenNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> allergyTriggerService.delete(randomId));
    }

    @Test
    void testUpdateThrowsWhenNotFound() {
        UUID randomId = UUID.randomUUID();
        AllergyTriggerDto dto = new AllergyTriggerDto();
        dto.setTriggerName("Something");
        dto.setTriggerState(TriggerState.STRONG);

        assertThrows(IllegalArgumentException.class, () -> allergyTriggerService.update(randomId, dto));
    }
}

