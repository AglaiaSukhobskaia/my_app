package com.aglaya.dto.response;

import lombok.Builder;

@Builder
public record ItemRs(Integer id,
                     String name) {
}
