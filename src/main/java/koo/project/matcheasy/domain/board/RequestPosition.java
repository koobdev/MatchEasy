package koo.project.matcheasy.domain.board;

import com.fasterxml.jackson.annotation.JsonBackReference;
import koo.project.matcheasy.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestPosition {

    @Id @GeneratedValue
    private Long id;
    private int status;
    private String rejectMessage;
    private String recruitMessage;

    // 모집 포지션을 불러올 때, 요청된 포지션정보를 항상 join해서 가져옴
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECRUITPOSITION_ID")
    private RecruitPosition position;

    @OneToOne
    @JoinColumn(name = "RECRUIT_MEMBER_ID")
    private Member recruitMember;


    // 연관관계 편의 메서드
    public void addPosition(RecruitPosition position){
        this.position = position;
    }

    public void addRecruitMember(Member member){
        this.recruitMember = member;
    }


    // update
    public void updateStatus(int status){
        this.status = status;
    }
    public void updateRejectMessage(String msg){
        this.rejectMessage = msg;
    }
    public void updateRecruitMessage(String msg){
        this.recruitMessage = msg;
    }
}
