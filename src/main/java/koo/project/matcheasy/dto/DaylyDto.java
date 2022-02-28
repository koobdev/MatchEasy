package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.team.Task;
import koo.project.matcheasy.domain.team.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DaylyDto {

    private Long id;
    @NotEmpty
    private int day; // N일
    @NotEmpty
    private String goal; // 목표
    private int status; // 완료여부
    @NotEmpty
    private Long teamId;


}
