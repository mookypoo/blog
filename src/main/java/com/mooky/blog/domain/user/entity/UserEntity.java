package com.mooky.blog.domain.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings("unused")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter long id;

  @Column(nullable = false, unique = true, length = 25)
  private @Getter String username;

  public enum SignUpType {
    GOOGLE, FACEBOOK, EMAIL, KAKAOTALK;
  }

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private @Getter SignUpType signUpType;

  @Column(length = 50)
  private String ssoId;

  @Column(unique = true, length = 200)
  private String email;

  @Basic(fetch = FetchType.LAZY) // large Serializable object 
  private @Getter String password;

  private enum UserStatus {
    NORMAL, PAUSED, WITHDRAWN
  }

  @Builder.Default
  private UserStatus status = UserStatus.NORMAL;

  @Column(updatable = false, insertable = false) // TODO - transient
  private LocalDateTime createdAt;

  @Column(length = 20)
  @Builder.Default
  private String createdBy = "SYSTEM";

  private LocalDateTime modifiedAt;

  private String modifiedBy;

  @Override
  public String toString() {
    return "User [id=" + id + ", signUpType=" + signUpType + ", ssoId=" + ssoId + ", email=" + email
        + ",username=" + username + "]";
  }

}
