package koo.project.matcheasy.domain.chat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import koo.project.matcheasy.domain.board.BoardContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id @GeneratedValue
    private Long id;
    private Long writerId;
    private String writerName;

    @JsonManagedReference
    @OneToOne(mappedBy = "chatRoom")
    private BoardContent boardContent;

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
