package com.ssafy.motif.app.domain.dto.post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {

    private Long postId;
    private Long memberId;
    private String nickname;
    private String likeCnt;
    private String contents;
    private String createdAt;
    private boolean isLiked;

}
