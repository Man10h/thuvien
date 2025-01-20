package com.web.thuvien.service.impl;

import com.web.thuvien.convert.UserConvert;
import com.web.thuvien.exception.ex.MailErrorException;
import com.web.thuvien.exception.ex.RoleNotFoundException;
import com.web.thuvien.exception.ex.UserExistsException;
import com.web.thuvien.exception.ex.UserNotFoundException;
import com.web.thuvien.model.dto.ResetPasswordDTO;
import com.web.thuvien.model.dto.UserLoginDTO;
import com.web.thuvien.model.dto.UserRegisterDTO;
import com.web.thuvien.model.dto.VerificationDTO;
import com.web.thuvien.model.entity.RefreshTokenEntity;
import com.web.thuvien.model.entity.RoleEntity;
import com.web.thuvien.model.entity.UserEntity;
import com.web.thuvien.model.response.UserResponse;
import com.web.thuvien.repository.RefreshTokenRepository;
import com.web.thuvien.repository.RoleRepository;
import com.web.thuvien.repository.UserRepository;
import com.web.thuvien.service.AuthenticationService;
import com.web.thuvien.service.MailService;
import com.web.thuvien.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserConvert userConvert;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UserResponse userInfo(String token) {
        String username = tokenService.getUsername(token);
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        if(optional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        UserEntity user = optional.get();
        return userConvert.convertToUserResponse(user);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public String register(UserRegisterDTO userRegisterDTO) {
        String email = userRegisterDTO.getEmail();
        String password = userRegisterDTO.getPassword();
        String username = userRegisterDTO.getUsername();
        Optional<UserEntity> optionalUserEmail = userRepository.findByEmail(email);
        if (optionalUserEmail.isPresent()) {
            throw new UserExistsException("User already exists");
        }
        Optional<UserEntity> optionalUsername = userRepository.findByUsername(username);
        if(optionalUsername.isPresent()) {
            throw new UserExistsException("User already exists");
        }
        Optional<RoleEntity> optionalRole = roleRepository.findById(2L);
        if(optionalRole.isEmpty()){
            throw new RoleNotFoundException("Role not found");
        }
        RoleEntity roleEntity = optionalRole.get();
        String code = generateCode();
        UserEntity userEntity = UserEntity.builder()
                .bookEntities(new ArrayList<>())
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(userRegisterDTO.getName())
                .roleEntity(roleEntity)
                .verificationCode(code)
                .verificationCodeExpiration(new Date(new Date().getTime() + 15 * 60))
                .status(false)
                .build();
        userRepository.save(userEntity);
        sendVerificationCode(userEntity);
        return "Create account successfully";
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public String login(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userLoginDTO.getUsername());
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("Fail to login");
        }
        UserEntity user = optionalUser.get();
        if(!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())
            || !user.getStatus()){
            throw new UserNotFoundException("Fail to login");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword(), user.getAuthorities());
        authenticationManager.authenticate(authentication);
        // refreshToken
        refreshTokenRepository.deleteByUserEntity(user);
        String refreshToken = tokenService.generateRefreshToken(user);
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .userEntity(user)
                .token(refreshToken)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);

        return tokenService.generateToken(user);
    }

    public String generateCode() {
        Random random = new Random();
        return random.nextInt(100000) + "";
    }

    public void sendVerificationCode(UserEntity user){
        String email = user.getEmail();
        String code = user.getVerificationCode();
        String name = user.getName();
        String html = "<html>Verification Code: " + code + "</html>";
        try{
            mailService.sendVerificationEmail(email, name, html);
        }catch (Exception e){
            throw new MailErrorException("Error mail sending");
        }
    }



    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public String verifyAccount(VerificationDTO verificationDTO){
        String email = verificationDTO.getEmail();
        String code = verificationDTO.getCode();
        Optional<UserEntity> optionalUserEmail = userRepository.findByEmail(email);
        if(optionalUserEmail.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        UserEntity userEntity = optionalUserEmail.get();
        if(userEntity.getStatus()){
            return "Account already verified";
        }
        if(!userEntity.getVerificationCode().equals(code) || new Date().before(userEntity.getVerificationCodeExpiration())){
            return "Fail to verify account";
        }
        userEntity.setVerificationCode(null);
        userEntity.setVerificationCodeExpiration(null);
        userEntity.setStatus(true);
        userRepository.save(userEntity);
        return "Verify account successfully";
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public String resendCode(String email) {
        Optional<UserEntity> optionalUserEmail = userRepository.findByEmail(email);
        if(optionalUserEmail.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        UserEntity userEntity = optionalUserEmail.get();
        if(userEntity.getStatus()){
            return "Account already verified";
        }
        String newCode = generateCode();
        userEntity.setVerificationCode(newCode);
        userRepository.save(userEntity);

        sendVerificationCode(userEntity);
        return "Check your email";
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public String refreshToken(String token) {
        if(!tokenService.validateToken(token)){
            return "Fail to refresh token";
        }
        String username = tokenService.getUsername(token);
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        if(optional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        UserEntity user = optional.get();
        return tokenService.generateToken(user);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<UserEntity> optional = userRepository.findById(resetPasswordDTO.getUserId());
        if(optional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        UserEntity user = optional.get();
        if(!user.getPassword().equals(passwordEncoder.encode(resetPasswordDTO.getPassword()))){
            return "Fail to reset password";
        }
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepository.save(user);
        return "Reset password successfully";
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public String forgotPassword(String email) {
        Optional<UserEntity> optional = userRepository.findByEmail(email);
        if(optional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        UserEntity user = optional.get();
        String newPassword = generateCode();
        user.setPassword(passwordEncoder.encode(newPassword));
        String html = "<html>New Password: " + newPassword + "</html>";
        mailService.sendVerificationEmail(email, user.getUsername(), html);
        userRepository.save(user);
        return "Check your email";
    }
}
