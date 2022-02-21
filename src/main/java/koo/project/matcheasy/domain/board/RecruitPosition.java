package koo.project.matcheasy.domain.board;

import koo.project.matcheasy.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECRUIT_MEMBER_ID")
    private Member recruitMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private BoardContent boardContent;

    // 연관관계 편의 메서드
    public void setBoardContent(BoardContent boardContent) {
        this.boardContent = boardContent;
        boardContent.getPositions().add(this);
    }

    public void addRecruitMember(Member member){
        this.recruitMember = member;
    }
}
