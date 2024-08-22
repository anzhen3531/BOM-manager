set echo on
REM Creating table ElectronicSignatureConfig for ext.ziang.model.ElectronicSignatureConfig
set echo off
CREATE TABLE ElectronicSignatureConfig (
   contentType   VARCHAR2(384) NOT NULL,
   docType   VARCHAR2(384) NOT NULL,
   docTypeName   VARCHAR2(384) NOT NULL,
   extendedField   VARCHAR2(384),
   extendedField1   VARCHAR2(384),
   extendedField2   VARCHAR2(384),
   signXIndex   VARCHAR2(384),
   signYIndex   VARCHAR2(384),
   status   NUMBER,
   createStampA2   DATE,
   markForDeleteA2   NUMBER NOT NULL,
   modifyStampA2   DATE,
   classnameA2A2   VARCHAR2(600),
   idA2A2   NUMBER NOT NULL,
   updateCountA2   NUMBER,
   updateStampA2   DATE,
   workItemName   VARCHAR2(384) NOT NULL,
 CONSTRAINT PK_ElectronicSignatureConfig PRIMARY KEY (idA2A2))
 STORAGE ( INITIAL 20k NEXT 20k PCTINCREASE 0 )
ENABLE PRIMARY KEY USING INDEX
 TABLESPACE INDX
 STORAGE ( INITIAL 20k NEXT 20k PCTINCREASE 0 )
/
COMMENT ON TABLE ElectronicSignatureConfig IS 'Table ElectronicSignatureConfig created for ext.ziang.model.ElectronicSignatureConfig'
/
REM @//ext/ziang/model/ElectronicSignatureConfig_UserAdditions
