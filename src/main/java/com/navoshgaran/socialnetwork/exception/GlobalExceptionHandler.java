package com.navoshgaran.socialnetwork.exception;

import com.navoshgaran.socialnetwork.dto.ExceptionDto;
import jakarta.servlet.ServletException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OperationException.class)
    private ResponseEntity<ExceptionDto> operationExceptionHandler(OperationException o){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(o.getMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(DuplicateException.class)
    private ResponseEntity<ExceptionDto> duplicateExceptionHandler(DuplicateException d){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(d.getMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ExceptionDto> notFoundException(NotFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(InvalidInputException.class)
    private ResponseEntity<ExceptionDto> invalidInputException(InvalidInputException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }


    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ExceptionDto> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(e.getAllErrors().get(0).getDefaultMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(BindException.class)
    private ResponseEntity<ExceptionDto> bindException(BindException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(e.getAllErrors().get(0).getDefaultMessage())
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    private ResponseEntity<ExceptionDto> fileSizeLimitExceededException(FileSizeLimitExceededException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message("Too Large File")
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ExceptionDto> accessDeniedException(AccessDeniedException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(ServletException.class)
    private ResponseEntity<ExceptionDto> servletException(ServletException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }
}
