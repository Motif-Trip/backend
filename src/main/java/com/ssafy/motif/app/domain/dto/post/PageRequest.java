package com.ssafy.motif.app.domain.dto.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageRequest { // 페이징 요청을 위한 Dto

    private int page;
    private int size;

    public int getSkip() {
        return (page - 1) * size;
    }

}
