package com.s2t.application.model.dto.responses;

import com.s2t.application.model.dto.responses.GenericResponse;
import com.s2t.application.model.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserStatusResponse extends GenericResponse {
    private UserStatus userStatus;
    private String token;
}
