package com.web.thuvien.service.impl;

import com.web.thuvien.exception.ex.ImageNotFoundException;
import com.web.thuvien.exception.ex.RoleNotFoundException;
import com.web.thuvien.exception.ex.UserExistsException;
import com.web.thuvien.exception.ex.UserNotFoundException;
import com.web.thuvien.model.dto.ChangePasswordDTO;
import com.web.thuvien.model.dto.ImageDTO;
import com.web.thuvien.model.dto.UserRegisterDTO;
import com.web.thuvien.model.entity.BookEntity;
import com.web.thuvien.model.entity.ImageEntity;
import com.web.thuvien.model.entity.RoleEntity;
import com.web.thuvien.model.entity.UserEntity;
import com.web.thuvien.repository.ImageRepository;
import com.web.thuvien.repository.RoleRepository;
import com.web.thuvien.repository.UserRepository;
import com.web.thuvien.service.CloudinaryService;
import com.web.thuvien.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public String addImage(ImageDTO imageDTO) {
        Optional<UserEntity> optional = userRepository.findById(imageDTO.getUserId());
        if (optional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        UserEntity user = optional.get();
        for(MultipartFile multipartFile : imageDTO.getImages()) {
            Map result = cloudinaryService.upload(multipartFile);
            ImageEntity imageEntity = ImageEntity.builder()
                    .fileUrl((String) result.get("url"))
                    .fileName((String) result.get("original_filename"))
                    .fileId((String) result.get("public_id"))
                    .userEntity(user)
                    .build();
            user.getImageEntities().add(imageEntity);
        }
        userRepository.save(user);
        return "Add image successfully";
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public String deleteImage(Long imageId) {
        Optional<ImageEntity> optional = imageRepository.findById(imageId);
        if (optional.isEmpty()) {
            throw new ImageNotFoundException("Image not found");
        }
        imageRepository.deleteById(imageId);
        return "delete image successfully";
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public String changePassword(ChangePasswordDTO changePasswordDTO) {
        Optional<UserEntity> optional = userRepository.findById(changePasswordDTO.getUserId());
        if (optional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        UserEntity user = optional.get();
        if(!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            return "Change password failed";
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
        return "Change password successfully";
    }
}
