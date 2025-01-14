package com.web.thuvien.service;

import com.web.thuvien.model.dto.BookDTO;
import com.web.thuvien.model.response.BookResponse;
import com.web.thuvien.model.response.FileResponse;
import com.web.thuvien.model.response.ImageResponse;

import java.util.List;

public interface BookService {
    public List<BookResponse> findAll(BookDTO bookDTO);
    public BookResponse findById(Long id);

    public String addBook(BookDTO bookDTO);
    public String editBook(BookDTO bookDTO);
    public String deleteBook(Long id);


}
