package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.member.MemberSkills;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.dto.MemberSkillsDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
//        (componentModel = "spring", uses = MemberSkillsMapper.class)
public interface MemberMapper extends EntityDtoMapper<MemberDto, Member>{

    MemberMapper MEMBER_MAPPER = Mappers.getMapper(MemberMapper.class);
    MemberSkillsMapper memberSkillsMapper = Mappers.getMapper(MemberSkillsMapper.class);

    Member toEntity(MemberDto dto, List<MemberSkillsDto> memberSkills);
//    default Member add(MemberDto dto){
//        if(dto == null){
//            return null;
//        }
//
//        Member member = new Member();
//        if(dto.getMemberSkills() != null){
//            for(MemberSkillsDto memberSkillsDto : dto.getMemberSkills()){
//                member.addMemberSkills(memberSkillsMapper.toEntity(memberSkillsDto));
//            }
//        }
//
//        return member;
//    }

    MemberDto toDto(Member entity);
}
