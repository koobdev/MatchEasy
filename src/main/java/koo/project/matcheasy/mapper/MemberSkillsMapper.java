package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.member.MemberSkills;
import koo.project.matcheasy.dto.MemberSkillsDto;
import org.mapstruct.Mapper;

@Mapper
public interface MemberSkillsMapper extends EntityDtoMapper<MemberSkillsDto, MemberSkills>{

    MemberSkillsDto toDto(MemberSkills memberSkills);

//    MemberSkills toEntity(MemberSkillsDto memberSkillsDto);
    MemberSkills toEntity(MemberSkillsDto memberSkillsDto);
}
