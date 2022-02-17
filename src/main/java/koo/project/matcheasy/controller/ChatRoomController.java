package koo.project.matcheasy.controller;

import koo.project.matcheasy.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
public class ChatRoomController {

    private final ChatRoomRepository repository;

    //채팅방 조회
    @GetMapping("/room")
    public void getRoom(String roomId){

        log.info("# get Chat Room, roomID : " + roomId);
    }
}
