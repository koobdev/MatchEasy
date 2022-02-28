package koo.project.matcheasy.domain.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Positionly {

    @Id @GeneratedValue
    @Column(name = "TEAM_POSITIONLY_ID")
    private Long id;
    private int position; // 포지션 INDEX

    @Embedded
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team teamPositionly;
}
