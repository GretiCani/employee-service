package com.icub.task.employee.commons.exception;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Date;

@ToString
@Data
public class ExceptionResponse {

    private HttpStatus status;
    private Object message;
    private Date timestamp;

    public ExceptionResponse(HttpStatus status, Object message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
