CREATE TABLE IF NOT EXISTS NOTICE
(
    `ID`           INT            NOT NULL    AUTO_INCREMENT COMMENT '아이디',
    `TITLE`        VARCHAR(50)    NOT NULL    COMMENT '제목. 제목',
    `SUBJECT`      MEDIUMTEXT     NOT NULL    COMMENT '내용. 내용',
    `START_DATE`   DATETIME       NOT NULL    COMMENT '시작 일',
    `END_DATE`     DATETIME       NOT NULL    COMMENT '종료 일',
    `VIEW_COUNT`   INT            NOT NULL    DEFAULT 0 COMMENT '조회 수',
    `CREATE_ID`    VARCHAR(50)    NOT NULL    COMMENT '등록 아이디',
    `CREATE_DATE`  DATETIME       NOT NULL    COMMENT '등록 일',
    `UPDATE_ID`    VARCHAR(50)    NULL        COMMENT '수정 아이디',
    `UPDATE_DATE`  DATETIME       NULL        COMMENT '수정 일',
    `USE_YN`       CHAR(1)        NOT NULL    DEFAULT 'Y' COMMENT '사용 여부',
    PRIMARY KEY (ID)
);

ALTER TABLE NOTICE COMMENT '공지사항. 공지사항을 등록관리';