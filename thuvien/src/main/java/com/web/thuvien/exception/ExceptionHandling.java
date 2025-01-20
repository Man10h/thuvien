package com.web.thuvien.exception;

import com.web.thuvien.exception.ex.*;
import com.web.thuvien.model.dto.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO BookNotFoundException(BookNotFoundException e, WebRequest request) {
        ErrorMessageDTO dto = new ErrorMessageDTO();
        dto.setMessage(e.getMessage());
        dto.setCode(10100L);
        return dto;
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO FileNotFoundException(FileNotFoundException e, WebRequest request) {
        ErrorMessageDTO dto = new ErrorMessageDTO();
        dto.setMessage(e.getMessage());
        dto.setCode(10100L);
        return dto;
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO BookNotFoundException(ImageNotFoundException e, WebRequest request) {
        ErrorMessageDTO dto = new ErrorMessageDTO();
        dto.setMessage(e.getMessage());
        dto.setCode(10100L);
        return dto;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO UserNotFoundException(UserNotFoundException e, WebRequest request) {
        ErrorMessageDTO dto = new ErrorMessageDTO();
        dto.setMessage(e.getMessage());
        dto.setCode(10100L);
        return dto;
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO UserExistsException(UserNotFoundException e, WebRequest request) {
        ErrorMessageDTO dto = new ErrorMessageDTO();
        dto.setMessage(e.getMessage());
        dto.setCode(10101L);
        return dto;
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO RoleNotFoundException(UserNotFoundException e, WebRequest request) {
        ErrorMessageDTO dto = new ErrorMessageDTO();
        dto.setMessage(e.getMessage());
        dto.setCode(10100L);
        return dto;
    }
}
