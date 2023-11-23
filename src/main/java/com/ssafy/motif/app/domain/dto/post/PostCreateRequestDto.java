package com.ssafy.motif.app.domain.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequestDto {

    @JsonIgnore
    private Long postId;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    @Schema(description = "게시글 제목", example = "새로운 게시글 제목")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 1000, message = "내용은 1000자 이내여야 합니다.")
    @Schema(description = "게시글 내용", example = "여기에 게시글 내용을 작성합니다. 여기에는 길이에 제한이 있으며, 최대 1000자까지 가능합니다.")
    private String contents;
}

