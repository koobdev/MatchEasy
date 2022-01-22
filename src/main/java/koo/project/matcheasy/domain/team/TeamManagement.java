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

    @OneToMany(mappedBy = "teamManagementWeekly")
    private List<Weekly> weeklyList = new ArrayList<>();
    @OneToMany(mappedBy = "teamManagementDayly")
    private List<Dayly> daylyList = new ArrayList<>();
    @OneToMany(mappedBy = "teamManagementPositionly")
    private List<Positionly> positionlyList = new ArrayList<>();

}
