package com.mysite.nara.repository;

import com.mysite.nara.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByEmail(String email);

   String email(String email);

   //이메일 중복체크
   boolean existsByEmail(String email);
}
