package koo.project.matcheasy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.team.Team;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.dto.MemberSkillsDto;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.repository.MemberRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ObjectUtils;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.EntityManager;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(SpringExtension.class)
//@Rollback(value = false)
@AutoConfigureMockMvc
@SpringBootTest
class MemberServiceTest {

    Logger log = (Logger) LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private EntityManager em;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


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
    public void joinTest2(){
        // given
        final MemberDto memberDto = MemberDto.builder()
                .loginId("test")
                .password("test!")
                .name("테스트")
                .age(20)
                .email("aa@aa.com")
                .build();

        // when
        memberService.join(memberDto);

        //then
        Optional<Member> findMember = memberRepository.findByLoginId("test");
        assertThat(memberDto.getLoginId()).isEqualTo(findMember.get().getLoginId());
    }

    @Test
    @DisplayName("")
    void teamAddTest(){
        // given
        Optional<Member> optionalMember = memberRepository.findByLoginId("test");
        Member member = optionalMember.get();
        Team team = new Team();
        team.addMember(member);

        // when
        // then
        assertThat(member.getTeam()).isEqualTo(team.getId());
    }


    @Test
    @DisplayName("")
    void findMemberSkills(){
        // given
        Member findMember = em.find(Member.class, 1L);
        log.info("findMember >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("memberName : {}, memberLoginId : {}", findMember.getName(), findMember.getLoginId());

        // when, then
//        List<String> getSkills = findMember.getSkills();
//        log.info("getSkiils >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//        for (String s : getSkills) {
//            log.info("skill : {} / ", s);
//        }

    }

    @Test
    @DisplayName("skill과 함께 회원가입")
    void memberJoinTestWithMemberSkills () throws Exception {
        // given

        List<Map<String, Object>> skill = new ArrayList<>();
        Map<String, Object> skill1 = new HashMap<>();
        skill1.put("skill", "Java");
        Map<String, Object> skill2 = new HashMap<>();
        skill2.put("skill", "Spring");
        Map<String, Object> skill3 = new HashMap<>();
        skill3.put("skill", "JPA");
        skill.add(skill1);
        skill.add(skill2);
        skill.add(skill3);

        Map<String, Object> member = new HashMap<>();
        member.put("loginId", "test");
        member.put("password", "test!");
        member.put("name", "테스트");
        member.put("age", 20);
        member.put("email", "aa@aa.com");
        member.put("position", "Back-End");
        member.put("skills", skill);

        mockMvc.perform(
                    post("/members/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(member))
                )
                .andExpect(status().isOk())
                .andDo(print());

        // when


        // then


    }
}