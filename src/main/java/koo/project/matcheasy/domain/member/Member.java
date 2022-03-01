package koo.project.matcheasy.domain.member;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import koo.project.matcheasy.domain.team.Team;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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


    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberSkills> memberSkills = new ArrayList<>();

    @JsonBackReference
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
    public void addTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

    /**
     * refreshToken 추가 메서드
     */
    public void addRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
