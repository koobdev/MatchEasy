package koo.project.matcheasy.domain.board;

import javax.persistence.*;

@Entity
public class RecruitPosition {

    @Id @GeneratedValue
    @Column(name = "POSITION_ID")
    private Long id;
    private String position;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private BoardContent boardContent;
}
