package koo.project.matcheasy.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSkills {

    @Id @GeneratedValue
    @Column(name = "MEMBER_SKILLS_ID")
    private Long id;
    private String skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member memberSkill;
}
