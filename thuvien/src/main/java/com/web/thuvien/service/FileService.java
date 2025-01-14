package com.web.thuvien.service;

import com.web.thuvien.model.dto.FileDTO;
import com.web.thuvien.model.response.FileResponse;

import java.util.List;

public interface FileService {
    public List<FileResponse> getFiles(Long bookId);
    public FileResponse getFileById(Long fileId);
    public String addFile(FileDTO fileDTO);
    public String deleteFile(Long fileId);
}
