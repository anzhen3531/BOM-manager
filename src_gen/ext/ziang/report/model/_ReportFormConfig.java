package ext.ziang.report.model;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _ReportFormConfig extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final String RESOURCE = "ext.ziang.report.model.modelResource";
   static final String CLASSNAME = ReportFormConfig.class.getName();

   /**
    * 当前SQL的描述
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public static final String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   String description;
   /**
    * 当前SQL的描述
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public String getDescription() {
      return description;
   }
   /**
    * 当前SQL的描述
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public void setDescription(String description) throws wt.util.WTPropertyVetoException {
      descriptionValidate(description);
      this.description = (description != null) ? description.toUpperCase() : null;
   }
   void descriptionValidate(String description) throws wt.util.WTPropertyVetoException {
      if (!wt.fc.IdentityHelper.isChangeable(this))
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.CHANGE_RESTRICTION,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description") },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
      if (description == null || description.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description") },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
      if (DESCRIPTION_UPPER_LIMIT < 1) {
         try { DESCRIPTION_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("description").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESCRIPTION_UPPER_LIMIT = 200; }
      }
      if (description != null && !wt.fc.PersistenceHelper.checkStoredLength(description.toString(), DESCRIPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description"), String.valueOf(Math.min(DESCRIPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
   }

   /**
    * 内容
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public static final String CONTENT = "content";
   static int CONTENT_UPPER_LIMIT = -1;
   String content;
   /**
    * 内容
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public String getContent() {
      return content;
   }
   /**
    * 内容
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public void setContent(String content) throws wt.util.WTPropertyVetoException {
      contentValidate(content);
      this.content = (content != null) ? content.toUpperCase() : null;
   }
   void contentValidate(String content) throws wt.util.WTPropertyVetoException {
      if (!wt.fc.IdentityHelper.isChangeable(this))
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.CHANGE_RESTRICTION,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "content") },
               new java.beans.PropertyChangeEvent(this, "content", this.content, content));
      if (content == null || content.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "content") },
               new java.beans.PropertyChangeEvent(this, "content", this.content, content));
      if (CONTENT_UPPER_LIMIT < 1) {
         try { CONTENT_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("content").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CONTENT_UPPER_LIMIT = 2000; }
      }
      if (content != null && !wt.fc.PersistenceHelper.checkStoredLength(content.toString(), CONTENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "content"), String.valueOf(Math.min(CONTENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "content", this.content, content));
   }

   /**
    * 状态 1为开启 0为关闭
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public static final String STATE = "state";
   static int STATE_UPPER_LIMIT = -1;
   Integer state;
   /**
    * 状态 1为开启 0为关闭
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public Integer getState() {
      return state;
   }
   /**
    * 状态 1为开启 0为关闭
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public void setState(Integer state) throws wt.util.WTPropertyVetoException {
      stateValidate(state);
      this.state = state;
   }
   void stateValidate(Integer state) throws wt.util.WTPropertyVetoException {
      if (state == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "state") },
               new java.beans.PropertyChangeEvent(this, "state", this.state, state));
      if (state != null && ((Number) state).floatValue() > 2)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "state"), String.valueOf(Math.min(STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "state", this.state, state));
   }

   /**
    * 创建者
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public static final String CREATOR = "creator";
   static int CREATOR_UPPER_LIMIT = -1;
   String creator;
   /**
    * 创建者
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public String getCreator() {
      return creator;
   }
   /**
    * 创建者
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public void setCreator(String creator) throws wt.util.WTPropertyVetoException {
      creatorValidate(creator);
      this.creator = creator;
   }
   void creatorValidate(String creator) throws wt.util.WTPropertyVetoException {
      if (creator == null || creator.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creator") },
               new java.beans.PropertyChangeEvent(this, "creator", this.creator, creator));
      if (CREATOR_UPPER_LIMIT < 1) {
         try { CREATOR_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("creator").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATOR_UPPER_LIMIT = 64; }
      }
      if (creator != null && !wt.fc.PersistenceHelper.checkStoredLength(creator.toString(), CREATOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creator"), String.valueOf(Math.min(CREATOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "creator", this.creator, creator));
   }

   /**
    * 修改者
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public static final String MODIFIER = "modifier";
   static int MODIFIER_UPPER_LIMIT = -1;
   String modifier;
   /**
    * 修改者
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public String getModifier() {
      return modifier;
   }
   /**
    * 修改者
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ReportFormConfig
    */
   public void setModifier(String modifier) throws wt.util.WTPropertyVetoException {
      modifierValidate(modifier);
      this.modifier = modifier;
   }
   void modifierValidate(String modifier) throws wt.util.WTPropertyVetoException {
      if (modifier == null || modifier.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifier") },
               new java.beans.PropertyChangeEvent(this, "modifier", this.modifier, modifier));
      if (MODIFIER_UPPER_LIMIT < 1) {
         try { MODIFIER_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifier").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFIER_UPPER_LIMIT = 64; }
      }
      if (modifier != null && !wt.fc.PersistenceHelper.checkStoredLength(modifier.toString(), MODIFIER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifier"), String.valueOf(Math.min(MODIFIER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifier", this.modifier, modifier));
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

   public static final long EXTERNALIZATION_VERSION_UID = 6243985841741719051L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( content );
      output.writeObject( creator );
      output.writeObject( description );
      output.writeObject( modifier );
      output.writeObject( state );
   }

   protected void super_writeExternal_ReportFormConfig(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ReportFormConfig) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ReportFormConfig(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "content", content );
      output.setString( "creator", creator );
      output.setString( "description", description );
      output.setString( "modifier", modifier );
      output.setIntObject( "state", state );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      content = input.getString( "content" );
      creator = input.getString( "creator" );
      description = input.getString( "description" );
      modifier = input.getString( "modifier" );
      state = input.getIntObject( "state" );
   }

   boolean readVersion6243985841741719051L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      content = (String) input.readObject();
      creator = (String) input.readObject();
      description = (String) input.readObject();
      modifier = (String) input.readObject();
      state = (Integer) input.readObject();
      return true;
   }

   protected boolean readVersion( ReportFormConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6243985841741719051L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ReportFormConfig( _ReportFormConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
