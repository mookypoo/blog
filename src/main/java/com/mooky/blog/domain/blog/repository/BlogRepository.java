package com.mooky.blog.domain.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mooky.blog.domain.blog.entity.BlogEntity;

public interface BlogRepository extends JpaRepository<BlogEntity, Long>{

  Optional<BlogEntity> findById(long id);

  

}


// @Query("SELECT profileName FROM BizAdmin WHERE profileName=:profileName")
// Optional<String> unavailableProfileName(@Param("profileName") String profileName)
// ;