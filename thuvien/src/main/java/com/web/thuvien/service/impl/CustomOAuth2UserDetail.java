package com.web.thuvien.service.impl;

import com.web.thuvien.exception.ex.UserNotFoundException;
import com.web.thuvien.model.entity.UserEntity;
import com.web.thuvien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserDetail extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = oAuth2User.getAttribute("email");
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        UserEntity user = optionalUser.get();
        return new DefaultOAuth2User(user.getAuthorities(), attributes, "email");
    }
}
