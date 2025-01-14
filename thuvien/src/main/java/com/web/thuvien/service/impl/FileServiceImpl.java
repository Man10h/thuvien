package com.web.thuvien.service.impl;

import com.web.thuvien.convert.FileConvert;
import com.web.thuvien.exception.ex.BookNotFoundException;
import com.web.thuvien.exception.ex.FileNotFoundException;
import com.web.thuvien.model.dto.FileDTO;
import com.web.thuvien.model.entity.BookEntity;
import com.web.thuvien.model.entity.FileEntity;
import com.web.thuvien.model.entity.ImageEntity;
import com.web.thuvien.model.response.FileResponse;
import com.web.thuvien.repository.BookRepository;
import com.web.thuvien.repository.FileRepository;
import com.web.thuvien.service.CloudinaryService;
import com.web.thuvien.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FileConvert fileConvert;

    @Transactional(readOnly = true)
    public List<FileResponse> getFiles(Long bookId) {
        List<FileEntity> fileEntities = fileRepository.findByBookEntity_Id(bookId);
        if (fileEntities.isEmpty()){
            return null;
        }
        return fileEntities.stream().map(it -> fileConvert.convertToFileResponse(it)).toList();
    }

    @Transactional(readOnly = true)
    public FileResponse getFileById(Long fileId) {
        Optional<FileEntity> optional = fileRepository.findById(fileId);
        if (optional.isEmpty()){
            throw new FileNotFoundException("File not found");
        }
        return fileConvert.convertToFileResponse(optional.get());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public String addFile(FileDTO fileDTO) {
        if(fileDTO.getBookId() == null || fileDTO.getFiles().isEmpty()){
            return null;
        }
        Optional<BookEntity> optional = bookRepository.findById(fileDTO.getBookId());
        if(optional.isEmpty()){
            throw new BookNotFoundException("Book not found");
        }
        BookEntity bookEntity = optional.get();
        for(MultipartFile multipartFile : fileDTO.getFiles()){
            Map result = cloudinaryService.upload(multipartFile);
            FileEntity fileEntity = FileEntity.builder()
                    .bookEntity(bookEntity)
                    .fileId((String) result.get("public_id"))
                    .fileName((String) result.get("original_filename"))
                    .fileUrl((String)result.get("url"))
                    .build();
            fileEntity.setBookEntity(bookEntity);
            fileRepository.save(fileEntity);
        }
        return "Add file successfully";
    }

    @Override
    public String deleteFile(Long fileId) {
        Optional<FileEntity> optional = fileRepository.findById(fileId);
        if(optional.isEmpty()){
            throw new FileNotFoundException("File not found");
        }
        fileRepository.deleteById(fileId);
        return "delete file successfully";
    }

}
