package koo.project.matcheasy.domain.team;

import javax.persistence.*;

@Entity
public class Dayly {

    @Id @GeneratedValue
    @Column(name = "TEAM_DAYLY_ID")
    private Long id;
    private int day; // N일

    @Embedded
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_MANAGEMENT_ID")
    private TeamManagement teamManagementDayly;
}
