package koo.project.matcheasy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeamServiceTest {

    Logger log = (Logger) LoggerFactory.getLogger(BoardServiceTest.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("일일 일정 추가하기 테스트")
    void daylyRegisterTestWithMock() throws Exception {

        // given
        // when
        Map<String, Object> dayly = new HashMap<>();
        dayly.put("day", 28);
        dayly.put("goal", "this is our goal");
        dayly.put("status", 0);
        dayly.put("teamId", 23);

//        teamService.registerDayly();


        // then
        mockMvc.perform(
                post("/test/team/dayly/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dayly))
        )
                .andExpect(status().isOk())
                .andDo(print());

    }

}