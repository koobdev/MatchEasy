package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.domain.chat.Chat;
import koo.project.matcheasy.domain.chat.ChatRoom;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.mapper.BoardContext;
import koo.project.matcheasy.mapper.BoardMapper;
import koo.project.matcheasy.repository.ChatRepository;
import koo.project.matcheasy.repository.ChatRoomRepository;
import koo.project.matcheasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static koo.project.matcheasy.exception.ErrorCode.CHATROOM_DUPLICATED;
import static koo.project.matcheasy.exception.ErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    /**
     * 채팅방 생성
     * 게시글이 작성되면 자동으로 createChatRoom이 실행됨
     */
    public void createChatRoom(BoardContent content){

        Member findWriter = validChatRoom(content.getWriterId());

        ChatRoom chatRoom = ChatRoom.builder()
                .writerId(findWriter.getId())
                .writerName(findWriter.getName())
                .build();
        content.addChatRoom(chatRoom);
    }


    /**
     * 1. 작성자 null 체크
     * 2. 채팅방 중복 체크 (작성자는 한개의 채팅방(혹은 한개의 게시글)만 생성할 수 있다.)
     */
    public Member validChatRoom(Long id){

        // 1. Member Check
        Member findMember = memberRepository.findById(id);

        if(Objects.isNull(findMember)){
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        // 2. ChatRoom Check
        chatRoomRepository.findByWriterId(id)
                .ifPresent(r -> {
                    throw new CustomException(CHATROOM_DUPLICATED);
                });

        return findMember;
    }
}
