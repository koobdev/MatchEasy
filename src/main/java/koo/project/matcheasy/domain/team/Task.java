package koo.project.matcheasy.domain.team;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class Task {
    private String goal; // 목표
    private boolean status; // 완료여부
}
