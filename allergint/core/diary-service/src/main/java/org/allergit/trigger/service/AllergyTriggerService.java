package org.allergit.trigger.service;

import lombok.RequiredArgsConstructor;
import org.allergit.diary.dto.AllergyTriggerDto;
import org.allergit.trigger.entity.AllergyTrigger;
import org.allergit.trigger.mapper.AllergyTriggerMapper;
import org.allergit.trigger.repository.AllergyTriggerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AllergyTriggerService {

    private final AllergyTriggerRepository allergyTriggerRepository;
    private final AllergyTriggerMapper allergyTriggerMapper;

    public AllergyTriggerDto create(AllergyTriggerDto dto) {
        AllergyTrigger entity = allergyTriggerMapper.toEntity(dto);
        AllergyTrigger saved = allergyTriggerRepository.save(entity);
        return allergyTriggerMapper.toDto(saved);
    }

    public Optional<AllergyTriggerDto> getById(UUID id) {
        return allergyTriggerRepository.findById(id).map(allergyTriggerMapper::toDto);
    }

    public List<AllergyTriggerDto> getAll() {
        return allergyTriggerMapper.toDto(allergyTriggerRepository.findAll());
    }

    public AllergyTriggerDto update(UUID id, AllergyTriggerDto dto) {
        AllergyTrigger entity = allergyTriggerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trigger with id " + id + " not found"));
        allergyTriggerMapper.updateEntityFromDto(dto, entity);
        AllergyTrigger updated = allergyTriggerRepository.save(entity);
        return allergyTriggerMapper.toDto(updated);
    }

    public void delete(UUID id) {
        if (!allergyTriggerRepository.existsById(id)) {
            throw new IllegalArgumentException("Trigger with id " + id + " not found");
        }
        allergyTriggerRepository.deleteById(id);
    }
}
