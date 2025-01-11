package com.web.thuvien.controller;

import com.web.thuvien.model.dto.BookDTO;
import com.web.thuvien.model.response.BookResponse;
import com.web.thuvien.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book")
    public ResponseEntity<List<BookResponse>> findAll(@ModelAttribute BookDTO bookDTO){
        return ResponseEntity.ok(bookService.findAll(bookDTO));
    }
}
