package com.example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDto {
    private String message;
    private ZonedDateTime zonedDateTime;
    private HttpStatus httpStatus;
    private int statusCode;
}
