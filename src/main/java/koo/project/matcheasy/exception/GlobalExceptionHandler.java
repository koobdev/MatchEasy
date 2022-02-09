package koo.project.matcheasy.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @valid  유효성체크에 통과하지 못했을 때 처리
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> methodValidException(BindException e, HttpServletRequest request){
        log.warn("BindException 발생 >>>>>> url:{}, trace:{}",request.getRequestURI(), e.getStackTrace());
        return ErrorResponse.toValidResponse(e);
    }

    /**
     * CustomException 일 때 처리
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customException(CustomException e, HttpServletRequest request){
        log.warn("customException 발생 >>>>>> url:{}, errorCode:{}, trace:{}",request.getRequestURI(), e.getErrorCode() ,e.getStackTrace());
        return ErrorResponse.toResponse(e.getErrorCode());
    }



//    private ErrorResponse makeErrorResponse(BindingResult bindingResult){
//        String code = "";
//        String description = "";
//        String detail = "";
//
//        //에러가 있다면
//        if(bindingResult.hasErrors()){
//            detail = bindingResult.getFieldError().getDefaultMessage();
//
//            //DTO에 유효성체크를 걸어놓은 어노테이션명을 가져온다.
//            String bindResultCode = bindingResult.getFieldError().getCode();
//
//            switch (bindResultCode){
//                case "NotNull":
//                case "NotBlank":
//                case "NotEmpty":
//                    code = ErrorCode.NOT_NULL.getCode();
//                    description = ErrorCode.NOT_NULL.getDescription();
//                    // 다국어 처리를 할 경우 아래 코드를 사용
//                    // description = messageSource.getMessage(ErrorCode.NOT_NULL.getDescription(), null, locale);
//                    break;
//            }
//        }
//
//        return new ErrorResponse(code, description, detail);
//    }
}
