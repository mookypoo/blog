package com.mooky.blog.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM UserEntity u where u.email=:email) THEN true ELSE false END")
  boolean existsByEmail(@Param("email") String email);

  @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM UserEntity u WHERE u.username = :username) THEN true ELSE false END")
  boolean existsByUsername(@Param("username") String username);

  @Query("SELECT new com.mooky.blog.domain.user.UserDetails(u.id, u.username, u.email) FROM UserEntity u WHERE u.id=:userId")
  Optional<UserDetails> getUserDetails(@Param("userId") Long userId);
}
