package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.chat.Chat;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.repository.ChatRepository;
import koo.project.matcheasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    /**
     * 채팅방 생성
     */
    public Chat createChatRoom(BoardDto boardDto){

        Member findWriter = memberRepository.findById(boardDto.getWriterId());

        return Chat.builder()
                .writerId(findWriter.getId())
                .writerName(findWriter.getName())
                .build();

        // websocket 작업

    }


    /**
     * 채팅방 멤버 추가
     */
    public void addMember(){

    }
}
