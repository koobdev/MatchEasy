package koo.project.matcheasy.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class TaskDto {

    private Long id;
    @NotEmpty
    private int date; // N일, N주
    @NotEmpty
    private String goal; // 목표
    @NotEmpty
    private int status; // 완료여부
    @NotEmpty
    private int dateStatus; // 일일 목표, 주간 목표
    @NotEmpty
    private Long teamId;
    @NotEmpty
    private Long positionId;
}
