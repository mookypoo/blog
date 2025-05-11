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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Entity
@NoArgsConstructor
@SuppressWarnings("unused")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter Long id;

  @Column(nullable = false, unique = true, length = 25)
  private @Getter String username;

  public enum SignUpType {
    GOOGLE, FACEBOOK, EMAIL, KAKAOTALK;
  }

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private @Getter SignUpType signUpType;

  @Column(length = 50)
  private @Getter String ssoId;

  @Column(unique = true, length = 200)
  private @Getter String email;

  @Basic(fetch = FetchType.LAZY) // large Serializable object 
  private @Getter String password;

  private enum UserStatus {
    NORMAL, PAUSED, WITHDRAWN
  }

  private UserStatus status = UserStatus.NORMAL;

  @Column(updatable = false, insertable = false) // TODO - transient
  private LocalDateTime createdAt;

  @Column(length = 20)
  private String createdBy = "SYSTEM";

  private LocalDateTime modifiedAt;

  private String modifiedBy;

  @Override
  public String toString() {
    return "User [id=" + id + ", signUpType=" + signUpType + ", ssoId=" + ssoId + ", email=" + email
        + ",username=" + username + "]";
  }

  public UserEntity(Builder builder) {
    this.username = builder.username;
    this.signUpType = builder.signUpType;
    this.email = builder.email;
    this.ssoId = builder.ssoId;
    this.password = builder.password;
  }

  public static class Builder {
    private String username;
    private SignUpType signUpType;
    private String email;
    private String ssoId;
    private String password;

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public Builder signupType(SignUpType signUpType) {
      this.signUpType = signUpType;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }
    
    public Builder ssoId(String ssoId) {
      this.ssoId = ssoId;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public UserEntity build() {
      return new UserEntity(this);
    }
  }

}
