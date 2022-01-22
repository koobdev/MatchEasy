package koo.project.matcheasy.domain.team;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Weekly {

    @Id @GeneratedValue
    @Column(name = "TEAM_WEEKLY_ID")
    private Long id;
    private int week; // N주차

    @Embedded
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_MANAGEMENT_ID")
    private TeamManagement teamManagementWeekly;
}
