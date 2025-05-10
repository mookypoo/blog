package com.mooky.blog.domain.blog.entity;

import com.mooky.blog.domain.user.entity.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "user")
@Getter
public class BlogCreaterEntity {
  @Id
  private long id;

  private String username;

  // for testing
  public BlogCreaterEntity(UserEntity user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }
}
