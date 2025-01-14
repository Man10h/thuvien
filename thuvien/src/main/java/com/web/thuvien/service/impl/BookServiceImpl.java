package com.web.thuvien.service.impl;

import com.web.thuvien.convert.BookConvert;
import com.web.thuvien.convert.FileConvert;
import com.web.thuvien.convert.ImageConvert;
import com.web.thuvien.exception.ex.BookNotFoundException;
import com.web.thuvien.model.dto.BookDTO;
import com.web.thuvien.model.entity.BookEntity;
import com.web.thuvien.model.entity.FileEntity;
import com.web.thuvien.model.entity.ImageEntity;
import com.web.thuvien.model.response.BookResponse;
import com.web.thuvien.repository.BookRepository;
import com.web.thuvien.repository.FileRepository;
import com.web.thuvien.repository.ImageRepository;
import com.web.thuvien.service.BookService;
import com.web.thuvien.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private BookConvert bookConvert;

    @Autowired
    private FileConvert fileConvert;

    @Autowired
    private ImageConvert imageConvert;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public List<BookResponse> findAll(BookDTO bookDTO) {
        List<BookEntity> bookEntities = bookRepository.findAll();
        if(bookEntities.isEmpty()){
            return null;
        }
        return bookEntities.stream().
                map(it -> bookConvert.convertToBookResponse(it))
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        Optional<BookEntity> optional = bookRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        }
        return bookConvert.convertToBookResponse(optional.get());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = {Exception.class})
    public String addBook(BookDTO bookDTO) {
        if(bookDTO.getId() == null){
            List<String> types = bookDTO.getTypes();
            BookEntity bookEntity = BookEntity.builder()
                    .name(bookDTO.getName())
                    .author(bookDTO.getAuthor())
                    .description(bookDTO.getDescription())
                    .type(String.join(", ", types))
                    .likeCount(bookDTO.getLikeCount())
                    .build();
            List<FileEntity> fileEntities = new ArrayList<>();
            List<ImageEntity> imageEntities = new ArrayList<>();
            for(MultipartFile multipartFile : bookDTO.getImages()){
                Map result = cloudinaryService.upload(multipartFile);
                ImageEntity imageEntity = ImageEntity.builder()
                        .bookEntity(bookEntity)
                        .fileId((String) result.get("public_id"))
                        .fileName((String) result.get("original_filename"))
                        .fileUrl((String)result.get("url"))
                        .build();
                imageEntities.add(imageEntity);
            }
            for(MultipartFile multipartFile : bookDTO.getFiles()){
                Map result = cloudinaryService.upload(multipartFile);
                FileEntity fileEntity = FileEntity.builder()
                        .bookEntity(bookEntity)
                        .fileId((String) result.get("public_id"))
                        .fileName((String) result.get("original_filename"))
                        .fileUrl((String)result.get("url"))
                        .build();
                fileEntities.add(fileEntity);
            }
            bookEntity.setFileEntities(fileEntities);
            bookEntity.setImageEntities(imageEntities);
            bookRepository.save(bookEntity);
            return "Add book successfully";
        }
        return "Add book without id";
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class})
    public String editBook(BookDTO bookDTO) {
        if(bookDTO.getId() == null){
            throw new BookNotFoundException("Book not found");
        }
        Optional<BookEntity> optional = bookRepository.findById(bookDTO.getId());
        if (optional.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        }
        BookEntity bookEntity = optional.get();
        if(bookDTO.getLikeCount() != null){
            bookEntity.setLikeCount(bookDTO.getLikeCount());
        }
        if(bookDTO.getAuthor() != null){
            bookEntity.setAuthor(bookDTO.getAuthor());
        }
        if(bookDTO.getDescription() != null){
            bookEntity.setDescription(bookDTO.getDescription());
        }
        if(bookDTO.getName() != null){
            bookEntity.setName(bookDTO.getName());
        }
        if(!bookDTO.getTypes().isEmpty()){
            bookEntity.setType(String.join(", ", bookDTO.getTypes()));
        }
        return "Edit book successfully";
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = {Exception.class})
    public String deleteBook(Long id) {
        Optional<BookEntity> optional = bookRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
        return "Delete book successfully";
    }

}
