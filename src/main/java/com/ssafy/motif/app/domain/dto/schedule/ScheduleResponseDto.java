package com.ssafy.motif.app.domain.dto.schedule;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleResponseDto {

    private Long timetableId;
    private Long scheduleId;
    private String name;
    private String category;
    private Double lat;
    private Double lng;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String colorCode;

}
