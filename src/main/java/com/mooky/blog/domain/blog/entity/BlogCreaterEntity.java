package com.mooky.blog.domain.blog.entity;

import com.mooky.blog.domain.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class BlogCreaterEntity {
 
  @Id
  @Column(name = "user_id")
  private Long id;

  private String username;

  // TODO profile image

  // for testing
  public BlogCreaterEntity(UserEntity user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }
}
