package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.member.MemberSkills;
import koo.project.matcheasy.domain.team.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberMeDto {

    private Long id;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String name;
    @NotNull
    private int age;
    @NotEmpty
    private String email;
    @NotEmpty
    private String position;
    @NotEmpty
    private List<MemberSkills> memberSkills;
    private Long teamId;
}
