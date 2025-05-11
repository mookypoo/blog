package com.mooky.blog.domain.blog.entity;

import com.mooky.blog.domain.blog.vo.BlogReq;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  @Column(nullable = false)
  private long userId;

  // @OneToOne(optional = false)
  // @JoinColumn(name = "CUSTREC_ID", unique = true, nullable = false, updatable = false)
  // public CustomerRecord getCustomerRecord() {
  //   return customerRecord;
  // }

  // // On CustomerRecord class:

  // @OneToOne(optional = false, mappedBy = "customerRecord")
  // public Customer getCustomer() {
  //   return customer;
  // }

  // @OneToMany
  // @JoinColumn(name = "id", updatable = false)
  // private BlogCreaterEntity user;

  public BlogEntity(BlogReq req) {
    this.title = req.getTitle();
    this.content = req.getContent();
    this.userId = req.getUserId();
  }

}
