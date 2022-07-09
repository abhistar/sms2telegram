package com.s2t.application.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PingResponse {
    private String reply;
}
