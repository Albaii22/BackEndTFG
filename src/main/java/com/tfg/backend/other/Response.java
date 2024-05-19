package com.tfg.backend.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    String message;
    T bodyDto;
    Boolean result;
    Object object;

    public Response(String message, T bodyDto, Boolean result) {
        this.message = message;
        this.bodyDto = bodyDto;
        this.result = result;
    }

    public Response(String message, Boolean result) {
        this.message = message;
        this.result = result;
    }

    public Response(String message, Object object) {
        this.message = message;
        this.object = object;
    }

    public Response(String message, T bodyDto, Object object) {
        this.message = message;
        this.bodyDto = bodyDto;
        this.object = object;
    }

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
