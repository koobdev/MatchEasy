package koo.project.matcheasy.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    @NotNull
    private int age;
    @NotEmpty
    private String email;
    @NotEmpty
    private String position;
    @NotEmpty
    private List<String> skills;

}
