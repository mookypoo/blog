package com.mooky.blog.domain.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooky.blog.domain.blog.entity.BlogEntity;
import com.mooky.blog.domain.blog.vo.BlogReq;

public interface BlogRepository extends JpaRepository<BlogEntity, Long>{

  //@Query(value = "UPDATE blog SET title=:'")
  BlogEntity editBlog(@Param("blogId") Long blogId, @Param("blogReq") BlogReq blogReq);

  

}


// @Query("SELECT profileName FROM BizAdmin WHERE profileName=:profileName")
// Optional<String> unavailableProfileName(@Param("profileName") String profileName)
// ;