package com.web.thuvien.service;

import com.web.thuvien.model.dto.ChangePasswordDTO;
import com.web.thuvien.model.dto.ImageDTO;
import com.web.thuvien.model.dto.UserRegisterDTO;

import java.util.List;

public interface UserService {
    public String addImage(ImageDTO imageDTO);
    public String deleteImage(Long imageId);
    public String changePassword(ChangePasswordDTO changePasswordDTO);
}
