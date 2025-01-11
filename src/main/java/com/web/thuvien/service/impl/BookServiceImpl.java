package com.web.thuvien.service.impl;

import com.web.thuvien.convert.BookConvert;
import com.web.thuvien.model.dto.BookDTO;
import com.web.thuvien.model.response.BookResponse;
import com.web.thuvien.repository.BookRepository;
import com.web.thuvien.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookConvert bookConvert;

    @Override
    public List<BookResponse> findAll(BookDTO bookDTO) {
        return bookRepository.findAll(bookDTO).stream().
                map(it -> bookConvert.convertToBookResponse(it))
                .toList();
    }
}
