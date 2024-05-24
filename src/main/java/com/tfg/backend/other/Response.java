package com.tfg.backend.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// Generic response object
public class Response<T> {
    String message; // Message describing the response
    T bodyDto; // Data object associated with the response
    Boolean result; // Result status of the operation
    Object object; // Additional object associated with the response

    // Constructors with different combinations of parameters
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

    // Getter and setter for the message field
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
