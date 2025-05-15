package com.mooky.blog.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooky.blog.domain.user.UserDetails;
import com.mooky.blog.domain.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  
  @Query(value = "SELECT email FROM User where email=:email", nativeQuery = true)
  Optional<String> unavailableEmail(@Param("email") String email);

  @Query(value = "SELECT username FROM User where username=:username", nativeQuery = true)
  Optional<String> unavailableUsername(@Param("username") String username);
  
  @Query(nativeQuery = true, value = "SELECT user_id, username, email FROM User where user_id=:userId")
  Optional<UserDetails> getUserDetails(@Param("userId") Long userId);

}
