package com.web.thuvien.controller;

import com.web.thuvien.model.dto.BookDTO;
import com.web.thuvien.model.dto.ChangePasswordDTO;
import com.web.thuvien.model.dto.ImageDTO;
import com.web.thuvien.model.dto.MyFavouriteDTO;
import com.web.thuvien.model.response.BookResponse;
import com.web.thuvien.model.response.ImageResponse;
import com.web.thuvien.model.response.UserResponse;
import com.web.thuvien.service.AuthenticationService;
import com.web.thuvien.service.BookService;
import com.web.thuvien.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @GetMapping("/my_favourite")
    public ResponseEntity<List<BookResponse>> getMyFavourite(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(bookService.findByUserId(userId));
    }

    @PostMapping("/my_favourite")
    public ResponseEntity<String> addToMyFavourite(@RequestBody MyFavouriteDTO myFavouriteDTO) {
        return ResponseEntity.ok(bookService.addBookToMyFavourite(myFavouriteDTO));
    }

    @DeleteMapping("/my_favourite")
    public ResponseEntity<String> deleteFromMyFavourite(@RequestBody MyFavouriteDTO myFavouriteDTO) {
        return ResponseEntity.ok(bookService.deleteFromMyFavourite(myFavouriteDTO));
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUserInfo(@RequestParam(name = "token") String token) {
        return ResponseEntity.ok(authenticationService.userInfo(token));
    }

    @PostMapping("/image")
    public ResponseEntity<String> addImage(@ModelAttribute ImageDTO imageDTO) {
        return ResponseEntity.ok(userService.addImage(imageDTO));
    }

    @DeleteMapping("/image/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId) {
        return ResponseEntity.ok(userService.deleteImage(imageId));
    }

    @PostMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO,
                                                 BindingResult bindingResult){
        if(bindingResult.hasErrors() || !changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())){
            return ResponseEntity.ok("Failed to change password");
        }
        return ResponseEntity.ok(userService.changePassword(changePasswordDTO));
    }
}
