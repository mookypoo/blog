package com.mooky.blog.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mooky.pet_diary.domain.user.UserDto;
import com.mooky.pet_diary.domain.user.UserRepository;
import com.mooky.pet_diary.domain.user.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldReturnUserWhenUserExists() {
        when(this.userRepository.getUserDetails(1L))
            .thenReturn(Optional.of(new UserDto(1L, "Mooky", "sookim482.dev@gmail.com")));

        UserDto user = this.userService.getUserDetails(1L);

        assertThat(user).isNotNull();
        assertThat("Mooky").isEqualTo(user.getUsername());
    }
}
