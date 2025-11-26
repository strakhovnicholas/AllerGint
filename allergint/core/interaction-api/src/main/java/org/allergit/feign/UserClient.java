package org.allergit.feign;


import org.allergit.user.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "user-service"
)
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserDto getById(@PathVariable("id") UUID id);

    @GetMapping("/api/users")
    List<UserDto> getAll();

    @PostMapping("/api/users")
    UserDto create(@RequestBody UserDto userDto);

    @PatchMapping("/api/users/{id}")
    UserDto update(@PathVariable("id") UUID id, @RequestBody UserDto userDto);

    @DeleteMapping("/api/users/{id}")
    void delete(@PathVariable("id") UUID id);
}

