package com.riki.api.service;

import com.riki.api.domain.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface UserService {

    public User registerUserLocal(User user);

    public User loginUserLocal(User user);

    public  User loginRegisterByGoogleOAuth2(OAuth2AuthenticationToken auth2AuthenticationToken);

}
