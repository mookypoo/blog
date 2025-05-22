package com.mooky.blog.domain.blog.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.mooky.blog.domain.blog.dto.BlogWriteDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO edit & comments
@Entity
@Table(name = "Blog")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    @Size(min = 1, max = 50)
    private String title;

    @Size(min = 1)
    private String content;
    
    // prefer to let the database foreign key check, so chose to have authorId field instead of a whole 
    // User author field relationship
    @Column(nullable = false, updatable = false)
    private long authorId;

    // TODO REMEMBER - lazy loading 하니까 hibernate selects twice, but eager loading joins the two tables
    @ManyToOne(optional = false)
    @JoinColumn(name = "authorId", referencedColumnName = "user_id", insertable = false, updatable = false)
    private BlogCreater author;

    @Column(insertable = false, updatable = false)
    @CreationTimestamp // need this in order to retrieve current_timestamp()
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public Blog(BlogWriteDto req) {
        this.title = req.getTitle();
        this.content = req.getContent();
        this.authorId = req.getUserId();
    }

}
