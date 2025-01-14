package com.web.thuvien.repository;

import com.web.thuvien.model.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByBookEntity_Id(Long BookId);
}
