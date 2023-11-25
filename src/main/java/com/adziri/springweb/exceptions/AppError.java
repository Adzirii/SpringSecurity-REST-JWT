package com.adziri.springweb.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.util.Date;

@Data
public class AppError {
    private Integer status;
    private String message;
    private Date timestamp;

    public AppError(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
