package com.mooky.blog.domain.blog.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.mooky.blog.domain.blog.dto.BlogResponse;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Blog")
@Getter
@NoArgsConstructor
@SqlResultSetMapping(name = "BlogDetailMapping",
    classes = @ConstructorResult(
      targetClass = BlogResponse.class,
      columns = {
            @ColumnResult(name = "blogId"),
            @ColumnResult(name = "title"),
            @ColumnResult(name = "content"),
            @ColumnResult(name = "authorUsername"),
            @ColumnResult(name = "authorId"),
            @ColumnResult(name = "createdAt", type = LocalDateTime.class),
            @ColumnResult(name = "modifiedAt", type = LocalDateTime.class),
          }
  ))
@NamedNativeQuery(name = "BlogEntityWithNativeQuery.findBlogDetails", query = """
        SELECT b.blog_id as blogId,
          b.title,
          b.content,
          b.author_id AS authorId,
          b.created_at AS createdAt,
          b.modified_at AS modifiedAt,
          u.username AS authorUsername
            FROM Blog b
            LEFT JOIN User u ON b.author_id=u.user_id
            WHERE b.blog_id=:blogId
    """, resultSetMapping = "BlogDetailMapping")
public class BlogWithNativeQuery {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "blog_id")
  private Long id;

  @Size(min = 1, max = 50)
  private String title;

  @Size(min = 1)
  private String content;

  @Column(nullable = false, updatable = false)
  private long authorId;

  @Column(insertable = false, updatable = false)
  @CreationTimestamp // need this in order to retrieve current_timestamp()
  private LocalDateTime createdAt;

  private String createdBy = "SYSTEM";

  private LocalDateTime modifiedAt;

}
