package koo.project.matcheasy.domain.chat;

import koo.project.matcheasy.domain.board.BoardContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id @GeneratedValue
    private Long id;
    private Long writerId;
    private String writerName;
    private String content;
}
