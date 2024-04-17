package ext.ziang.modelGen;

import ext.ziang.model.ElectronicSignatureConfig;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _ElectronicSignatureConfig extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final String RESOURCE = "ext.ziang.model.modelResource";
   static final String CLASSNAME = ElectronicSignatureConfig.class.getName();

   /**
    * @see ElectronicSignatureConfig
    */
   public static final String OBJECT_TYPE = "objectType";
   wt.fc.ObjectReference objectType;
   /**
    * @see ElectronicSignatureConfig
    */
   public wt.fc.ObjectReference getObjectType() {
      return objectType;
   }
   /**
    * @see ElectronicSignatureConfig
    */
   public void setObjectType(wt.fc.ObjectReference objectType) throws wt.util.WTPropertyVetoException {
      objectTypeValidate(objectType);
      this.objectType = objectType;
   }
   void objectTypeValidate(wt.fc.ObjectReference objectType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 内容类型
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public static final String CONTENT_TYPE = "contentType";
   static int CONTENT_TYPE_UPPER_LIMIT = -1;
   String contentType;
   /**
    * 内容类型
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public String getContentType() {
      return contentType;
   }
   /**
    * 内容类型
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public void setContentType(String contentType) throws wt.util.WTPropertyVetoException {
      contentTypeValidate(contentType);
      this.contentType = contentType;
   }
   void contentTypeValidate(String contentType) throws wt.util.WTPropertyVetoException {
      if (contentType == null || contentType.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "contentType") },
               new java.beans.PropertyChangeEvent(this, "contentType", this.contentType, contentType));
      if (CONTENT_TYPE_UPPER_LIMIT < 1) {
         try { CONTENT_TYPE_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("contentType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CONTENT_TYPE_UPPER_LIMIT = 128; }
      }
      if (contentType != null && !wt.fc.PersistenceHelper.checkStoredLength(contentType.toString(), CONTENT_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "contentType"), String.valueOf(Math.min(CONTENT_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "contentType", this.contentType, contentType));
   }

   /**
    * 签字X轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public static final String SIGN_XINDEX = "signXIndex";
   static int SIGN_XINDEX_UPPER_LIMIT = -1;
   String signXIndex;
   /**
    * 签字X轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public String getSignXIndex() {
      return signXIndex;
   }
   /**
    * 签字X轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public void setSignXIndex(String signXIndex) throws wt.util.WTPropertyVetoException {
      signXIndexValidate(signXIndex);
      this.signXIndex = signXIndex;
   }
   void signXIndexValidate(String signXIndex) throws wt.util.WTPropertyVetoException {
      if (SIGN_XINDEX_UPPER_LIMIT < 1) {
         try { SIGN_XINDEX_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("signXIndex").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SIGN_XINDEX_UPPER_LIMIT = 128; }
      }
      if (signXIndex != null && !wt.fc.PersistenceHelper.checkStoredLength(signXIndex.toString(), SIGN_XINDEX_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "signXIndex"), String.valueOf(Math.min(SIGN_XINDEX_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "signXIndex", this.signXIndex, signXIndex));
   }

   /**
    * 签字Y轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public static final String SIGN_YINDEX = "signYIndex";
   static int SIGN_YINDEX_UPPER_LIMIT = -1;
   String signYIndex;
   /**
    * 签字Y轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public String getSignYIndex() {
      return signYIndex;
   }
   /**
    * 签字Y轴索引
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public void setSignYIndex(String signYIndex) throws wt.util.WTPropertyVetoException {
      signYIndexValidate(signYIndex);
      this.signYIndex = signYIndex;
   }
   void signYIndexValidate(String signYIndex) throws wt.util.WTPropertyVetoException {
      if (SIGN_YINDEX_UPPER_LIMIT < 1) {
         try { SIGN_YINDEX_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("signYIndex").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SIGN_YINDEX_UPPER_LIMIT = 128; }
      }
      if (signYIndex != null && !wt.fc.PersistenceHelper.checkStoredLength(signYIndex.toString(), SIGN_YINDEX_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "signYIndex"), String.valueOf(Math.min(SIGN_YINDEX_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "signYIndex", this.signYIndex, signYIndex));
   }

   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public static final String STATUS = "status";
   static int STATUS_UPPER_LIMIT = -1;
   String status;
   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public String getStatus() {
      return status;
   }
   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public void setStatus(String status) throws wt.util.WTPropertyVetoException {
      statusValidate(status);
      this.status = status;
   }
   void statusValidate(String status) throws wt.util.WTPropertyVetoException {
      if (STATUS_UPPER_LIMIT < 1) {
         try { STATUS_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("status").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STATUS_UPPER_LIMIT = 128; }
      }
      if (status != null && !wt.fc.PersistenceHelper.checkStoredLength(status.toString(), STATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "status"), String.valueOf(Math.min(STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "status", this.status, status));
   }

   /**
    * 扩展字段
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public static final String EXTENDED_FIELD = "extendedField";
   static int EXTENDED_FIELD_UPPER_LIMIT = -1;
   String extendedField;
   /**
    * 扩展字段
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public String getExtendedField() {
      return extendedField;
   }
   /**
    * 扩展字段
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public void setExtendedField(String extendedField) throws wt.util.WTPropertyVetoException {
      extendedFieldValidate(extendedField);
      this.extendedField = extendedField;
   }
   void extendedFieldValidate(String extendedField) throws wt.util.WTPropertyVetoException {
      if (EXTENDED_FIELD_UPPER_LIMIT < 1) {
         try { EXTENDED_FIELD_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("extendedField").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXTENDED_FIELD_UPPER_LIMIT = 128; }
      }
      if (extendedField != null && !wt.fc.PersistenceHelper.checkStoredLength(extendedField.toString(), EXTENDED_FIELD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "extendedField"), String.valueOf(Math.min(EXTENDED_FIELD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "extendedField", this.extendedField, extendedField));
   }

   /**
    * 扩展字段1
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public static final String EXTENDED_FIELD1 = "extendedField1";
   static int EXTENDED_FIELD1_UPPER_LIMIT = -1;
   Integer extendedField1;
   /**
    * 扩展字段1
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public Integer getExtendedField1() {
      return extendedField1;
   }
   /**
    * 扩展字段1
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ElectronicSignatureConfig
    */
   public void setExtendedField1(Integer extendedField1) throws wt.util.WTPropertyVetoException {
      extendedField1Validate(extendedField1);
      this.extendedField1 = extendedField1;
   }
   void extendedField1Validate(Integer extendedField1) throws wt.util.WTPropertyVetoException {
      if (extendedField1 != null && ((Number) extendedField1).floatValue() > 2)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "extendedField1"), String.valueOf(Math.min(EXTENDED_FIELD1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "extendedField1", this.extendedField1, extendedField1));
   }

   public String getConceptualClassname() {
      return CLASSNAME;
   }

   public wt.introspection.ClassInfo getClassInfo() throws wt.introspection.WTIntrospectionException {
      return wt.introspection.WTIntrospector.getClassInfo(getConceptualClassname());
   }

   public String getType() {
      try { return getClassInfo().getDisplayName(); }
      catch (wt.introspection.WTIntrospectionException wte) { return wt.util.WTStringUtilities.tail(getConceptualClassname(), '.'); }
   }

   public static final long EXTERNALIZATION_VERSION_UID = 8591467240660100996L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( contentType );
      output.writeObject( extendedField );
      output.writeObject( extendedField1 );
      output.writeObject( objectType );
      output.writeObject( signXIndex );
      output.writeObject( signYIndex );
      output.writeObject( status );
   }

   protected void super_writeExternal_ElectronicSignatureConfig(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ElectronicSignatureConfig) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ElectronicSignatureConfig(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "contentType", contentType );
      output.setString( "extendedField", extendedField );
      output.setIntObject( "extendedField1", extendedField1 );
      output.writeObject( "objectType", objectType, wt.fc.ObjectReference.class, true );
      output.setString( "signXIndex", signXIndex );
      output.setString( "signYIndex", signYIndex );
      output.setString( "status", status );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      contentType = input.getString( "contentType" );
      extendedField = input.getString( "extendedField" );
      extendedField1 = input.getIntObject( "extendedField1" );
      objectType = (wt.fc.ObjectReference) input.readObject( "objectType", objectType, wt.fc.ObjectReference.class, true );
      signXIndex = input.getString( "signXIndex" );
      signYIndex = input.getString( "signYIndex" );
      status = input.getString( "status" );
   }

   boolean readVersion8591467240660100996L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      contentType = (String) input.readObject();
      extendedField = (String) input.readObject();
      extendedField1 = (Integer) input.readObject();
      objectType = (wt.fc.ObjectReference) input.readObject();
      signXIndex = (String) input.readObject();
      signYIndex = (String) input.readObject();
      status = (String) input.readObject();
      return true;
   }

   protected boolean readVersion( ElectronicSignatureConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion8591467240660100996L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ElectronicSignatureConfig( _ElectronicSignatureConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
