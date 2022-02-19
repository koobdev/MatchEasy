package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.dto.BoardDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

//@Mapper(uses = {RecruitPositionMapper.class})
@Mapper
public interface BoardMapper  {

    BoardMapper BOARD_MAPPER = Mappers.getMapper(BoardMapper.class);

//    @Override
//    @Mapping(source = "positions", target = "positionList")
    BoardContent toEntity(BoardDto dto, @Context BoardContext boardContext);

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

//    @Override
    BoardDto toDto(final BoardContent entity);

//    BoardContent toEntity(BoardDto boardDto);
}
