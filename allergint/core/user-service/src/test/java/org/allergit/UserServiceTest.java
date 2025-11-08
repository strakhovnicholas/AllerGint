package org.allergit;

import lombok.RequiredArgsConstructor;
import org.allergit.mapper.UserMapper;
import org.allergit.service.UserService;
import org.allergit.user.dto.UserDto;
import org.allergit.user.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = {
                "spring.cloud.config.enabled=false",
                "spring.cloud.discovery.enabled=false",
                "eureka.client.enabled=false",
                "eureka.client.register-with-eureka=false",
                "eureka.client.fetch-registry=false"
        },
        classes = UserServiceApplication.class
)
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
class UserServiceTest {

    private final UserService userService;
    private final UserMapper userMapper;

    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUserDto = new UserDto();
        testUserDto.setName("John Doe");
        testUserDto.setEmail("johndoe@example.com");
        testUserDto.setAge(30);
        testUserDto.setGender("Male");
        testUserDto.setPassword("securePassword123");
        testUserDto.setRole(UserRole.USER);
    }

    @Test
    void testCreateUser() {
        UserDto createdUser = userService.create(testUserDto);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getName()).isEqualTo("John Doe");
    }

    @Test
    void testUpdateUser() {
        UserDto createdUser = userService.create(testUserDto);

        createdUser.setName("Jane Doe");
        UserDto updatedUser = userService.update(createdUser.getId(), createdUser);

        assertThat(updatedUser.getName()).isEqualTo("Jane Doe");
    }

    @Test
    void testDeleteUser() {
        UserDto createdUser = userService.create(testUserDto);

        userService.delete(createdUser.getId());

        List<UserDto> users = userService.getAll();
        assertThat(users).doesNotContain(createdUser);
    }
}
