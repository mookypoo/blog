package com.mooky.blog.domain.blog.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mooky.blog.domain.blog.entity.Blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
public class BlogDetailsDto {
    private final Long blogId;
    private final String title;
    private final String content;
    private final Author author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public BlogDetailsDto(Blog blogEntity) {
        this.blogId = blogEntity.getId();
        this.title = blogEntity.getTitle();
        this.content = blogEntity.getContent();
        this.author = new Author(blogEntity.getAuthor().getId(), blogEntity.getAuthor().getUsername());
        this.createdAt = blogEntity.getCreatedAt();
        this.modifiedAt = blogEntity.getModifiedAt();
    }

    @RequiredArgsConstructor
    @Getter
    public class Author {
        private final Long authorId;
        private final String username;
    }
}