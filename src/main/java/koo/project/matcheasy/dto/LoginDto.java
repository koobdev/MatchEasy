package koo.project.matcheasy.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

}
