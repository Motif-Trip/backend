package com.ssafy.motif.app.domain.dto.timetable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimetableCreateRequestDto {

    @JsonIgnore
    private Long timetableId;

    @JsonIgnore
    private Long memberId;

    @NotBlank(message = "테이블 이름은 필수입니다.")
    @Size(max = 100, message = "테이블 이름은 최대 100자 이내여야 합니다.")
    @Schema(description = "테이블 - 여행계획표 이름", example = "건대역 맛집 탐방")
    private String tableName;

}
