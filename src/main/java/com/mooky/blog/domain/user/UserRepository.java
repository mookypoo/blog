package com.mooky.blog.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM User u where u.email = :email) THEN true ELSE false END")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM User u WHERE u.username = :username) THEN true ELSE false END")
    boolean existsByUsername(@Param("username") String username);

    // TODO remove
    @Query("SELECT new com.mooky.blog.domain.user.UserDto(u.id, u.username, u.email) FROM User u WHERE u.id = :userId")
    Optional<UserDto> getUserDetails(@Param("userId") Long userId);

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.recentLoginAt = CURRENT_TIMESTAMP WHERE u.id = :userId")
    int updateRecentLoginById(@Param("userId") Long userId);
}
