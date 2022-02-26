package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.member.MemberSkills;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.dto.MemberSkillsDto;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Mapper
        (collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
                uses = TeamMapper.class)
public interface TeamMemberMapper{

    TeamMemberMapper TEAM_MEMBER_MAPPER = Mappers.getMapper(TeamMemberMapper.class);

    //    @Mapping(target = "memberSkills", ignore = true)
    Member toEntity(MemberDto dto);

    MemberDto toDto(Member entity);
}
