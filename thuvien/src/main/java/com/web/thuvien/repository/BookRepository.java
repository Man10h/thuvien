package com.web.thuvien.repository;

import com.web.thuvien.model.entity.BookEntity;
import com.web.thuvien.repository.custom.BookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>, BookRepositoryCustom {

}
