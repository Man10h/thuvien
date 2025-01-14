package com.web.thuvien.repository;

import com.web.thuvien.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByBookEntity_Id(Long bookId);
}
