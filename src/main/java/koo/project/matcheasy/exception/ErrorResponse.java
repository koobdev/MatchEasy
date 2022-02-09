package koo.project.matcheasy.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.ibatis.binding.BindingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponse(ErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus().value())
                        .error(errorCode.getStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDescription())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toValidResponse(BindException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String description = ErrorCode.valueOf(e.getFieldError().getCode()).getDescription();

        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .status(status.value())
                        .error(status.name())
                        .code(e.getFieldError().getCode())
                        .message(description)
                        .build()
                );
    }
}
