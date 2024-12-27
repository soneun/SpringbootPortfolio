package com.mysite.nara.service;

import com.mysite.nara.dto.UserDTO;
import com.mysite.nara.entity.User;
import com.mysite.nara.exception.DataNotFoundException;
import com.mysite.nara.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(UserDTO userDTO ){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = mapToEntity(userDTO);
        user.setUserId(UUID.randomUUID().toString());
        userRepo.save(user);
    }

    private User mapToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);

    }
    //로그인 된 유저 가져오기
    public User getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginUserEmail = auth.getName();
        User user = userRepo.findByEmail(loginUserEmail).orElseThrow(()->
                new RuntimeException("이메일을 찾을 수 없습니다"));
        return user;
    }

//    public User getUser(String email){
//        Optional<User> user = userRepo.findByEmail(email);
//        if(user.isPresent()){
//            return user.get();
//        }else{
//            throw new DataNotFoundException("이메일을 찾을 수 없습니다");
//        }
//    }

    //이메일 중복 체크
    public boolean existsByEmail(String email){
        return userRepo.existsByEmail(email);
    }

}
