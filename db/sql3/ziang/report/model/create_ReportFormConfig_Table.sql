set echo on
REM Creating table ReportFormConfig for ext.ziang.report.model.ReportFormConfig
set echo off
CREATE TABLE ReportFormConfig (
   SqlScript   VARCHAR2(4000) NOT NULL,
   creator   VARCHAR2(192) NOT NULL,
   description   VARCHAR2(600) NOT NULL,
   modifier   VARCHAR2(192) NOT NULL,
   state   NUMBER NOT NULL,
   createStampA2   DATE,
   markForDeleteA2   NUMBER NOT NULL,
   modifyStampA2   DATE,
   classnameA2A2   VARCHAR2(600),
   idA2A2   NUMBER NOT NULL,
   updateCountA2   NUMBER,
   updateStampA2   DATE,
 CONSTRAINT PK_ReportFormConfig PRIMARY KEY (idA2A2))
 STORAGE ( INITIAL 20k NEXT 20k PCTINCREASE 0 )
ENABLE PRIMARY KEY USING INDEX
 TABLESPACE INDX
 STORAGE ( INITIAL 20k NEXT 20k PCTINCREASE 0 )
/
COMMENT ON TABLE ReportFormConfig IS 'Table ReportFormConfig created for ext.ziang.report.model.ReportFormConfig'
/
REM @//ext/ziang/report/model/ReportFormConfig_UserAdditions
