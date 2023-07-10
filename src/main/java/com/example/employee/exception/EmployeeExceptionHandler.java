package com.example.employee.exception;

import com.example.employee.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.FileAlreadyExistsException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class EmployeeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> dataNotFoundException(DataNotFoundException dataNotFoundException){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(dataNotFoundException.getMessage(),ZonedDateTime.now(ZoneId.of("Z")),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponseDto> illegalArgumentExceptionHandler(IllegalArgumentException exception, WebRequest request){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(exception.getMessage(),ZonedDateTime.now(ZoneId.of("Z")),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FileAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponseDto> fileAlreadyExistsHandler(FileAlreadyExistsException fe){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("File Already Exists With The Same Name",ZonedDateTime.now(ZoneId.of("Z")),HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(exceptionResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ExceptionResponseDto> maxUploadSizeExceededException(MaxUploadSizeExceededException  maxUploadSizeExceededException){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(maxUploadSizeExceededException.getMessage(),ZonedDateTime.now(ZoneId.of("Z")),HttpStatus.EXPECTATION_FAILED,HttpStatus.EXPECTATION_FAILED.value());
        return new ResponseEntity<>(exceptionResponseDto,HttpStatus.EXPECTATION_FAILED);
    }
}
