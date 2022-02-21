package koo.project.matcheasy.dto;

import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.chat.Chat;
import koo.project.matcheasy.domain.chat.ChatRoom;
import koo.project.matcheasy.domain.member.Member;
import lombok.*;
import org.hibernate.validator.internal.engine.PredefinedScopeValidatorContextImpl;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private List<RecruitPositionDto> positions;
    private ChatRoom chatRoom;
    @NotNull
    private LocalDateTime startdate;
    @NotNull
    private LocalDateTime enddate;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    @NotNull
    private Long writerId;
    private int status;


    public void addPosition(RecruitPositionDto recruitPositionDto){
        positions.add(recruitPositionDto);
    }
}
