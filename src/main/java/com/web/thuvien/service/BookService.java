package com.web.thuvien.service;

import com.web.thuvien.model.dto.BookDTO;
import com.web.thuvien.model.response.BookResponse;

import java.util.List;

public interface BookService {
    public List<BookResponse> findAll(BookDTO bookDTO);
}
