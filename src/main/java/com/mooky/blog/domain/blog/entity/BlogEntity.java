package com.mooky.blog.domain.blog.entity;

import com.mooky.blog.domain.blog.vo.BlogReq;
import com.mooky.blog.domain.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog")
@Getter
@NoArgsConstructor
public class BlogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Size(min = 1, max = 50)
  private String title;

  @Size(min = 1)
  private String content;

  @Column(nullable = false)
  private long userId;

  @OneToOne(optional = false)
  @JoinColumn(name = "id")
  private BlogCreaterEntity user;

  public BlogEntity(BlogReq req) {
    this.title = req.getTitle();
    this.content = req.getContent();
    this.userId = req.getUserId();
  }

}
