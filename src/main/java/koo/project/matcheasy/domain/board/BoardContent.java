package koo.project.matcheasy.domain.board;

import koo.project.matcheasy.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BoardContent {

    @Id @GeneratedValue
    @Column(name = "CONTENT_ID")
    private Long id;
    private String title;
    private String content;

    @OneToMany(mappedBy = "boardContent")
    private List<RecruitPosition> positionList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

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
}
