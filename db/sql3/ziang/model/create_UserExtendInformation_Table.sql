set echo on
REM Creating table UserExtendInformation for ext.ziang.model.UserExtendInformation
set echo off
CREATE TABLE UserExtendInformation (
   alternateUserName1   VARCHAR2(384),
   alternateUserName2   VARCHAR2(384),
   alternateUserName3   VARCHAR2(384),
   alternateUserName4   VARCHAR2(384),
   password   VARCHAR2(384) NOT NULL,
   state   NUMBER,
   createStampA2   DATE,
   markForDeleteA2   NUMBER NOT NULL,
   modifyStampA2   DATE,
   classnameA2A2   VARCHAR2(600),
   idA2A2   NUMBER NOT NULL,
   updateCountA2   NUMBER,
   updateStampA2   DATE,
   username   VARCHAR2(384) NOT NULL,
 CONSTRAINT PK_UserExtendInformation PRIMARY KEY (idA2A2))
 STORAGE ( INITIAL 1m NEXT 1m PCTINCREASE 0 )
ENABLE PRIMARY KEY USING INDEX
 TABLESPACE INDX
 STORAGE ( INITIAL 1m NEXT 1m PCTINCREASE 0 )
/
COMMENT ON TABLE UserExtendInformation IS 'Table UserExtendInformation created for ext.ziang.model.UserExtendInformation'
/
REM @//ext/ziang/model/UserExtendInformation_UserAdditions
