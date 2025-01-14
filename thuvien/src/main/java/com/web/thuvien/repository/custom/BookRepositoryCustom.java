package com.web.thuvien.repository.custom;

import com.web.thuvien.model.dto.BookDTO;
import com.web.thuvien.model.entity.BookEntity;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface BookRepositoryCustom {
    public List<BookEntity> findAll(BookDTO bookDTO);
}
