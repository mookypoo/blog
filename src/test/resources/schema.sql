create database if not exists pet_diary;
use pet_diary;

create table if not exists pet_diary.usr 
(
    user_id         bigint          primary key auto_increment comment 'user_id',
    username        varchar(25)     not null unique,
    sign_up_type    varchar(20)     not null comment '가입방식. 이메일(EMAIL) 구글(GOOGLE)',
    sso_id          varchar(50)     comment 'SSO 가입 시 ID',
    email           varchar(200)    not null unique comment '가입 시 이메일',
    password        varchar(200)    comment '이메일 가입 시 로그인을 위한 암호화 되어있는 비밀번호',
    agreed_marketing_terms  bool    default false,
    created_at      datetime        default current_timestamp(),
    created_by      varchar(20)     default 'SYSTEM',
    recent_login_at datetime        comment '최근 로그인 일시',
    modified_at     datetime        comment '사용자 정보 업데이트 일시',
    modified_by     varchar(20) 
)
    comment '펫 다이어리 사용자 마스터 테이블';

create table if not exists pet_diary.pet 
(
    pet_id          bigint          primary key auto_increment comment 'pet id',
    owner_id        bigint          not null comment 'owner_id',
    name            varchar(50)     not null comment 'pet name',
    species         varchar(50)     comment 'type of pet eg) dog',
    breed           varchar(50)     comment 'breed of pet eg) pomeranian',
    birth_date      date            comment 'YYYY-MM-DD',
    adoption_date   date            comment 'YYYY-MM-DD',
    description     text            comment 'description of pet',
    profile_photo   varchar(200)    comment 'amazon s3 url',
    created_at      datetime        default current_timestamp(),
    modified_at     datetime        comment '정보 업데이트 일시',
    constraint FK_pet_user_id foreign key (owner_id) references pet_diary.usr (user_id)
)
    comment '반려동물 마스터 테이블';

