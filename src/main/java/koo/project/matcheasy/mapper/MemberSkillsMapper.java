package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.member.MemberSkills;
import koo.project.matcheasy.dto.MemberSkillsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
//        (uses = MemberMapper.class)
public interface MemberSkillsMapper {

    MemberSkillsMapper MEMBER_SKILLS_MAPPER = Mappers.getMapper(MemberSkillsMapper.class);

    MemberSkillsDto toDto(MemberSkills memberSkills);

//    MemberSkills toEntity(MemberSkillsDto memberSkillsDto);
    @Mapping(target = "member", ignore = true)
    MemberSkills toEntity(MemberSkillsDto memberSkillsDto);
}
