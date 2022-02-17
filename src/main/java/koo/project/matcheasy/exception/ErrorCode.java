package koo.project.matcheasy.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * TODO ::: Status 별로 정리
     */


    // @Valid
    NotNull(BAD_REQUEST, "필수 입력칸을 모두 입력해주세요."),
    NotBlank(BAD_REQUEST, "띄어쓰기를 허용하지 않습니다."),
    NotEmpty(BAD_REQUEST, "필수 입력칸을 모두 입력해주세요."),
//    BadCredentialException


    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    MEMBER_DUPLICATED(CONFLICT, "이미 가입된 회원입니다."),
    CONTENT_DUPLICATED(CONFLICT, "이미 작성된 게시글이 존재합니다."),
    CHATROOM_DUPLICATED(CONFLICT, "이미 생성된 채팅방이 존재합니다."),
    FAIL_MEMBER_AUTHORIZED(FORBIDDEN, "게시글은 작성자만 수정이 가능합니다."),




    REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, "토큰이 만료되었습니다. 다시 로그인하세요."),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    ;


    // EXAMPLE
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
//    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
//    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
//    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
//    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
//    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),


    private final HttpStatus status;
    private final String description;
}
