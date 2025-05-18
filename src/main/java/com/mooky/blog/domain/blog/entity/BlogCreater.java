package com.mooky.blog.domain.blog.entity;

import com.mooky.blog.domain.user.User;

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
public class BlogCreater {
 
  @Id
  @Column(name = "user_id")
  private Long id;

  private String username;

  // TODO profile image

  // for testing
  public BlogCreater(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }
}
