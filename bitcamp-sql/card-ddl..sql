-- 1
CREATE TABLE c_member (
    mno   INT          NOT NULL COMMENT '번호',
    mname   VARCHAR(20) NOT NULL COMMENT '이름', -- 이름
    email VARCHAR(255) NOT NULL COMMENT '이메일', -- 이메일
    pwd   VARCHAR(100) NOT NULL COMMENT '암호', -- 암호
    phone VARCHAR(20)  NULL COMMENT '휴대전화', -- 휴대전화
    tel   VARCHAR(20)  NULL COMMENT '일반전화', -- 일반전화
    fax   VARCHAR(20)  NULL COMMENT '팩스', -- 팩스
    memo  TEXT         NULL COMMENT '메모' -- 메모
)

COMMENT '회원';
ALTER TABLE c_member
    ADD CONSTRAINT PK_c_member -- 회원 기본키
        PRIMARY KEY (
            mno -- 번호
        );

ALTER TABLE c_member
    MODIFY COLUMN mno INT NOT NULL AUTO_INCREMENT COMMENT '번호';
----------------------------------------------------------------------------
-- 2
CREATE TABLE c_member(
    mno    INT          NOT NULL COMMENT '번호',
    name  VARCHAR(20)  NOT NULL COMMENT '이름',
    email  VARCHAR(255) NOT NULL COMMENT '이메일',
    pwd    VARCHAR(100) NOT NULL COMMENT '암호'
)
COMMENT '회원';

CREATE TABLE c_view(
    vno    INT          NOT NULL COMMENT '번호'
    mno    INT          NOT NULL COMMENT '멤버',
    phone  VARCHAR(20)  NULL COMMENT '휴대전화',
    tel    VARCHAR(20)  NULL COMMENT '일반전화',
    fax    VARCHAR(20)  NULL COMMENT '팩스',
    memo   TEXT         NULL COMMENT '메모'
)
COMMENT '회원 상세';

-- c_member
ALTER TABLE c_member
    ADD CONSTRAINT PK_c_member
        PRIMARY KEY(
            mno -- 번호
        );

ALTER TABLE c_member
    MODIFY COLUMN mno INT NOT NULL AUTO_INCREMENT COMMENT '번호';

-- c_view
ALTER TABLE c_view
    ADD CONSTRAINT PK_c_view
        PRIMARY KEY(
            vno -- 번호
        );
ALTER TABLE c_view
    MODIFY COLUMN vno INT NOT NULL AUTO_INCREMENT COMMENT '번호';

ALTER TABLE c_view
    ADD CONSTRAINT FK_c_member_TO_c_view
        FOREIGN KEY (
            mno -- 멤버
        )
        REFERENCES c_member (
            mno
        );

