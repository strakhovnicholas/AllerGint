package org.allergit.trigger.controller;

import lombok.RequiredArgsConstructor;
import org.allergit.diary.dto.AllergyTriggerDto;
import org.allergit.trigger.service.AllergyTriggerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/triggers")
@RequiredArgsConstructor
public class AllergyTriggerController {

    private final AllergyTriggerService allergyTriggerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AllergyTriggerDto create(@RequestBody AllergyTriggerDto dto) {
        return allergyTriggerService.create(dto);
    }

    @GetMapping("/{id}")
    public AllergyTriggerDto getById(@PathVariable UUID id) {
        return allergyTriggerService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trigger with id " + id + " not found"));
    }

    @GetMapping
    public List<AllergyTriggerDto> getAll() {
        return allergyTriggerService.getAll();
    }

    @PutMapping("/{id}")
    public AllergyTriggerDto update(@PathVariable UUID id, @RequestBody AllergyTriggerDto dto) {
        return allergyTriggerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        allergyTriggerService.delete(id);
    }
}
