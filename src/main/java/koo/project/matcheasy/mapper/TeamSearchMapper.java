package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.team.Team;
import koo.project.matcheasy.dto.TeamSearchDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeamSearchMapper {
    TeamSearchMapper TEAM_SEARCH_MAPPER = Mappers.getMapper(TeamSearchMapper.class);

    Team toEntity(TeamSearchDto dto);
    TeamSearchDto toDto(Team entity);
}
