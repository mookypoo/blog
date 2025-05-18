package com.mooky.blog.domain.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  public void shouldReturnUserWhenUserExists() {
    when(userRepository.getUserDetails(1L))
        .thenReturn(Optional.of(new UserDetails(1L, "Mooky", "sookim482.dev@gmail.com")));

    UserDetails user = userService.getUserDetails(1L);

    assertNotNull(user);
    assertEquals("Mooky", user.getUsername());
  }
}
