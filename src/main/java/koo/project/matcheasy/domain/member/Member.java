package koo.project.matcheasy.domain.member;

import koo.project.matcheasy.domain.team.Team;
import lombok.*;
import org.apache.ibatis.annotations.Many;

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

    @ElementCollection
    @CollectionTable(
            name = "MEMBER_SKILLS",
            joinColumns = @JoinColumn(name = "MEMBER_ID")
    )
    private List<String> skills;

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

}
