package com.web.thuvien.controller;

import com.web.thuvien.model.dto.*;
import com.web.thuvien.model.response.BookResponse;
import com.web.thuvien.service.AuthenticationService;
import com.web.thuvien.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/book")
    public ResponseEntity<List<BookResponse>> findAll(@ModelAttribute BookDTO bookDTO){
        return ResponseEntity.ok(bookService.findAll(bookDTO));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long bookId){
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO,
                                           BindingResult bindingResult){
        if(bindingResult.hasErrors() || !userRegisterDTO.getRetype().equals(userRegisterDTO.getPassword())){
            return ResponseEntity.ok("Error to create account");
        }
        return ResponseEntity.ok(authenticationService.register(userRegisterDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO,
                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.ok("Error to login");
        }
        return ResponseEntity.ok(authenticationService.login(userLoginDTO));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerificationDTO verificationDTO){
        return ResponseEntity.ok(authenticationService.verifyAccount(verificationDTO));
    }

    @GetMapping("/resend_code")
    public ResponseEntity<String> resendCode(@RequestParam(name = "email") String email){
        return ResponseEntity.ok(authenticationService.resendCode(email));
    }

    @GetMapping("/refresh_token")
    public ResponseEntity<String> refresh_token(@RequestParam(name = "refreshToken") String refreshToken){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> reset_password(@RequestBody ResetPasswordDTO resetPasswordDTO,
                                                 BindingResult bindingResult){
        if(bindingResult.hasErrors() || !resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmPassword())){
            return ResponseEntity.ok("Error to reset password");
        }
        return ResponseEntity.ok(authenticationService.resetPassword(resetPasswordDTO));
    }

    @GetMapping("/forgot_password")
    public ResponseEntity<String> forgot_password(@RequestParam(name = "email") String email){
        return ResponseEntity.ok(authenticationService.forgotPassword(email));
    }
}
