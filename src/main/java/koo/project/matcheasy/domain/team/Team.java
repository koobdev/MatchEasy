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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "TEAM_ID")
    private TeamManagement teamManagement;

    private String name;
    private LocalDate startdate;
    private LocalDate enddate;


    public void setName(String name) {
        this.name = name;
    }

    // 연관관계 편의 메서드
    public void addMember(Member member){
        members.add(member);
        member.builder().team(this);
    }

    // TODO
    public void addTeamManagement(Team team){
        teamManagement.addTeam(team);
    }
}