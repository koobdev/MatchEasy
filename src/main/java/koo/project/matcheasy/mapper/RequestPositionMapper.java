package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.board.RequestPosition;
import koo.project.matcheasy.dto.RequestPositionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RequestPositionMapper{
    RequestPositionMapper REQUEST_POSITION_MAPPER = Mappers.getMapper(RequestPositionMapper.class);

    RequestPositionDto toDto(final RequestPosition entity);
    RequestPosition toEntity(final RequestPositionDto dto);
}
