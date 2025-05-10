package com.mooky.blog.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mooky.blog.domain.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
  
  

}
