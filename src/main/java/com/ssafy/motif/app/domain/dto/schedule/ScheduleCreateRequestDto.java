package com.ssafy.motif.app.domain.dto.schedule;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleCreateRequestDto {

    private Long scheduleId;

    /* 스케쥴 이름 (default = 여행지명) */
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    /* 스케쥴 카테고리 (ex. 식당) */
    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    /* 여행지 위도 */
    @NotNull(message = "위도는 필수입니다.")
    private Double lat;

    /* 여행지 경도 */
    @NotNull(message = "경도는 필수입니다.")
    private Double lng;

    /* 스케쥴 시작 시간 */
    @NotNull(message = "시작 시간은 필수입니다.")
    private LocalDateTime startTime;

    /* 스케쥴 끝나는 시간 */
    @NotNull(message = "종료 시간은 필수입니다.")
    private LocalDateTime endTime;

    /* 마커 컬러 코드 (default = 랜덤) */
    @NotBlank(message = "컬러 코드는 필수입니다.")
    private String colorCode;

}
