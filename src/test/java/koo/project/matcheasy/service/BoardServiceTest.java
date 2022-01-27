package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.mapper.BoardMapper;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.repository.BoardRepository;
import koo.project.matcheasy.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    Logger log = (Logger) LoggerFactory.getLogger(BoardServiceTest.class);

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("")
    void entityConvertTest(){
        // given

        // position
        RecruitPosition recruitPosition = RecruitPosition.builder()
                .position("BE")
                .content("I WANT BE")
                .build();
        List<RecruitPosition> list = new ArrayList<>();
        list.add(recruitPosition);

        // member entity
        List<String> skills = new ArrayList<>();
        skills.add("Java");
        skills.add("Spring");
        skills.add("JPA");
        MemberDto memberDto = MemberDto.builder()
                .loginId("test")
                .password("test!")
                .name("테스트")
                .age(20)
                .email("aa@aa.com")
                .position("Back-End")
                .skills(skills)
                .build();

        Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(memberDto);


        BoardDto boardDto = BoardDto.builder()
                .title("TestTitle")
                .content("ccccoooonnnteeennntttt")
                .positions(list)
                .startdate(LocalDateTime.now())
                .enddate(LocalDateTime.now())
                .writer(memberEntity)
                .build();

        // when
        final BoardContent afterConvert = BoardMapper.BOARD_MAPPER.toEntity(boardDto);

        // then
        log.info("boardDTO : {}", boardDto.toString());
        log.info("title:{}, content:{}", afterConvert.getTitle(), afterConvert.getContent());
        assertThat(afterConvert.getTitle()).isEqualTo(boardDto.getTitle());

    }
}