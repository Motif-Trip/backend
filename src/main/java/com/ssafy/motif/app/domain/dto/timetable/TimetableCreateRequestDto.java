package com.ssafy.motif.app.domain.dto.timetable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
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

    private String tableName;

    private LocalDateTime dateTime;

}
