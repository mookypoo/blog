package com.mooky.blog.domain.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooky.blog.domain.blog.entity.BlogEntity;
import com.mooky.blog.domain.blog.vo.BlogDetails;

import jakarta.transaction.Transactional;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
  /**
   * uses native query (blog/entity/BlogEntityWithNativeQuery) to join Blog & User
   * and return a DTO right away
   * 
   * @param blogId
   * @return BlogDetails (instead of Optional BlogEntity)
   */
  @Query(nativeQuery = true, name = "BlogEntityWithNativeQuery.findBlogDetails")
  BlogDetails findBlogDetailsWithNativeQuery(@Param("blogId") Long blogId);

  // TODO check if requesting user is same as author 
  @Modifying
  @Transactional
  @Query(value = "UPDATE blog SET title=:title, content=:content, modified_at=current_timestamp(), modified_by='AUTHOR' where blog_id=:blogId", nativeQuery = true)
  int editBlog(@Param("blogId") Long blogId, @Param("title") String title, @Param("content") String content);

}
