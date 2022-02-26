package koo.project.matcheasy.domain.team;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import koo.project.matcheasy.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;


    @JsonManagedReference
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    private String name;
    private LocalDateTime startdate;
    private LocalDateTime enddate;

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

    @OneToMany(mappedBy = "teamWeekly", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Weekly> weeklyList = new ArrayList<>();
    @OneToMany(mappedBy = "teamDayly", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Dayly> daylyList = new ArrayList<>();
    @OneToMany(mappedBy = "teamPositionly", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Positionly> positionlyList = new ArrayList<>();


    public void addMember(Member member){
        this.getMembers().add(member);
        member.builder().team(this);
    }
}