package ext.ziang.model;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _ElectronicSignatureConfig extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ziang.model.modelResource";
   static final java.lang.String CLASSNAME = ElectronicSignatureConfig.class.getName();

   /**
    * 文档类型外部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public static final java.lang.String DOC_TYPE = "docType";
   static int DOC_TYPE_UPPER_LIMIT = -1;
   java.lang.String docType;
   /**
    * 文档类型外部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public java.lang.String getDocType() {
      return docType;
   }
   /**
    * 文档类型外部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public void setDocType(java.lang.String docType) throws wt.util.WTPropertyVetoException {
      docTypeValidate(docType);
      this.docType = docType;
   }
   void docTypeValidate(java.lang.String docType) throws wt.util.WTPropertyVetoException {
      if (docType == null || docType.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "docType") },
               new java.beans.PropertyChangeEvent(this, "docType", this.docType, docType));
      if (DOC_TYPE_UPPER_LIMIT < 1) {
         try { DOC_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("docType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOC_TYPE_UPPER_LIMIT = 128; }
      }
      if (docType != null && !wt.fc.PersistenceHelper.checkStoredLength(docType.toString(), DOC_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "docType"), java.lang.String.valueOf(java.lang.Math.min(DOC_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "docType", this.docType, docType));
   }

   /**
    * 文档类型显示名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public static final java.lang.String DOC_TYPE_NAME = "docTypeName";
   static int DOC_TYPE_NAME_UPPER_LIMIT = -1;
   java.lang.String docTypeName;
   /**
    * 文档类型显示名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public java.lang.String getDocTypeName() {
      return docTypeName;
   }
   /**
    * 文档类型显示名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public void setDocTypeName(java.lang.String docTypeName) throws wt.util.WTPropertyVetoException {
      docTypeNameValidate(docTypeName);
      this.docTypeName = docTypeName;
   }
   void docTypeNameValidate(java.lang.String docTypeName) throws wt.util.WTPropertyVetoException {
      if (docTypeName == null || docTypeName.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "docTypeName") },
               new java.beans.PropertyChangeEvent(this, "docTypeName", this.docTypeName, docTypeName));
      if (DOC_TYPE_NAME_UPPER_LIMIT < 1) {
         try { DOC_TYPE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("docTypeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOC_TYPE_NAME_UPPER_LIMIT = 128; }
      }
      if (docTypeName != null && !wt.fc.PersistenceHelper.checkStoredLength(docTypeName.toString(), DOC_TYPE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "docTypeName"), java.lang.String.valueOf(java.lang.Math.min(DOC_TYPE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "docTypeName", this.docTypeName, docTypeName));
   }

   /**
    * 内容类型
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public static final java.lang.String CONTENT_TYPE = "contentType";
   static int CONTENT_TYPE_UPPER_LIMIT = -1;
   java.lang.String contentType;
   /**
    * 内容类型
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public java.lang.String getContentType() {
      return contentType;
   }
   /**
    * 内容类型
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public void setContentType(java.lang.String contentType) throws wt.util.WTPropertyVetoException {
      contentTypeValidate(contentType);
      this.contentType = contentType;
   }
   void contentTypeValidate(java.lang.String contentType) throws wt.util.WTPropertyVetoException {
      if (contentType == null || contentType.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "contentType") },
               new java.beans.PropertyChangeEvent(this, "contentType", this.contentType, contentType));
      if (CONTENT_TYPE_UPPER_LIMIT < 1) {
         try { CONTENT_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("contentType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CONTENT_TYPE_UPPER_LIMIT = 128; }
      }
      if (contentType != null && !wt.fc.PersistenceHelper.checkStoredLength(contentType.toString(), CONTENT_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "contentType"), java.lang.String.valueOf(java.lang.Math.min(CONTENT_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "contentType", this.contentType, contentType));
   }

   /**
    * 活动名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public static final java.lang.String WORK_ITEM_NAME = "workItemName";
   static int WORK_ITEM_NAME_UPPER_LIMIT = -1;
   java.lang.String workItemName;
   /**
    * 活动名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public java.lang.String getWorkItemName() {
      return workItemName;
   }
   /**
    * 活动名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public void setWorkItemName(java.lang.String workItemName) throws wt.util.WTPropertyVetoException {
      workItemNameValidate(workItemName);
      this.workItemName = workItemName;
   }
   void workItemNameValidate(java.lang.String workItemName) throws wt.util.WTPropertyVetoException {
      if (workItemName == null || workItemName.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workItemName") },
               new java.beans.PropertyChangeEvent(this, "workItemName", this.workItemName, workItemName));
      if (WORK_ITEM_NAME_UPPER_LIMIT < 1) {
         try { WORK_ITEM_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workItemName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_ITEM_NAME_UPPER_LIMIT = 128; }
      }
      if (workItemName != null && !wt.fc.PersistenceHelper.checkStoredLength(workItemName.toString(), WORK_ITEM_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workItemName"), java.lang.String.valueOf(java.lang.Math.min(WORK_ITEM_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workItemName", this.workItemName, workItemName));
   }

   /**
    * 签字X轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public static final java.lang.String SIGN_XINDEX = "signXIndex";
   static int SIGN_XINDEX_UPPER_LIMIT = -1;
   java.lang.String signXIndex;
   /**
    * 签字X轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public java.lang.String getSignXIndex() {
      return signXIndex;
   }
   /**
    * 签字X轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public void setSignXIndex(java.lang.String signXIndex) throws wt.util.WTPropertyVetoException {
      signXIndexValidate(signXIndex);
      this.signXIndex = signXIndex;
   }
   void signXIndexValidate(java.lang.String signXIndex) throws wt.util.WTPropertyVetoException {
      if (SIGN_XINDEX_UPPER_LIMIT < 1) {
         try { SIGN_XINDEX_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("signXIndex").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SIGN_XINDEX_UPPER_LIMIT = 128; }
      }
      if (signXIndex != null && !wt.fc.PersistenceHelper.checkStoredLength(signXIndex.toString(), SIGN_XINDEX_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "signXIndex"), java.lang.String.valueOf(java.lang.Math.min(SIGN_XINDEX_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "signXIndex", this.signXIndex, signXIndex));
   }

   /**
    * 签字Y轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public static final java.lang.String SIGN_YINDEX = "signYIndex";
   static int SIGN_YINDEX_UPPER_LIMIT = -1;
   java.lang.String signYIndex;
   /**
    * 签字Y轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public java.lang.String getSignYIndex() {
      return signYIndex;
   }
   /**
    * 签字Y轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public void setSignYIndex(java.lang.String signYIndex) throws wt.util.WTPropertyVetoException {
      signYIndexValidate(signYIndex);
      this.signYIndex = signYIndex;
   }
   void signYIndexValidate(java.lang.String signYIndex) throws wt.util.WTPropertyVetoException {
      if (SIGN_YINDEX_UPPER_LIMIT < 1) {
         try { SIGN_YINDEX_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("signYIndex").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SIGN_YINDEX_UPPER_LIMIT = 128; }
      }
      if (signYIndex != null && !wt.fc.PersistenceHelper.checkStoredLength(signYIndex.toString(), SIGN_YINDEX_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "signYIndex"), java.lang.String.valueOf(java.lang.Math.min(SIGN_YINDEX_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "signYIndex", this.signYIndex, signYIndex));
   }

   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public static final java.lang.String STATUS = "status";
   static int STATUS_UPPER_LIMIT = -1;
   java.lang.Integer status;
   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public java.lang.Integer getStatus() {
      return status;
   }
   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public void setStatus(java.lang.Integer status) throws wt.util.WTPropertyVetoException {
      statusValidate(status);
      this.status = status;
   }
   void statusValidate(java.lang.Integer status) throws wt.util.WTPropertyVetoException {
      if (status != null && ((java.lang.Number) status).floatValue() > 2)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "status"), java.lang.String.valueOf(java.lang.Math.min(STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "status", this.status, status));
   }

   /**
    * 扩展字段
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public static final java.lang.String EXTENDED_FIELD = "extendedField";
   static int EXTENDED_FIELD_UPPER_LIMIT = -1;
   java.lang.String extendedField;
   /**
    * 扩展字段
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public java.lang.String getExtendedField() {
      return extendedField;
   }
   /**
    * 扩展字段
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public void setExtendedField(java.lang.String extendedField) throws wt.util.WTPropertyVetoException {
      extendedFieldValidate(extendedField);
      this.extendedField = extendedField;
   }
   void extendedFieldValidate(java.lang.String extendedField) throws wt.util.WTPropertyVetoException {
      if (EXTENDED_FIELD_UPPER_LIMIT < 1) {
         try { EXTENDED_FIELD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("extendedField").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXTENDED_FIELD_UPPER_LIMIT = 128; }
      }
      if (extendedField != null && !wt.fc.PersistenceHelper.checkStoredLength(extendedField.toString(), EXTENDED_FIELD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "extendedField"), java.lang.String.valueOf(java.lang.Math.min(EXTENDED_FIELD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "extendedField", this.extendedField, extendedField));
   }

   /**
    * 扩展字段1
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public static final java.lang.String EXTENDED_FIELD1 = "extendedField1";
   static int EXTENDED_FIELD1_UPPER_LIMIT = -1;
   java.lang.String extendedField1;
   /**
    * 扩展字段1
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public java.lang.String getExtendedField1() {
      return extendedField1;
   }
   /**
    * 扩展字段1
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public void setExtendedField1(java.lang.String extendedField1) throws wt.util.WTPropertyVetoException {
      extendedField1Validate(extendedField1);
      this.extendedField1 = extendedField1;
   }
   void extendedField1Validate(java.lang.String extendedField1) throws wt.util.WTPropertyVetoException {
      if (EXTENDED_FIELD1_UPPER_LIMIT < 1) {
         try { EXTENDED_FIELD1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("extendedField1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXTENDED_FIELD1_UPPER_LIMIT = 128; }
      }
      if (extendedField1 != null && !wt.fc.PersistenceHelper.checkStoredLength(extendedField1.toString(), EXTENDED_FIELD1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "extendedField1"), java.lang.String.valueOf(java.lang.Math.min(EXTENDED_FIELD1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "extendedField1", this.extendedField1, extendedField1));
   }

   /**
    * 扩展字段2
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public static final java.lang.String EXTENDED_FIELD2 = "extendedField2";
   static int EXTENDED_FIELD2_UPPER_LIMIT = -1;
   java.lang.String extendedField2;
   /**
    * 扩展字段2
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public java.lang.String getExtendedField2() {
      return extendedField2;
   }
   /**
    * 扩展字段2
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.ElectronicSignatureConfig
    */
   public void setExtendedField2(java.lang.String extendedField2) throws wt.util.WTPropertyVetoException {
      extendedField2Validate(extendedField2);
      this.extendedField2 = extendedField2;
   }
   void extendedField2Validate(java.lang.String extendedField2) throws wt.util.WTPropertyVetoException {
      if (EXTENDED_FIELD2_UPPER_LIMIT < 1) {
         try { EXTENDED_FIELD2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("extendedField2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXTENDED_FIELD2_UPPER_LIMIT = 128; }
      }
      if (extendedField2 != null && !wt.fc.PersistenceHelper.checkStoredLength(extendedField2.toString(), EXTENDED_FIELD2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "extendedField2"), java.lang.String.valueOf(java.lang.Math.min(EXTENDED_FIELD2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "extendedField2", this.extendedField2, extendedField2));
   }

   public java.lang.String getConceptualClassname() {
      return CLASSNAME;
   }

   public wt.introspection.ClassInfo getClassInfo() throws wt.introspection.WTIntrospectionException {
      return wt.introspection.WTIntrospector.getClassInfo(getConceptualClassname());
   }

   public java.lang.String getType() {
      try { return getClassInfo().getDisplayName(); }
      catch (wt.introspection.WTIntrospectionException wte) { return wt.util.WTStringUtilities.tail(getConceptualClassname(), '.'); }
   }

   public static final long EXTERNALIZATION_VERSION_UID = -9132151901755674697L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( contentType );
      output.writeObject( docType );
      output.writeObject( docTypeName );
      output.writeObject( extendedField );
      output.writeObject( extendedField1 );
      output.writeObject( extendedField2 );
      output.writeObject( signXIndex );
      output.writeObject( signYIndex );
      output.writeObject( status );
      output.writeObject( workItemName );
   }

   protected void super_writeExternal_ElectronicSignatureConfig(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ziang.model.ElectronicSignatureConfig) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ElectronicSignatureConfig(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "contentType", contentType );
      output.setString( "docType", docType );
      output.setString( "docTypeName", docTypeName );
      output.setString( "extendedField", extendedField );
      output.setString( "extendedField1", extendedField1 );
      output.setString( "extendedField2", extendedField2 );
      output.setString( "signXIndex", signXIndex );
      output.setString( "signYIndex", signYIndex );
      output.setIntObject( "status", status );
      output.setString( "workItemName", workItemName );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      contentType = input.getString( "contentType" );
      docType = input.getString( "docType" );
      docTypeName = input.getString( "docTypeName" );
      extendedField = input.getString( "extendedField" );
      extendedField1 = input.getString( "extendedField1" );
      extendedField2 = input.getString( "extendedField2" );
      signXIndex = input.getString( "signXIndex" );
      signYIndex = input.getString( "signYIndex" );
      status = input.getIntObject( "status" );
      workItemName = input.getString( "workItemName" );
   }

   boolean readVersion_9132151901755674697L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      contentType = (java.lang.String) input.readObject();
      docType = (java.lang.String) input.readObject();
      docTypeName = (java.lang.String) input.readObject();
      extendedField = (java.lang.String) input.readObject();
      extendedField1 = (java.lang.String) input.readObject();
      extendedField2 = (java.lang.String) input.readObject();
      signXIndex = (java.lang.String) input.readObject();
      signYIndex = (java.lang.String) input.readObject();
      status = (java.lang.Integer) input.readObject();
      workItemName = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( ElectronicSignatureConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_9132151901755674697L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ElectronicSignatureConfig( _ElectronicSignatureConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
