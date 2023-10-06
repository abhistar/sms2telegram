package com.s2t.application.util;

import com.s2t.application.model.UserEntity;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

@UtilityClass
public class UserMapper {
    public User getUserFromUserEntities(UserEntity userEntity) {
        return new User(
            userEntity.getUserId(),
            userEntity.getPassword(),
            new ArrayList<>());
    }
}
