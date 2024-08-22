CREATE INDEX PartDeriveLink$COMPOSITE0 ON PartDeriveLink(idA3A5)
 TABLESPACE INDX
 STORAGE ( INITIAL 100k NEXT 100k PCTINCREASE 0 )
/
CREATE INDEX PartDeriveLink$COMPOSITE1 ON PartDeriveLink(idA3B5)
 TABLESPACE INDX
 STORAGE ( INITIAL 100k NEXT 100k PCTINCREASE 0 )
/
CREATE UNIQUE INDEX PartDeriveLink$UNIQUE50 ON PartDeriveLink(idA3A5,idA3B5)
 TABLESPACE INDX
 STORAGE ( INITIAL 100k NEXT 100k PCTINCREASE 0 )
/