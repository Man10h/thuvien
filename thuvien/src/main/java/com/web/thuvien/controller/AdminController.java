package com.web.thuvien.controller;

import com.web.thuvien.model.dto.BookDTO;
import com.web.thuvien.model.response.FileResponse;
import com.web.thuvien.model.response.ImageResponse;
import com.web.thuvien.service.BookService;
import com.web.thuvien.service.FileService;
import com.web.thuvien.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private FileService fileService;

    @PostMapping("/book")
    public ResponseEntity<String> addBook(@ModelAttribute BookDTO bookDTO){
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @PutMapping("/book")
    public ResponseEntity<String> updateBook(@ModelAttribute BookDTO bookDTO){
        return ResponseEntity.ok(bookService.editBook(bookDTO));
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId){
        return ResponseEntity.ok(bookService.deleteBook(bookId));
    }

    @GetMapping("/book/image")
    public ResponseEntity<List<ImageResponse>> getBookImage(@RequestParam Long bookId){
        return ResponseEntity.ok(imageService.getImages(bookId));
    }

    @GetMapping("/book/file")
    public ResponseEntity<List<FileResponse>> getBookFile(@RequestParam Long bookId){
        return ResponseEntity.ok(fileService.getFiles(bookId));
    }

}
