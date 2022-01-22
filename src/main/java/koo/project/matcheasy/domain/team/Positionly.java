package koo.project.matcheasy.domain.team;

import javax.persistence.*;

@Entity
public class Positionly {

    @Id @GeneratedValue
    @Column(name = "TEAM_POSITIONLY_ID")
    private Long id;
    private int position; // 포지션 INDEX

    @Embedded
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_MANAGEMENT_ID")
    private TeamManagement teamManagementPositionly;
}
