set echo on
REM Creating table CommonFilterConfig for ext.ziang.model.CommonFilterConfig
set echo off
CREATE TABLE CommonFilterConfig (
   actionName   VARCHAR2(192) NOT NULL,
   groupInnerName   VARCHAR2(192),
   groupName   VARCHAR2(192),
   lifecycleState   VARCHAR2(192),
   roleName   VARCHAR2(192),
   createStampA2   DATE,
   markForDeleteA2   NUMBER NOT NULL,
   modifyStampA2   DATE,
   classnameA2A2   VARCHAR2(600),
   idA2A2   NUMBER NOT NULL,
   updateCountA2   NUMBER,
   updateStampA2   DATE,
   typeInnerName   VARCHAR2(384) NOT NULL,
   typeName   VARCHAR2(192),
 CONSTRAINT PK_CommonFilterConfig PRIMARY KEY (idA2A2))
 STORAGE ( INITIAL 1m NEXT 1m PCTINCREASE 0 )
ENABLE PRIMARY KEY USING INDEX
 TABLESPACE INDX
 STORAGE ( INITIAL 1m NEXT 1m PCTINCREASE 0 )
/
COMMENT ON TABLE CommonFilterConfig IS 'Table CommonFilterConfig created for ext.ziang.model.CommonFilterConfig'
/
REM @//ext/ziang/model/CommonFilterConfig_UserAdditions
