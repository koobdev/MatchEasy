package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.dto.BoardDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapper extends EntityDtoMapper<BoardDto, BoardContent>{

    BoardMapper BOARD_MAPPER = Mappers.getMapper(BoardMapper.class);

    @Override
    BoardContent toEntity(final BoardDto dto);

    @Override
    BoardDto toDto(final BoardContent entity);
}
