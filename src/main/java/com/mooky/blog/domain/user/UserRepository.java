package com.mooky.blog.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooky.blog.domain.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
  
  @Query(nativeQuery = true, value = "SELECT user_id, username, email FROM User where user_id = :userId")
  Optional<UserDetails> getUserDetails(@Param("userId") Long userId);

}
