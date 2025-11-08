package org.allergit.symptom.service;

import org.allergit.diary.dto.UserSymptomDto;
import org.allergit.symptom.entity.UserSymptom;
import org.allergit.symptom.mapper.UserSymptomMapper;
import org.allergit.symptom.repository.UserSymptomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserSymptomService {

    private final UserSymptomRepository repository;
    private final UserSymptomMapper mapper;

    public UserSymptomService(UserSymptomRepository repository, UserSymptomMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserSymptomDto create(UserSymptomDto dto) {
        UserSymptom entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public List<UserSymptomDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserSymptomDto> getById(UUID id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public UserSymptomDto update(UUID id, UserSymptomDto dto) {
        UserSymptom entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserSymptom not found"));
        entity.setSymptomName(dto.getSymptomName());
        entity.setTimestamp(dto.getTimestamp());
        entity.setSymptomState(dto.getSymptomState());
        return mapper.toDto(repository.save(entity));
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("UserSymptom not found");
        }
        repository.deleteById(id);
    }
}
