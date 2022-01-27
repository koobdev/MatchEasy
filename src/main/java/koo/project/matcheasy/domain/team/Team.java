package koo.project.matcheasy.domain.team;

import koo.project.matcheasy.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    private String name;
    private LocalDate startdate;
    private LocalDate enddate;

    @OneToMany(mappedBy = "teamWeekly", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Weekly> weeklyList = new ArrayList<>();
    @OneToMany(mappedBy = "teamDayly", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Dayly> daylyList = new ArrayList<>();
    @OneToMany(mappedBy = "teamPositionly", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Positionly> positionlyList = new ArrayList<>();


//    public void setName(String name) {
//        this.name = name;
//    }

    // 연관관계 편의 메서드
    public void addMember(Member member){
        members.add(member);
        member.builder().team(this);
    }
}