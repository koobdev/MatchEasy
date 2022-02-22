package koo.project.matcheasy.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
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
    private Member member;


    // 연관관계 편의 메서드
    public void addMember(Member member){
        this.member = member;
        member.getMemberSkills().add(this);
    }
}
