package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.dto.MemberDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

//@Mapper(uses = {RecruitPositionMapper.class})
@Mapper
public interface BoardMapper extends EntityDtoMapper<BoardDto, BoardContent>{

    BoardMapper BOARD_MAPPER = Mappers.getMapper(BoardMapper.class);

//    @Override
//    @Mapping(source = "positions", target = "positionList")
    BoardContent toEntity(BoardDto dto);

    default BoardContent formId(Long id){
        if(id == null){
            return null;
        }
        BoardContent boardContent = BoardContent.builder()
                .id(id).build();
        return boardContent;
    }

//    @AfterMapping
//    default void after(@MappingTarget BoardContent boardContent, BoardDto boardDto){
//        boardContent.getPositions().forEach(recruitPosition -> {
//            recruitPosition.builder().boardContent(boardContent);
//        });
//    }

    BoardDto toDto(final BoardContent entity);

}
