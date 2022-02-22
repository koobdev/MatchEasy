package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.member.MemberSkills;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    @NotNull
    private int age;
    @NotEmpty
    private String email;
    @NotEmpty
    private String position;
    @NotEmpty
    private List<MemberSkillsDto> memberSkills;

    // 연관관계 편의 메서드
    public void addSkills(MemberSkillsDto skills){
        memberSkills.add(skills);
    }

}
