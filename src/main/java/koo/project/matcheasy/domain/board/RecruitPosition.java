package koo.project.matcheasy.domain.board;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import koo.project.matcheasy.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecruitPosition {

    @Id @GeneratedValue
    @Column(name = "POSITION_ID")
    private Long id;
    private String position;
    private String content;
    private int status;
//    private String rejectMessage;
//    private String recruitMessage;

    @JsonManagedReference
    @OneToMany(mappedBy = "position")
    private List<RequestPosition> requestPosition = new ArrayList<>();

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "RECRUIT_MEMBER_ID")
//    private Member recruitMember;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTENT_ID")
    private BoardContent boardContent;

    // 연관관계 편의 메서드
    public void addBoardContent(BoardContent boardContent) {
        this.boardContent = boardContent;
        boardContent.getPositions().add(this);
    }

//    public void addRecruitMember(Member member){
//        this.recruitMember = member;
//    }


    // Update Entity
    public void updateStatus(int status){
        this.status = status;
    }

//    public void updateRecruitMessage(String message){
//        this.recruitMessage = message;
//    }
//
//    public void updateRejectMessage(String message){
//        this.rejectMessage = message;
//    }
}
