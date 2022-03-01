package koo.project.matcheasy.domain.team;

import com.fasterxml.jackson.annotation.JsonBackReference;
import koo.project.matcheasy.domain.member.Member;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamPosition {

    @Id @GeneratedValue
    @Column(name = "TEAM_POSITION_ID")
    private Long id;
    private String position;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    // 연관관계 편의메서드
    public void addTeam(Team team){
        if(this.team != null) {
            this.team.getPositions().remove(this);
        }

        this.team = team;
        team.getPositions().add(this);
    }
}
