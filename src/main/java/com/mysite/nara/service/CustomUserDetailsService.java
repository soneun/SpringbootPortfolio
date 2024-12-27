package com.mysite.nara.service;

import com.mysite.nara.entity.User;
import com.mysite.nara.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //유저를 찾고 + 권한 시큐리티 유저 객체로 리턴
        User user = userRepo.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException(email + "해당 이메일의 유저가 없습니다"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()//권한인데 비어있음
        );
    }


}
