package com.s2t.application.core;

import com.s2t.application.model.UserEntity;
import com.s2t.application.model.repository.UserRepository;
import com.s2t.application.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByChatId(userId);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Unable to find given user");
        }
        return UserMapper.getUserFromUserEntities(user.get());
    }

    public UserEntity loadUserByChatId(Long chatId) {
        Optional<UserEntity> user = userRepository.findByChatId(String.valueOf(chatId));
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Unable to find given user");
        }
        return user.get();
    }
}
