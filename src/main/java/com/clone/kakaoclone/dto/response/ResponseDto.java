package com.clone.kakaoclone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;
    private List<T> list;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null,null);
    }

    public static <T> ResponseDto<T> fail(String code, String message) {
        return new ResponseDto<>(false, null, new Error(code, message),null);
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private String code;
        private String message;
    }

    public ResponseDto(boolean response,List<T> list) {
        this.success = response;
        this.list = list;
    }

}