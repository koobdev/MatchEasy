package koo.project.matcheasy.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class TaskDto {

    private Long id;
    @NotEmpty
    private String goal; // 목표
    @NotEmpty
    private int status; // 완료여부
    @NotEmpty
    private Long teamId;
    @NotEmpty
    private Long positionId;
    @NotEmpty
    private String position; // 포지션 이름

    private LocalDateTime startdate;
    private LocalDateTime enddate;
}
