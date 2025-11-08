package org.allergit.diarypage;

import lombok.RequiredArgsConstructor;
import org.allergit.DiaryServiceApplication;
import org.allergit.diary.dto.DiaryPageDto;
import org.allergit.diary.dto.MedicineDto;
import org.allergit.diary.dto.UserSymptomDto;
import org.allergit.diary.dto.WeatherDto;
import org.allergit.diarypage.repository.DiaryPageRepository;
import org.allergit.diarypage.service.DiaryPageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
public class DiaryPageServiceTest {

    @Autowired
    private DiaryPageService service;

    @Autowired
    private DiaryPageRepository repository;

    @Test
    void testCreateAndGetDiaryPage() {
        DiaryPageDto dto = new DiaryPageDto();
        dto.setUserId(UUID.randomUUID());
        dto.setUserNotes("Feeling good today");
        dto.setTimestamp(ZonedDateTime.now());

        DiaryPageDto saved = service.create(dto);
        assertThat(saved.getId()).isNotNull();

        DiaryPageDto fetched = service.getById(saved.getId()).orElseThrow();
        assertThat(fetched.getUserNotes()).isEqualTo("Feeling good today");
    }

    @Test
    void testAddMedicineSymptomWeather() {
        DiaryPageDto diary = service.create(new DiaryPageDto());
        UUID diaryId = diary.getId();

        MedicineDto med = new MedicineDto();
        med.setMedicineName("Aspirin");
        med.setDose(100.0);
        service.addMedicine(diaryId, med);

        UserSymptomDto symptom = new UserSymptomDto();
        symptom.setSymptomName("Headache");
        symptom.setTimestamp(ZonedDateTime.now());
        service.addSymptom(diaryId, symptom);

        WeatherDto weather = new WeatherDto();
        weather.setTemperature(20.0);
        weather.setTimestamp(ZonedDateTime.now());
        service.addWeather(diaryId, weather);

        DiaryPageDto updated = service.getByFullId(diaryId).orElseThrow();
        assertThat(updated.getMedicines()).hasSize(1);
        assertThat(updated.getUserSymptoms()).hasSize(1);
        assertThat(updated.getWeathers()).hasSize(1);
    }
}
