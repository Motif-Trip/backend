package com.ssafy.motif.app.domain.dto.schedule;

import com.ssafy.motif.app.code.ErrorCode;
import com.ssafy.motif.app.exception.EndTimeBeforeStartTimeException;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleCreateRequestDto {

    private Long scheduleId;

    @NotBlank(message = "이름은 필수입니다.")
    @Schema(description = "여행지명 (default: 상호명)", example = "삼첩분식 건대점")
    private String name;

    @NotBlank(message = "카테고리는 필수입니다.")
    @Schema(description = "카테고리", example = "맛집")
    private String category;

    @NotNull(message = "위도는 필수입니다.")
    @Schema(description = "위도(latitude)", example = "37.540705")
    private Double lat;

    @NotNull(message = "경도는 필수입니다.")
    @Schema(description = "경도(longitude)", example = "127.069227")
    private Double lng;

    @NotNull(message = "시작 시간은 필수입니다.")
    @Schema(description = "시작 시간", example = "2023-01-01T08:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @NotNull(message = "종료 시간은 필수입니다.")
    @Schema(description = "종료 시간", example = "2023-041-01T10:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    @NotBlank(message = "컬러 코드는 필수입니다.")
    @Schema(description = "컬러 코드", example = "#FFFFFF")
    private String colorCode;

    public void validateTime() {
        if (startTime != null && endTime != null && !endTime.isAfter(startTime)) {
            throw new EndTimeBeforeStartTimeException(ErrorCode.INVALID_SCHEDULE_TIMING);
        }
    }

}
