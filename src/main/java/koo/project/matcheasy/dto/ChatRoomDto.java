package koo.project.matcheasy.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoomDto {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();
}
