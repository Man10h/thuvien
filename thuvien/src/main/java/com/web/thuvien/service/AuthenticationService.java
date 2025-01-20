package com.web.thuvien.service;

import com.web.thuvien.model.dto.ResetPasswordDTO;
import com.web.thuvien.model.dto.UserLoginDTO;
import com.web.thuvien.model.dto.UserRegisterDTO;
import com.web.thuvien.model.dto.VerificationDTO;
import com.web.thuvien.model.response.UserResponse;

public interface AuthenticationService {
    public UserResponse userInfo(String token);
    public String register(UserRegisterDTO userRegisterDTO);
    public String login(UserLoginDTO userLoginDTO);
    public String verifyAccount(VerificationDTO verificationDTO);
    public String resendCode(String email);
    public String refreshToken(String token);
    public String resetPassword(ResetPasswordDTO resetPasswordDTO);
    public String forgotPassword(String email);
}
