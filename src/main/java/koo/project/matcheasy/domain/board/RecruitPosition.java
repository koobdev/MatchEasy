package koo.project.matcheasy.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
