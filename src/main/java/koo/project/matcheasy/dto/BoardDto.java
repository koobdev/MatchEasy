package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ToString
public class BoardDto {

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private List<RecruitPosition> positions;
    @NotNull
    private LocalDateTime startdate;
    @NotNull
    private LocalDateTime enddate;
    @NotNull
    private Member writer;
}
