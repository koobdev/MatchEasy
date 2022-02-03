package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.dto.RecruitPositionDto;
import koo.project.matcheasy.repository.RecruitPositionRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class BoardContext {

    private BoardContent boardContent;

    @BeforeMapping
    public void setEntity(@MappingTarget BoardContent boardContent){
        this.boardContent = boardContent;
    }

    @AfterMapping
    public void establishRelation(@MappingTarget RecruitPosition recruitPosition){
        recruitPosition.setBoardContent(boardContent);
//        recruitPosition.builder().boardContent(boardContent);
//        recruitPosition.addBoardContent(boardContent);
    }
}
