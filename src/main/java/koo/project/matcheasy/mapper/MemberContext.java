package koo.project.matcheasy.mapper;


import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.member.MemberSkills;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemberContext {

    private Member member;

    @BeforeMapping
    public void setEntity(@MappingTarget Member member){
        this.member = member;
    }

    @AfterMapping
    public void establishRelation(@MappingTarget MemberSkills memberSkills){
        memberSkills.addMember(member);
    }
}
