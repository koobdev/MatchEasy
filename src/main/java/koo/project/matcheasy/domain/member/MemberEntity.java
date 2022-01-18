package koo.project.matcheasy.domain.member;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class MemberEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String password;
}
