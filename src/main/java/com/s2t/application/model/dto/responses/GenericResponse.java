package com.s2t.application.model.dto.responses;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class GenericResponse {
    private String message;
}
