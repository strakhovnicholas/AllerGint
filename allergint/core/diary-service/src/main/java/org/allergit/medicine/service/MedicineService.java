package org.allergit.medicine.service;

import org.allergit.diary.dto.MedicineDto;
import org.allergit.medicine.entity.Medicine;
import org.allergit.medicine.mapper.MedicineMapper;
import org.allergit.medicine.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicineService {

    private final MedicineRepository repository;
    private final MedicineMapper mapper;

    public MedicineService(MedicineRepository repository, MedicineMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MedicineDto create(MedicineDto dto) {
        Medicine medicine = mapper.toEntity(dto);
        Medicine saved = repository.save(medicine);
        return mapper.toDto(saved);
    }

    public Optional<MedicineDto> getById(UUID id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public List<MedicineDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public MedicineDto update(UUID id, MedicineDto dto) {
        Medicine existing = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Medicine not found"));
        existing.setMedicineName(dto.getMedicineName());
        existing.setMedicineDescription(dto.getMedicineDescription());
        existing.setMedicineType(dto.getMedicineType());
        existing.setDose(dto.getDose());
        existing.setDoseMeasureType(dto.getDoseMeasureType());
        Medicine updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) throw new IllegalArgumentException("Medicine not found");
        repository.deleteById(id);
    }
}
