package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.team.Team;
import koo.project.matcheasy.dto.TeamDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
        (collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
                uses = MemberMapper.class)
public interface TeamMapper{

    TeamMapper TEAM_MAPPER = Mappers.getMapper(TeamMapper.class);
    MemberMapper membersMapper = Mappers.getMapper(MemberMapper.class);
//    TeamMemberMapper membersMapper = Mappers.getMapper(TeamMemberMapper.class);

    Team toEntity(TeamDto dto);
    TeamDto toDto(Team entity);
}
