package com.web.thuvien.convert;

import com.web.thuvien.model.entity.BookEntity;
import com.web.thuvien.model.entity.FileEntity;
import com.web.thuvien.model.entity.ImageEntity;
import com.web.thuvien.model.enums.TypeEnum;
import com.web.thuvien.model.response.BookResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BookConvert {

    @Autowired
    private ModelMapper modelMapper;


    public BookResponse convertToBookResponse(BookEntity bookEntity) {
        BookResponse bookResponse = modelMapper.map(bookEntity, BookResponse.class);
        // types
        String[] s = bookEntity.getType().split(",");
        List<String> types = new ArrayList<String>();
        for(String x: s){
            types.add(TypeEnum.getTypeEnumMap().get(x.trim()));
        }
        bookResponse.setTypes(types);
        // images
        List<String> images = new ArrayList<>();
        List<ImageEntity> imageEntities = bookEntity.getImageEntities();
        for(ImageEntity x: imageEntities){
            images.add("https://res.cloudinary.com/djuq2enmy/image/upload/" + x.getFileId());
        }
        bookResponse.setImages(images);
        // files
        List<String> files = new ArrayList<>();
        List<FileEntity> fileEntities = bookEntity.getFileEntities();
        for(FileEntity x: fileEntities){
            files.add("https://res.cloudinary.com/djuq2enmy/raw/upload" + x.getFileId());
        }
        bookResponse.setFiles(files);
        return bookResponse;
    }
}
