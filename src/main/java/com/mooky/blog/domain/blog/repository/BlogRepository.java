package com.mooky.blog.domain.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooky.blog.domain.blog.entity.BlogEntity;
import com.mooky.blog.domain.blog.vo.BlogReq;

import jakarta.transaction.Transactional;

public interface BlogRepository extends JpaRepository<BlogEntity, Long>{

  //BlogEntity save();

  // @Modifying
  // @Query(value = "INSERT INTO blog (author_id, title, content) values (:authorId, :title, :content)", nativeQuery = true)
  // BlogEntity saveBlog(@Param("authorId") Long authorId, @Param("title") String title, @Param("content") String content);

  // TODO check if requesting user is same as author 
  @Modifying
  @Transactional
  @Query(value = "UPDATE blog SET title=:title, content=:content, modified_at=current_timestamp(), modified_by='AUTHOR' where blog_id=:blogId", nativeQuery = true)
  int editBlog(@Param("blogId") Long blogId, @Param("title") String title, @Param("content") String content);

}


// @Query("SELECT profileName FROM BizAdmin WHERE profileName=:profileName")
// Optional<String> unavailableProfileName(@Param("profileName") String profileName)
// ;