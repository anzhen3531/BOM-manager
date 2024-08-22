set echo on
REM Creating table PartDeriveLink for ext.ziang.part.model.derive.PartDeriveLink
set echo off
CREATE TABLE PartDeriveLink (
   classnamekeyroleAObjectRef   VARCHAR2(600),
   idA3A5   NUMBER,
   classnamekeyroleBObjectRef   VARCHAR2(600),
   idA3B5   NUMBER,
   state   VARCHAR2(192) NOT NULL,
   createStampA2   DATE,
   markForDeleteA2   NUMBER NOT NULL,
   modifyStampA2   DATE,
   classnameA2A2   VARCHAR2(600),
   idA2A2   NUMBER NOT NULL,
   updateCountA2   NUMBER,
   updateStampA2   DATE,
 CONSTRAINT PK_PartDeriveLink PRIMARY KEY (idA2A2))
 STORAGE ( INITIAL 100k NEXT 100k PCTINCREASE 0 )
ENABLE PRIMARY KEY USING INDEX
 TABLESPACE INDX
 STORAGE ( INITIAL 100k NEXT 100k PCTINCREASE 0 )
/
COMMENT ON TABLE PartDeriveLink IS 'Table PartDeriveLink created for ext.ziang.part.model.derive.PartDeriveLink'
/
REM @//ext/ziang/part/model/derive/PartDeriveLink_UserAdditions
