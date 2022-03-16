package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.team.Task;
import koo.project.matcheasy.domain.team.TeamPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamSearchDto {

    private Long id;
    private List<MemberMeDto> members = new ArrayList<>();
    private List<TeamPosition> positions = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private String name;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
}
