package com.s2t.application.core;

import com.s2t.application.model.UserEntity;
import com.s2t.application.model.repository.UserRepository;
import com.s2t.application.util.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.s2t.application.util.StringConstants.ExceptionMessage.ERROR;
import static com.s2t.application.util.StringConstants.ExceptionMessage.USERNAME_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try {
            return UserMapper.getUserFromUserEntities(loadUserByUserId(userId));
        }
        catch (NullPointerException e) {
            log.error(ERROR, e);
            throw new UsernameNotFoundException(USERNAME_NOT_FOUND);
        }
    }

    public UserEntity loadUserByUserId(Long userId) {
        return loadUserByUserId(String.valueOf(userId));
    }

    @SneakyThrows
    public void saveUser(long userId) {
        String password = passwordEncoder.encode(UUID.randomUUID().toString());
        UserEntity user = new UserEntity(userId, password);
        try {
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException e) {
            log.error(ERROR, e);
            throw new IllegalArgumentException();
        }
    }

    private UserEntity loadUserByUserId(String userId) {
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        return user.orElse(null);
    }
}
