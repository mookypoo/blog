package com.mooky.blog.domain.blog.entity;

import com.mooky.blog.domain.user.entity.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private long id;

  private String title;

  private String content;

  @OneToOne(optional = false)
  @JoinColumn(name = "id")
  private BlogCreaterEntity user;

}
