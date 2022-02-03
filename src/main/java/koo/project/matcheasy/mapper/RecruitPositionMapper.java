package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.dto.RecruitPositionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecruitPositionMapper extends EntityDtoMapper<RecruitPositionDto, RecruitPosition>{

    RecruitPositionMapper POSITION_MAPPER = Mappers.getMapper(RecruitPositionMapper.class);

    @Override
    RecruitPosition toEntity(final RecruitPositionDto dto);

    @Override
    RecruitPositionDto toDto(final RecruitPosition entity);
}
