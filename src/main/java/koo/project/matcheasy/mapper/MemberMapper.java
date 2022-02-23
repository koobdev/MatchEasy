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
//        (uses = MemberSkillsMapper.class)
public interface MemberMapper{

    Logger log = (Logger) LoggerFactory.getLogger(MemberMapper.class);

    MemberMapper MEMBER_MAPPER = Mappers.getMapper(MemberMapper.class);
//    MemberSkillsMapper memberSkillsMapper = Mappers.getMapper(MemberSkillsMapper.class);

//    @Mapping(target = "memberSkills", ignore = true)
    Member toEntity(MemberDto dto);
//    default Member toEntity(MemberDto dto){
//        if(dto == null){
//            return null;
//        }
//
//        Member member = new Member();
//
//        log.info("dto.getMemberSkills :::::::::: {}", dto.getMemberSkills());
//
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
