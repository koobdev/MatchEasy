package koo.project.matcheasy.domain.member;

import com.fasterxml.jackson.annotation.JsonBackReference;
import koo.project.matcheasy.service.MemberService;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberSkills {

    @Id @GeneratedValue
    @Column(name = "MEMBER_SKILLS_ID")
    private Long id;
    private String skill;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    // 연관관계 편의 메서드
    public void addMember(Member member){
        this.member = member;
        member.getMemberSkills().add(this);
    }
}
