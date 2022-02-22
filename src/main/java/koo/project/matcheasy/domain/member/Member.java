package koo.project.matcheasy.domain.member;

import koo.project.matcheasy.domain.team.Team;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private int age;
    private String email;
    private String position;

    // 로그인 시 저장되는 refreshToken
    private String refreshToken;

    // 조회할 때 쿼리를 2번 날림. 프록시를 사용할 수 있도록 일대다 관계로 바꿀 것.
//    @ElementCollection
//    @CollectionTable(
//            name = "MEMBER_SKILLS",
//            joinColumns = @JoinColumn(name = "MEMBER_ID")
//    )
//    private List<String> skills;


    @OneToMany(mappedBy = "member")
    private List<MemberSkills> memberSkills;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    @Column(
            updatable = false, insertable = false, nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime regdate;

    @Column(
            updatable = false, insertable = false, nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    private LocalDateTime moddate;


    // 연관관계 편의 메서드
    public void addMemberSkills(MemberSkills skills){
        memberSkills.add(skills);
        skills.builder().member(this);
    }

    /**
     * refreshToken 추가 메서드
     */
    public void addRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
