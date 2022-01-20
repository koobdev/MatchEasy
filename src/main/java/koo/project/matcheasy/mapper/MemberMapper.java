package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper MEMBER_MAPPER = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "id", constant = "0L")
    Member toEntity(MemberDto dto);

    MemberDto toDto(Member entity);
}
