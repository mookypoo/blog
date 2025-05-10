create or replace table mooky.blog
(
  id            bigint                primary key auto_increment comment 'blog id',
  title         varchar(100)          not null comment '블로그 제목',
  content       varchar(200)          not null comment '블로그 콘텐트',
  user_id       bigint                not null comment '블로그 쓴 사용자 id',
  created_at    datetime              default current_timestamp(),
  created_by    varchar(20)           default 'SYSTEM',
  constraint FK_blog_user_id foreign key (user_id) references mooky.user (id)
)
  comment '블로그';

create or replace table mooky.user 
(
  id            bigint                primary key auto_increment comment 'user id',
  username      varchar(25)           not null unique,
  sign_up_type  varchar(20)           not null comment '가입방식. 구글(GOOGLE), 페이스북(FACEBOOK), 자체 이메일(EMAIL), 카톡(KAKAOTALK)',
  sso_id        varchar(50)           comment 'SSO 가입 시 ID',
  email         varchar(200)          not null unique comment '가입 시 이메일',
  password      varchar(200)          comment '이메일 가입 시 로그인을 위한 암호화 되어있는 비밀번호',
  status        varchar(10)           default 'NORMAL' comment '보통(NORMAL), 휴면(PAUSED), 탈퇴(WITHDRAWN)',
  created_at    datetime              default current_timestamp(),
  created_by    varchar(20)           default 'SYSTEM',
  recent_login_at datetime            comment '최근 로그인 일시',
  modified_at   datetime              comment '사용자 정보 업데이트 일시',
  modified_by   varchar(20)           
)
  comment '블로그 사용자 마스터 테이블 ';

insert into mooky.user (username, sign_up_type, email, password) values ("mooky", "EMAIL", "sookim482.dev@gmail.com", "password");

-------------------------------------------  varchar 용량 많은것?
-- id            binary(16)            primary key default UUID_SHORT() comment 'blog id',
-- created_by    varchar(50)           not null comment 'userId',