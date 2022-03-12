package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {

    private Long id;
    @NotEmpty
    private String name; // 팀명 혹은 프로젝트명
    @NotNull
    private LocalDateTime startdate;
    @NotNull
    private LocalDateTime enddate;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
}
