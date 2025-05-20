create or replace table mooky.blog
(
  blog_id       bigint                primary key auto_increment comment 'blog id',
  title         varchar(100)          not null comment '블로그 제목',
  content       varchar(200)          not null comment '블로그 콘텐트',
  author_id     bigint                not null comment '블로그 쓴 사용자 id',
  created_at    datetime              default current_timestamp(),
  created_by    varchar(20)           default 'SYSTEM',
  modified_at   datetime              comment '업데이트 일시',
  modified_by   varchar(20)           comment '사용자 AUTHOR',
  constraint FK_blog_user_id foreign key (user_id) references mooky.user (user_id)
)
  comment '블로그';

create or replace table mooky.user 
(
  user_id       bigint                primary key auto_increment comment 'user id',
  username      varchar(25)           not null unique,
  sign_up_type  varchar(20)           not null comment '가입방식. 구글(GOOGLE), 페이스북(FACEBOOK), 자체 이메일(EMAIL), 카톡(KAKAOTALK)',
  sso_id        varchar(50)           comment 'SSO 가입 시 ID',
  email         varchar(200)          not null unique comment '가입 시 이메일',
  password      varchar(200)          comment '이메일 가입 시 로그인을 위한 암호화 되어있는 비밀번호',
  status        varchar(10)           default 'ACTIVE' comment '보통(ACTIVE), 휴면(PAUSED), 탈퇴(WITHDRAWN)',
  agreed_marketing_terms  bool        default false,
  created_at    datetime              default current_timestamp(),
  created_by    varchar(20)           default 'SYSTEM',
  recent_login_at datetime            comment '최근 로그인 일시',
  modified_at   datetime              comment '사용자 정보 업데이트 일시',
  modified_by   varchar(20)           
)
  comment '블로그 사용자 마스터 테이블 ';

insert into mooky.user (username, sign_up_type, email, password) values ("Happy", "EMAIL", "heysookim482@gmail.com", "password");

create trigger log_user_terms
after insert on user
for each row
insert into user_terms (user_id, terms_version) values (NEW.user_id, (select version from terms order by terms_id desc limit 1));

create trigger delete_user_terms
before delete on user
for each row
delete from user_terms where user_id=OLD.user_id;

create trigger delete_user_blog
before delete on user
for each row
delete from blog where author_id=OLD.user_id;


create or replace table mooky.terms 
(
  terms_id      bigint                primary key auto_increment comment 'terms id',
  terms_url     varchar(200)          not null comment '해당 버전 url',
  version       varchar(20)           not null comment '이용약관 버전',
  created_at    datetime              default current_timestamp()
)
  comment '이용약관 master table';

insert into mooky.terms (terms_url, version) values ("some url", "1.0");
select version from mooky.terms ORDER BY terms_id DESC LIMIT 1;

create or replace table mooky.user_terms
(
  user_id       bigint                primary key comment 'user id',
  agreed_on     datetime              default current_timestamp(),
  terms_version varchar(20)           not null comment '해당 이용약관 버전',
  agreed_at     varchar(20)           default 'signup page',
  constraint FK_user_terms_user_id foreign key (user_id) references mooky.user (user_id)
)
  comment '사용자 - 이용약관 동의 정보';

insert into mooky.user_terms (user_id, terms_version) values (1, (select version from mooky.terms ORDER BY terms_id DESC LIMIT 1));
-------------------------------------------  varchar 용량 많은것?
-- id            binary(16)            primary key default UUID_SHORT() comment 'blog id',
-- created_by    varchar(50)           not null comment 'userId',