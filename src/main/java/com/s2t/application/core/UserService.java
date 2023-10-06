package com.s2t.application.core;

import com.s2t.application.model.UserEntity;
import com.s2t.application.model.repository.UserRepository;
import com.s2t.application.util.AuthUtil;
import com.s2t.application.util.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

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
        catch (Exception e) {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public UserEntity loadUserByUserId(Long userId) {
        return loadUserByUserId(String.valueOf(userId));
    }

    public void saveUser(long userId) {
        String password = passwordEncoder.encode(AuthUtil.generateOTP(10));
        UserEntity user = new UserEntity(userId, password);
        userRepository.save(user);
    }

    private UserEntity loadUserByUserId(String userId) {
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        return user.orElse(null);
    }
}
