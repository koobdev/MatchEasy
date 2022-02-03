package koo.project.matcheasy.domain.board;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private BoardContent boardContent;

    public void setBoardContent(BoardContent boardContent) {
        this.boardContent = boardContent;
    }

    // 연관관계 편의 메서드
    public void addBoardContent(BoardContent boardContent){
        this.boardContent = boardContent;
        boardContent.getPositions().add(this);
    }
}
