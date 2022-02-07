package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.board.BoardContent;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class RecruitPositionDto {

    private Long id;
    @NotEmpty
    private String position;
    @NotEmpty
    private String content;

    private Long positionId;

}
