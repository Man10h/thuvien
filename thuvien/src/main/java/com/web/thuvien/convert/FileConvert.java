package com.web.thuvien.convert;

import com.web.thuvien.model.entity.FileEntity;
import com.web.thuvien.model.response.FileResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class FileConvert {

    @Autowired
    private ModelMapper modelMapper;

    public FileResponse convertToFileResponse(FileEntity fileEntity){
        return modelMapper.map(fileEntity, FileResponse.class);
    }
}
