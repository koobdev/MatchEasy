package koo.project.matcheasy.domain.board;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import koo.project.matcheasy.domain.chat.Chat;
import koo.project.matcheasy.domain.chat.ChatRoom;
import koo.project.matcheasy.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardContent {

//    @Builder
//    public BoardContent(Long id, String title, String content, Chat chat, Long writerId, LocalDateTime startdate, LocalDateTime enddate, LocalDateTime regdate, LocalDateTime moddate) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.chat = chat;
//        this.writerId = writerId;
//        this.startdate = startdate;
//        this.enddate = enddate;
//        this.regdate = regdate;
//        this.moddate = moddate;
//    }

    @Id @GeneratedValue
    @Column(name = "CONTENT_ID")
    private Long id;
    private String title;
    private String content;

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "boardContent", cascade = CascadeType.ALL)
    private List<RecruitPosition> positions = new ArrayList<>();

    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CHAT_ID")
    private ChatRoom chatRoom;

    private Long writerId;
    private int status;

    private LocalDateTime startdate;
    private LocalDateTime enddate;

    @Column(
            updatable = false, insertable = false, nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime regdate;

    @Column(
            updatable = false, insertable = false, nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    private LocalDateTime moddate;


    // 연관관계 편의 메서드
    public void addRecruitPosition(RecruitPosition position){
        positions.add(position);
        position.builder().boardContent(this);
    }

    public void addChatRoom(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
        chatRoom.builder().boardContent(this);
    }

    // Update status
    public void updateStatus(int status){
        this.status = status;
    }
}
