package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.board.BoardContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitPositionDto {

    private Long id;
    @NotEmpty
    private String position;
    @NotEmpty
    private String content;
    private String recruitMessage;
    private int status;
    private Long positionId;
}
