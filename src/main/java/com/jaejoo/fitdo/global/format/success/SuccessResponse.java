package com.jaejoo.fitdo.global.format.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"time", "status", "code", "message", "result"})
public class SuccessResponse<T> {

    @JsonProperty("status")
    private int status;
    private String time;
    private String code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    @Builder
    public SuccessResponse(int status, LocalDateTime time, String code, String message) {
        this.status = status;
        this.time = time.toString();
        this.code = code;
        this.message = message;
    }

    //성공의 경우
    public SuccessResponse(T result) {
        this.status = HttpStatus.OK.value();
        this.time = now().toString();
        this.code = SuccessResponseStatus.SUCCESS.getCode();
        this.message = SuccessResponseStatus.SUCCESS.getMessage();
        this.result = result;
    }

    public static SuccessResponse ok(String message) {
        return new SuccessResponse(message);
    }

    public static SuccessResponse ok() {
        return SuccessResponse.ok("SUCCESS");
    }

    public SuccessResponse(int status, T result) {
        this.status = status;
        this.time = now().toString();
        this.code = SuccessResponseStatus.SUCCESS.getCode();
        this.message = SuccessResponseStatus.SUCCESS.getMessage();
        this.result = result;
    }
}
