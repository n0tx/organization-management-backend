package com.riki.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity
               .csrf(AbstractHttpConfigurer::disable)
               .headers(AbstractHttpConfigurer::disable)
               .cors(Customizer.withDefaults())
               .authorizeHttpRequests(requests -> requests
                       .requestMatchers("/v1/customer/**", "/v1/user/**", "/v1/custaddress/**", "/h2-ui/**").permitAll()
                       .anyRequest().authenticated())
               .oauth2Login(oauth2 -> oauth2
                       .loginPage("/v1/user/login/google")
                       .defaultSuccessUrl("/loginSuccess", true)
                       .failureUrl("/loginFailure"))
               .logout(logout -> logout
                       .logoutUrl("/v1/user/logout")
                       .logoutSuccessUrl("/v1/user/login/local") // Redirect to homepage after logout
                       .invalidateHttpSession(true)
                       .clearAuthentication(true)
                       .deleteCookies("JSESSIONID")
                       .permitAll());

       return httpSecurity.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();  // This creates the BCryptPasswordEncoder bean
   }


}



