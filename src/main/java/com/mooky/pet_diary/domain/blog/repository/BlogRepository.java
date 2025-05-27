package com.mooky.pet_diary.domain.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooky.pet_diary.domain.blog.dto.BlogDetailsDto;
import com.mooky.pet_diary.domain.blog.entity.Blog;

import jakarta.transaction.Transactional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    /**
     * uses native query (blog/entity/BlogEntityWithNativeQuery) to join Blog & User
     * and return a DTO right away
     * 
     * // Define the projection interface
     * public interface BlogSummary {
     * Long getId();
     * String getTitle();
     * String getContent();
     * String getAuthorUsername();
     * }
     * 
     * could just use jpql...@Query("SELECT b.id as id, b.title as title, b.content
     * as content, u.username as authorUsername FROM Blog b JOIN b.author u WHERE
     * b.id = :blogId")
     * 
     * @param blogId
     * @return BlogDetails (instead of Optional BlogEntity)
     */
    @Query(nativeQuery = true, name = "BlogEntityWithNativeQuery.findBlogDetails")
    Optional<BlogDetailsDto> findBlogDetailsWithNativeQuery(@Param("blogId") Long blogId);

    // TODO check if requesting user is same as author 
    @Modifying
    @Transactional
    @Query(value = "UPDATE blog SET title=:title, content=:content, modified_at=current_timestamp(), modified_by='AUTHOR' where blog_id=:blogId", nativeQuery = true)
    int editBlog(@Param("blogId") Long blogId, @Param("title") String title, @Param("content") String content);
}
