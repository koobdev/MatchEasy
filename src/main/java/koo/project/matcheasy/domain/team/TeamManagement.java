package koo.project.matcheasy.domain.team;

import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeamManagement {

    @Id @GeneratedValue
    @Column(name = "TEAM_MANAGEMENT_ID")
    private Long id;
    private String carryOver;

    @OneToOne(mappedBy = "teamManagement")
    private Team team;

    @OneToMany(mappedBy = "teamManagementWeekly", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Weekly> weeklyList = new ArrayList<>();
    @OneToMany(mappedBy = "teamManagementDayly", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Dayly> daylyList = new ArrayList<>();
    @OneToMany(mappedBy = "teamManagementPositionly", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Positionly> positionlyList = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addTeam(Team team){
        this.id = team.getId();
    }
}
