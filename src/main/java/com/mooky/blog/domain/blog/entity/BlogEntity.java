package com.mooky.blog.domain.blog.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.mooky.blog.domain.blog.vo.BlogReq;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO edit & comments

@Entity
@Table(name = "blog")
@Getter
@NoArgsConstructor
public class BlogEntity {

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

  @OneToOne
  @JoinColumn(name = "authorId", referencedColumnName = "user_id", insertable = false, updatable = false)
  private BlogCreaterEntity author;

  @Column(insertable = false, updatable = false)
  @CreationTimestamp // need this in order to retrieve current_timestamp()
  private LocalDateTime createdAt;

  private String createdBy = "SYSTEM";

  private LocalDateTime modifiedAt;

  // TODO don't need if i dont use jpa repository save 
  public BlogEntity(BlogReq req) {
    this.title = req.getTitle();
    this.content = req.getContent();
    this.authorId = req.getUserId();
  }

}
