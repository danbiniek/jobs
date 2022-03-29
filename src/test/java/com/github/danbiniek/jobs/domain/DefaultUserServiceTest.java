package com.github.danbiniek.jobs.domain;

import com.github.danbiniek.jobs.api.dto.UserDto;
import com.github.danbiniek.jobs.domain.converter.UserDto2EntityConverter;
import com.github.danbiniek.jobs.domain.converter.User2DtoConverter;
import com.github.danbiniek.jobs.domain.entity.User;
import com.github.danbiniek.jobs.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @Mock
    private Clock clock;
    @Mock
    private UserRepository userRepository;
    @Mock
    private User2DtoConverter toDtoConverter;
    @Mock
    private UserDto2EntityConverter toEntityConverter;

    @InjectMocks
    private DefaultUserService userService;

    @Test
    void aUserPagedList_shouldBeReturned_whenSizeAndPageIsGiven() {
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());
        userService.getAllUsers(12, 3);

        verify(userRepository).findAll(PageRequest.of(12, 3));
    }

    @Test
    void aNewUser_isSaved() {
        when(clock.instant()).thenReturn(Instant.parse("2022-01-01T12:00:00.00Z"));
        when(clock.getZone()).thenReturn(ZoneId.of("Europe/Warsaw"));
        when(toEntityConverter.convert(any(UserDto.class))).thenCallRealMethod();

        UserDto testUser = new UserDto("test name", "test last name", "test login", "test pass");
        userService.addUser(testUser);

        User expected = new User(null, "test name", "test last name",
                "last login", "test pass", LocalDateTime.now(clock));
        verify(userRepository).save(eq(expected));
    }

    @Test
    void anExistingUser_isDeleted_ifExist() {
        UserDto testUser = new UserDto(1, "test name", "test last name", "test login", "test pass", LocalDateTime.now());

        boolean result = userService.deleteUser(testUser);

        assertTrue(result);
        verify(userRepository).deleteById(eq(1));
    }

    @Test
    void theDelete_shouldReturnFalse_whenUserDoesNotExist() {
        when(clock.instant()).thenReturn(Instant.parse("2022-01-01T12:00:00.00Z"));
        when(clock.getZone()).thenReturn(ZoneId.of("Europe/Warsaw"));
        doThrow(new IllegalArgumentException()).when(userRepository).deleteById(anyInt());

        UserDto testUser = new UserDto(1, "test name", "test last name", "test login", "test pass", LocalDateTime.now(clock));

        boolean result = userService.deleteUser(testUser);

        assertFalse(result);
    }

    @Test
    void aUser_shouldBeUpdated_ifExist() {
        when(clock.instant()).thenReturn(Instant.parse("2022-01-01T12:00:00.00Z"));
        when(clock.getZone()).thenReturn(ZoneId.of("Europe/Warsaw"));
        User testUser = new User(1, "test name", "test last name", "test login", "test pass", LocalDateTime.now(clock));
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        UserDto result = userService.updateUser(new UserDto(1, "name", "surename", "login", "pass", LocalDateTime.now(clock)));

        User expected = new User(1, "name", "surename", "login", "pass", LocalDateTime.now(clock));
        assertEquals(toDtoConverter.convert(expected), result);
        verify(userRepository).save(eq(expected));
    }

    @Test
    void anIAE_shouldBeThrown_whenUserNotExistExist() {
        when(userRepository.findById(123)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(new UserDto(123, null, null, null, null, null)));
    }
}