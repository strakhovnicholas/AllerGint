package org.allergit.symptom.controller;

import org.allergit.diary.dto.UserSymptomDto;
import org.allergit.symptom.service.UserSymptomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/symptoms")
public class UserSymptomController {

    private final UserSymptomService service;

    public UserSymptomController(UserSymptomService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserSymptomDto> create(@RequestBody UserSymptomDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<UserSymptomDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSymptomDto> getById(@PathVariable UUID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSymptomDto> update(@PathVariable UUID id, @RequestBody UserSymptomDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
