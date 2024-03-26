-- 用户信息扩展表
create table USEREXTENDEDINFORMATION
(
    ID           NUMBER        not null
        constraint "UserExtendedInformation_pk"
            primary key,
    USERNAME     VARCHAR2(64)  not null
        constraint UK_USERNAME
            unique,
    PASSWORD     VARCHAR2(128) not null,
    STATE        NUMBER,
    CREATED_BY   VARCHAR2(64),
    CREATED_TIME DATE default SYSDATE,
    MODIFY_BY    VARCHAR2(64),
    MODIFY_TIME  DATE default SYSDATE
);
comment on table USEREXTENDEDINFORMATION is '用户扩展信息';
comment on column USEREXTENDEDINFORMATION.USERNAME is '账号';
comment on column USEREXTENDEDINFORMATION.PASSWORD is '密码';