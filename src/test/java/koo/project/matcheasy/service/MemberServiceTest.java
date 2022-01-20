package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.repository.MemberRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.DiscriminatorColumn;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("MapStruct Dto<->Entity 변환 테스트")
    void MapStructTest(){
        final MemberDto memberDto = MemberDto.builder()
                .loginId("test")
                .password("test!")
                .name("테스트")
                .age(20)
                .email("aa@aa.com")
                .build();

        final Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(memberDto);

        assertThat(memberDto.getLoginId()).isEqualTo(memberEntity.getLoginId());
        assertThat(memberDto.getPassword()).isEqualTo(memberEntity.getPassword());
        assertThat(memberDto.getName()).isEqualTo(memberEntity.getName());
        assertThat(memberDto.getAge()).isEqualTo(memberEntity.getAge());
        assertThat(memberDto.getEmail()).isEqualTo(memberEntity.getEmail());
    }


    @Test
    @DisplayName("회원가입")
    void joinTest(){

        final MemberDto memberDto = MemberDto.builder()
                .loginId("test")
                .password("test!")
                .name("테스트")
                .age(20)
                .email("aa@aa.com")
                .build();

        Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(memberDto);
        memberRepository.save(memberEntity);
        Optional<Member> findMember = memberRepository.findByLoginId("test");


        assertThat(memberEntity.getLoginId()).isEqualTo(findMember.get().getLoginId());
    }

    @Test
    void findMember(){

    }
}