package com.mysite.nara.config;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    //인증 담당
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();

    }


    //인증 설정
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
               .csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests((authz)->
                       authz
                               .requestMatchers("/", "/login", "/register").permitAll()
                               .anyRequest().authenticated()
               )
               .formLogin((formLogin)->
                       formLogin
                               .loginPage("/login")
                               .failureUrl("/login?error=true")
                               .defaultSuccessUrl("/")
                               .usernameParameter("email")
                               .passwordParameter("password")
               )
               .logout((logout)->
                       logout
                               .logoutUrl("/logout")
                               .invalidateHttpSession(true)
                               .clearAuthentication(true)
                               .logoutSuccessUrl("/login?logout=true")
                               .permitAll()
               );



       return http.build();
   }

   //시큐리티 매니저 설정(패스워드 암호화와 유저서비스를 이용해서 인증함)
   @Bean
    public BCryptPasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

   //정적파일(자주쓰는 js, css 파일등)은 시큐리티 예외로 처리
   @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
       return (web)->
               web.ignoring().requestMatchers("/css/**", "/js/**", "/error","/assets/**");
   }

}
