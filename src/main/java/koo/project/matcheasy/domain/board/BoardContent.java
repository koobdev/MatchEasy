package koo.project.matcheasy.domain.board;

import koo.project.matcheasy.domain.chat.Chat;
import koo.project.matcheasy.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class BoardContent {

    @Builder
    public BoardContent(Long id, String title, String content, Chat chat, Long writerId, LocalDateTime startdate, LocalDateTime enddate, LocalDateTime regdate, LocalDateTime moddate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.chat = chat;
        this.writerId = writerId;
        this.startdate = startdate;
        this.enddate = enddate;
        this.regdate = regdate;
        this.moddate = moddate;
    }

    @Id @GeneratedValue
    @Column(name = "CONTENT_ID")
    private Long id;
    private String title;
    private String content;

    @OneToMany
    @JoinColumn(name = "CONTENT_ID")
    private List<RecruitPosition> positionList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "CHAT_ID")
    private Chat chat;

    private Long writerId;

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
        positionList.add(position);
    }
}
