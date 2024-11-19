package com.riki.api.service.impl;


import com.riki.api.domain.User;
import com.riki.api.enums.AuthProvider;
import com.riki.api.repository.UserRepository;
import com.riki.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUserLocal(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthProvider(AuthProvider.LOCAL);
        return  userRepository.save(user);
    }

    @Override
    public User loginUserLocal(User user) {
        String errorMessage = "Email or password is incorrect";
        User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null){
            if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                throw new RuntimeException(errorMessage);
            }
            return existingUser;
        }
        throw new RuntimeException(errorMessage);
    }

    @Override
    public User loginRegisterByGoogleOAuth2(OAuth2AuthenticationToken auth2AuthenticationToken) {
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        log.info("USER Email FROM GOOGLE  IS {}",email );
        log.info("USER Name from GOOGLE IS {}",name );

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAuthProvider(AuthProvider.GOOGLE);
            return userRepository.save(user);
        }
        return user;
    }
}
