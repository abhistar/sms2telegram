package com.s2t.application.core;

import com.s2t.application.model.UserEntity;
import com.s2t.application.model.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityExistsException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Nested
    class loadUserByUsernameMethod {
        @Test
        void getUserDetails_WhenValidUser() {
            UserEntity user = Mockito.mock(UserEntity.class);
            when(user.getUserId()).thenReturn("1");
            when(user.getPassword()).thenReturn("password");
            when(userRepository.findByUserId(any())).thenReturn(Optional.of(user));

            UserDetails userDetails = userService.loadUserByUsername(any());

            assertEquals("1", userDetails.getUsername());
            assertEquals("password", userDetails.getPassword());
        }

        @Test
        void throwUserNameNotFoundException_WhenNoUser() {
            when(userRepository.findByUserId(any())).thenReturn(Optional.empty());

            assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(any()));
        }
    }

    @Nested
    class loadUserByUserIdMethod {
        @Test
        void getUser_WhenValidUser() {
            UserEntity user = Mockito.mock(UserEntity.class);
            when(user.getUserId()).thenReturn("1");
            when(user.getPassword()).thenReturn("password");
            when(userRepository.findByUserId(any())).thenReturn(Optional.of(user));

            UserEntity userEntity = userService.loadUserByUserId(anyLong());

            assertEquals("1", userEntity.getUserId());
            assertEquals("password", userEntity.getPassword());
        }

        @Test
        void getNull_WhenInvalidUser() {
            when(userRepository.findByUserId(any())).thenReturn(Optional.empty());

            UserEntity userEntity = userService.loadUserByUserId(anyLong());

            assertNull(userEntity);
        }
    }

    @Nested
    class saveUserMethod {
        @Test
        void saveUser_WhenNewUserIsAdded() {
            when(userRepository.save(any())).thenReturn(Mockito.mock(UserEntity.class));

            userService.saveUser(1L);

            verify(userRepository, times(1)).save(any());
        }

        @Test
        void throwIllegalArgumentError_WhenUserAlreadyExists() {
            doThrow(DataIntegrityViolationException.class).when(userRepository).save(any(UserEntity.class));

            assertThrows(IllegalArgumentException.class, () -> userService.saveUser(1L));
        }
    }

}