package koo.project.matcheasy.dto;

import koo.project.matcheasy.exception.ErrorCode;
import koo.project.matcheasy.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OkResponse {

    private final int status;
    private final String code;
    private final String message;
    private final Object data;

    public static ResponseEntity<OkResponse> toResponse(String returnData, String message){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OkResponse.builder()
                        .status(200)
                        .code("OK")
                        .message(message)
                        .data(returnData)
                        .build()
                );
    }

    public static ResponseEntity<OkResponse> toJSonResponse(JSONArray jsonArray, String message){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OkResponse.builder()
                        .status(200)
                        .code("OK")
                        .message(message)
                        .data(jsonArray)
                        .build()
                );
    }


    public static ResponseEntity<OkResponse> toListResponse(List<Object> list, String message){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OkResponse.builder()
                        .status(200)
                        .code("OK")
                        .message(message)
                        .data(list)
                        .build()
                );
    }
}
