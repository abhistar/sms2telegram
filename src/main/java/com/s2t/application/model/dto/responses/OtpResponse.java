package com.s2t.application.model.dto.responses;

import com.s2t.application.model.dto.responses.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class OtpResponse extends GenericResponse {
    private String otp;
}

