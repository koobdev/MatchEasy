package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.team.Team;
import koo.project.matcheasy.dto.TeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper TASK_MAPPER = Mappers.getMapper(TaskMapper.class);

    Team toEntity(TeamDto dto);
    TeamDto toDto(Team entity);
}
