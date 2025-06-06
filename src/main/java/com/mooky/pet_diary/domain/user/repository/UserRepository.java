package com.mooky.pet_diary.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooky.pet_diary.domain.user.User;
import com.mooky.pet_diary.domain.user.dto.UserDto;
import com.mooky.pet_diary.domain.user.dto.UserWithPetSummaryProjection;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM User u where u.email = :email) THEN true ELSE false END")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM User u WHERE u.username = :username) THEN true ELSE false END")
    boolean existsByUsername(@Param("username") String username);

    // TODO remove
    @Query("SELECT new com.mooky.pet_diary.domain.user.dto.UserDto(u.id, u.username, u.email) FROM User u WHERE u.id = :userId")
    Optional<UserDto> getUserDetails(@Param("userId") Long userId);

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.recentLoginAt = CURRENT_TIMESTAMP WHERE u.id = :userId")
    int updateRecentLoginById(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = """
            SELECT u.user_id as userId, u.username, u.email,
                   p.pet_id as petId, p.name as petName, p.profile_photo as petProfilePhoto
            FROM usr u
            LEFT JOIN pet p ON u.user_id = p.owner_id
            WHERE u.user_id = :userId
            """)
    List<UserWithPetSummaryProjection> findUserProfileWithPetsSummaryById(@Param("userId") Long userId);
}
