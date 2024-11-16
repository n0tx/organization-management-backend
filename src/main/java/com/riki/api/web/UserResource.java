package com.riki.api.web;

import com.riki.api.domain.User;
import com.riki.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
public class UserResource {

    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<User> register(@RequestBody @Validated User user){
//        return ResponseEntity.ok(userService.registerUserLocal(user));
//    }

    @PostMapping("/v1/user/register")
    public ResponseEntity<User> registerUser(@Validated @RequestBody User user) {
        return new ResponseEntity<>(userService.registerUserLocal(user), HttpStatus.CREATED);
    }

    @PostMapping("/v1/user/login/local")
    public ResponseEntity<User> loginLocal(@RequestBody User user){
        return new ResponseEntity<>(userService.loginUserLocal(user), HttpStatus.OK);
    }

//    @GetMapping("/auth/google")
//    public ResponseEntity<String> loginGoogleAuth(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/oauth2/authorization/google");
//        return ResponseEntity.ok("Redirecting ..");
//    }

    @GetMapping("/v1/user/login/google")
    public ResponseEntity<String> loginGoogleAuth(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
        return ResponseEntity.ok("Redirecting ..");
    }

    @GetMapping("/loginSuccess")
    public ResponseEntity<?> handleGoogleSuccess(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        User user = userService.loginRegisterByGoogleOAuth2(oAuth2AuthenticationToken);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8081/customer/list")).build();
    }

    @PostMapping("/v1/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
        return "Logged out successfully";
    }

//    @GetMapping("/logoutSuccess")
//    public ResponseEntity<?> handleLogoutSuccess(OAuth2AuthenticationToken oAuth2AuthenticationToken){
//        User user = userService.loginRegisterByGoogleOAuth2(oAuth2AuthenticationToken);
//        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8081/customer/list")).build();
//    }

}
