package koo.project.matcheasy.domain.team;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id @GeneratedValue
    @Column(name = "TASK_ID")
    private Long id;
    private int date; // N일, N주
    private String goal; // 목표
    private int status; // 완료여부
    private int dateStatus; // 일일 목표, 주간 목표
    private Long positionId; // 포지션 id (TeamSearch에서 가져와서 선택할 수 있음)

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    // 연관관계 편의 메서드
    public void addTeam(Team team){
        this.team = team;
        team.getTasks().add(this);
    }

}