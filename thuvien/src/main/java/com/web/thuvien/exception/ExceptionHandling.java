package com.web.thuvien.exception;

import com.web.thuvien.exception.ex.BookNotFoundException;
import com.web.thuvien.exception.ex.FileNotFoundException;
import com.web.thuvien.exception.ex.ImageNotFoundException;
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
}
