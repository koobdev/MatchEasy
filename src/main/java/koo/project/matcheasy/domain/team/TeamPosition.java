package koo.project.matcheasy.domain.team;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamPosition {

    @Id @GeneratedValue
    private Long id;
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    // 연관관계 편의메서드
    public void addPosition(Team team){
        this.team = team;
        team.getPositions().add(this);
    }
}
