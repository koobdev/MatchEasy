package koo.project.matcheasy.domain.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dayly {

    @Id @GeneratedValue
    @Column(name = "TEAM_DAYLY_ID")
    private Long id;
    private int day; // N일
    private String goal; // 목표
    private int status; // 완료여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team teamDayly;


    // ㅂ연관관계 편의 메서드
//    public void addTeam(Team team){
//        this.teamDayly = team;
//        team.getDaylyList().add(this);
//    }
}
