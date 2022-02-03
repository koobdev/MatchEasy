package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.chat.Chat;
import koo.project.matcheasy.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.internal.engine.PredefinedScopeValidatorContextImpl;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BoardDto {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private List<RecruitPositionDto> positions;
    private Chat chat;
    @NotNull
    private LocalDateTime startdate;
    @NotNull
    private LocalDateTime enddate;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    @NotNull
    private Long writerId;


    public void addPosition(RecruitPositionDto recruitPositionDto){
        positions.add(recruitPositionDto);
    }
}
