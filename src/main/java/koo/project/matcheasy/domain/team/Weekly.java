package koo.project.matcheasy.domain.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Weekly {

    @Id @GeneratedValue
    @Column(name = "TEAM_WEEKLY_ID")
    private Long id;
    private int week; // N주차

    @Embedded
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team teamWeekly;
}
