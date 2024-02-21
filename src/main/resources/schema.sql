-- 테이블 생성 SQL - NOTICE
CREATE TABLE IF NOT EXISTS NOTICE
(
    `ID`           INT UNSIGNED    NOT NULL    AUTO_INCREMENT COMMENT '아이디',
    `TITLE`        VARCHAR(50)     NOT NULL    COMMENT '제목. 제목',
    `SUBJECT`      MEDIUMTEXT      NOT NULL    COMMENT '내용. 내용',
    `START_DATE`   DATETIME        NOT NULL    COMMENT '시작 일',
    `END_DATE`     DATETIME        NOT NULL    COMMENT '종료 일',
    `VIEW_COUNT`   INT UNSIGNED    NOT NULL    DEFAULT 0 COMMENT '조회 수',
    `CREATE_ID`    VARCHAR(50)     NOT NULL    COMMENT '등록 아이디',
    `CREATE_DATE`  DATETIME        NOT NULL    COMMENT '등록 일',
    `UPDATE_ID`    VARCHAR(50)     NULL        COMMENT '수정 아이디',
    `UPDATE_DATE`  DATETIME        NULL        COMMENT '수정 일',
    `USE_YN`       CHAR(1)         NOT NULL    DEFAULT 'Y' COMMENT '사용 여부',
    PRIMARY KEY (ID)
);

-- 테이블 Comment 설정 SQL - NOTICE
ALTER TABLE NOTICE COMMENT '공지사항. 공지사항을 등록관리';


-- 테이블 생성 SQL - NOTICE_FILE
CREATE TABLE IF NOT EXISTS NOTICE_FILE
(
    `ID`           INT UNSIGNED    NOT NULL    AUTO_INCREMENT COMMENT '아이디',
    `NOTICE_ID`    INT UNSIGNED    NOT NULL    COMMENT '공지 아이디',
    `NAME`         VARCHAR(50)     NOT NULL    COMMENT '이름',
    `PATH`         VARCHAR(50)     NOT NULL    COMMENT '경로',
    `SIZE`         INT UNSIGNED    NOT NULL    COMMENT '크기',
    `USE_YN`       CHAR(1)         NOT NULL    DEFAULT 'Y' COMMENT '사용 여부',
    `CREATE_DATE`  DATETIME        NOT NULL    COMMENT '등록 일',
    `CREATE_ID`    VARCHAR(50)     NOT NULL    COMMENT '등록 아이디',
    `UPDATE_DATE`  DATETIME        NULL        COMMENT '수정 일',
    `UPDATE_ID`    VARCHAR(50)     NULL        COMMENT '수정 아이디',
    PRIMARY KEY (ID)
);

-- 테이블 Comment 설정 SQL - NOTICE_FILE
ALTER TABLE NOTICE_FILE COMMENT '공지사항 첨부파일';

/*
-- Foreign Key 는 설정하지 않습니다.
-- Foreign Key 설정 SQL - NOTICE_FILE(NOTICE_ID) -> NOTICE(ID)
ALTER TABLE NOTICE_FILE
    ADD CONSTRAINT FK_NOTICE_FILE_NOTICE_ID_NOTICE_ID FOREIGN KEY (NOTICE_ID)
        REFERENCES NOTICE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - NOTICE_FILE(NOTICE_ID)
-- ALTER TABLE NOTICE_FILE
-- DROP FOREIGN KEY FK_NOTICE_FILE_NOTICE_ID_NOTICE_ID;
*/