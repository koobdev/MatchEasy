package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPositionDto {

    private Long id;
    private int status;
    private String rejectMessage;
    private String recruitMessage;
    private RecruitPosition position;
    private Long boardId;
    private int boardStatus;

    public void updateBoardDto(Long id, int status){
        this.boardId = id;
        this.boardStatus = status;
    }
}
