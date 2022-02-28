package koo.project.matcheasy.domain.team;

import lombok.*;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_DAYLY_ID")
    private Long id;
    private int nDate; // N일, N주
    private String goal; // 목표
    private int status; // 완료여부
    private int dateStatus; // 일일 목표, 주간 목표

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    // 연관관계 편의 메서드
//    public void addTeam(Team team){
//        this.teamDayly = team;
//        team.getDaylyList().add(this);
//    }

}