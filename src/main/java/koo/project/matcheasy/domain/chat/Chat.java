package koo.project.matcheasy.domain.chat;

import koo.project.matcheasy.domain.board.BoardContent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Chat {

    @Id @GeneratedValue
    private Long id;
    private String writer;
    private String content;

    @OneToOne
    private BoardContent boardContent;
}
