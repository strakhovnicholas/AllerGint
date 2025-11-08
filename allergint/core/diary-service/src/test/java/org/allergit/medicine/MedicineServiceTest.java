package org.allergit.medicine;

import lombok.RequiredArgsConstructor;
import org.allergit.DiaryServiceApplication;
import org.allergit.diary.dto.MedicineDto;
import org.allergit.diary.enums.DoseMeasureType;
import org.allergit.diary.enums.MedicineType;
import org.allergit.medicine.repository.MedicineRepository;
import org.allergit.medicine.service.MedicineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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
class MedicineServiceTest {

    @Autowired
    private MedicineService service;

    @Autowired
    private MedicineRepository repository;

    private MedicineDto dto;

    @BeforeEach
    void setUp() {
        dto = new MedicineDto();
        dto.setMedicineName("Paracetamol");
        dto.setMedicineDescription("Painkiller");
        dto.setMedicineType(MedicineType.PILLS);
        dto.setDose(500.0);
        dto.setDoseMeasureType(DoseMeasureType.MG);
    }

    @Test
    void testCreateAndGetById() {
        MedicineDto saved = service.create(dto);

        Optional<MedicineDto> found = service.getById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getMedicineName()).isEqualTo("Paracetamol");
    }

    @Test
    void testGetAll() {
        service.create(dto);
        MedicineDto dto2 = new MedicineDto();
        dto2.setMedicineName("Ibuprofen");
        dto2.setMedicineDescription("Anti-inflammatory");
        dto2.setMedicineType(MedicineType.SYRUP);
        dto2.setDose(200.0);
        dto2.setDoseMeasureType(DoseMeasureType.MG);
        service.create(dto2);

        List<MedicineDto> all = service.getAll();
        assertThat(all).hasSize(2);
        assertThat(all).extracting("medicineName").containsExactlyInAnyOrder("Paracetamol", "Ibuprofen");
    }

    @Test
    void testUpdate() {
        MedicineDto saved = service.create(dto);
        saved.setMedicineName("Acetaminophen");
        saved.setDose(650.0);

        MedicineDto updated = service.update(saved.getId(), saved);
        assertThat(updated.getMedicineName()).isEqualTo("Acetaminophen");
        assertThat(updated.getDose()).isEqualTo(650.0);
    }

    @Test
    void testDelete() {
        MedicineDto saved = service.create(dto);
        service.delete(saved.getId());
        Optional<MedicineDto> found = service.getById(saved.getId());
        assertThat(found).isEmpty();
    }

    @Test
    void testUpdateNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> service.update(randomId, dto));
    }
}
