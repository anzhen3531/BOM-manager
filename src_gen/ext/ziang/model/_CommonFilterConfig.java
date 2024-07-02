package ext.ziang.model;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _CommonFilterConfig extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ziang.model.modelResource";
   static final java.lang.String CLASSNAME = CommonFilterConfig.class.getName();

   /**
    * 按钮名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public static final java.lang.String ACTION_NAME = "actionName";
   static int ACTION_NAME_UPPER_LIMIT = -1;
   java.lang.String actionName;
   /**
    * 按钮名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public java.lang.String getActionName() {
      return actionName;
   }
   /**
    * 按钮名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public void setActionName(java.lang.String actionName) throws wt.util.WTPropertyVetoException {
      actionNameValidate(actionName);
      this.actionName = actionName;
   }
   void actionNameValidate(java.lang.String actionName) throws wt.util.WTPropertyVetoException {
      if (actionName == null || actionName.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionName") },
               new java.beans.PropertyChangeEvent(this, "actionName", this.actionName, actionName));
      if (ACTION_NAME_UPPER_LIMIT < 1) {
         try { ACTION_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("actionName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTION_NAME_UPPER_LIMIT = 64; }
      }
      if (actionName != null && !wt.fc.PersistenceHelper.checkStoredLength(actionName.toString(), ACTION_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionName"), java.lang.String.valueOf(java.lang.Math.min(ACTION_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "actionName", this.actionName, actionName));
   }

   /**
    * 角色
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public static final java.lang.String ROLE_NAME = "roleName";
   static int ROLE_NAME_UPPER_LIMIT = -1;
   java.lang.String roleName;
   /**
    * 角色
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public java.lang.String getRoleName() {
      return roleName;
   }
   /**
    * 角色
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public void setRoleName(java.lang.String roleName) throws wt.util.WTPropertyVetoException {
      roleNameValidate(roleName);
      this.roleName = roleName;
   }
   void roleNameValidate(java.lang.String roleName) throws wt.util.WTPropertyVetoException {
      if (ROLE_NAME_UPPER_LIMIT < 1) {
         try { ROLE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_NAME_UPPER_LIMIT = 64; }
      }
      if (roleName != null && !wt.fc.PersistenceHelper.checkStoredLength(roleName.toString(), ROLE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleName"), java.lang.String.valueOf(java.lang.Math.min(ROLE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleName", this.roleName, roleName));
   }

   /**
    * 团队
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public static final java.lang.String GROUP_NAME = "groupName";
   static int GROUP_NAME_UPPER_LIMIT = -1;
   java.lang.String groupName;
   /**
    * 团队
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public java.lang.String getGroupName() {
      return groupName;
   }
   /**
    * 团队
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public void setGroupName(java.lang.String groupName) throws wt.util.WTPropertyVetoException {
      groupNameValidate(groupName);
      this.groupName = groupName;
   }
   void groupNameValidate(java.lang.String groupName) throws wt.util.WTPropertyVetoException {
      if (GROUP_NAME_UPPER_LIMIT < 1) {
         try { GROUP_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("groupName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { GROUP_NAME_UPPER_LIMIT = 64; }
      }
      if (groupName != null && !wt.fc.PersistenceHelper.checkStoredLength(groupName.toString(), GROUP_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "groupName"), java.lang.String.valueOf(java.lang.Math.min(GROUP_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "groupName", this.groupName, groupName));
   }

   /**
    * 团队内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public static final java.lang.String GROUP_INNER_NAME = "groupInnerName";
   static int GROUP_INNER_NAME_UPPER_LIMIT = -1;
   java.lang.String groupInnerName;
   /**
    * 团队内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public java.lang.String getGroupInnerName() {
      return groupInnerName;
   }
   /**
    * 团队内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public void setGroupInnerName(java.lang.String groupInnerName) throws wt.util.WTPropertyVetoException {
      groupInnerNameValidate(groupInnerName);
      this.groupInnerName = groupInnerName;
   }
   void groupInnerNameValidate(java.lang.String groupInnerName) throws wt.util.WTPropertyVetoException {
      if (GROUP_INNER_NAME_UPPER_LIMIT < 1) {
         try { GROUP_INNER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("groupInnerName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { GROUP_INNER_NAME_UPPER_LIMIT = 64; }
      }
      if (groupInnerName != null && !wt.fc.PersistenceHelper.checkStoredLength(groupInnerName.toString(), GROUP_INNER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "groupInnerName"), java.lang.String.valueOf(java.lang.Math.min(GROUP_INNER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "groupInnerName", this.groupInnerName, groupInnerName));
   }

   /**
    * 类型名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public static final java.lang.String TYPE_NAME = "typeName";
   static int TYPE_NAME_UPPER_LIMIT = -1;
   java.lang.String typeName;
   /**
    * 类型名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public java.lang.String getTypeName() {
      return typeName;
   }
   /**
    * 类型名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public void setTypeName(java.lang.String typeName) throws wt.util.WTPropertyVetoException {
      typeNameValidate(typeName);
      this.typeName = typeName;
   }
   void typeNameValidate(java.lang.String typeName) throws wt.util.WTPropertyVetoException {
      if (TYPE_NAME_UPPER_LIMIT < 1) {
         try { TYPE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("typeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TYPE_NAME_UPPER_LIMIT = 64; }
      }
      if (typeName != null && !wt.fc.PersistenceHelper.checkStoredLength(typeName.toString(), TYPE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "typeName"), java.lang.String.valueOf(java.lang.Math.min(TYPE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "typeName", this.typeName, typeName));
   }

   /**
    * 类型内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public static final java.lang.String TYPE_INNER_NAME = "typeInnerName";
   static int TYPE_INNER_NAME_UPPER_LIMIT = -1;
   java.lang.String typeInnerName;
   /**
    * 类型内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public java.lang.String getTypeInnerName() {
      return typeInnerName;
   }
   /**
    * 类型内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public void setTypeInnerName(java.lang.String typeInnerName) throws wt.util.WTPropertyVetoException {
      typeInnerNameValidate(typeInnerName);
      this.typeInnerName = typeInnerName;
   }
   void typeInnerNameValidate(java.lang.String typeInnerName) throws wt.util.WTPropertyVetoException {
      if (typeInnerName == null || typeInnerName.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "typeInnerName") },
               new java.beans.PropertyChangeEvent(this, "typeInnerName", this.typeInnerName, typeInnerName));
      if (TYPE_INNER_NAME_UPPER_LIMIT < 1) {
         try { TYPE_INNER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("typeInnerName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TYPE_INNER_NAME_UPPER_LIMIT = 128; }
      }
      if (typeInnerName != null && !wt.fc.PersistenceHelper.checkStoredLength(typeInnerName.toString(), TYPE_INNER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "typeInnerName"), java.lang.String.valueOf(java.lang.Math.min(TYPE_INNER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "typeInnerName", this.typeInnerName, typeInnerName));
   }

   /**
    * 生命周期状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public static final java.lang.String LIFECYCLE_STATE = "lifecycleState";
   static int LIFECYCLE_STATE_UPPER_LIMIT = -1;
   java.lang.String lifecycleState;
   /**
    * 生命周期状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public java.lang.String getLifecycleState() {
      return lifecycleState;
   }
   /**
    * 生命周期状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.CommonFilterConfig
    */
   public void setLifecycleState(java.lang.String lifecycleState) throws wt.util.WTPropertyVetoException {
      lifecycleStateValidate(lifecycleState);
      this.lifecycleState = lifecycleState;
   }
   void lifecycleStateValidate(java.lang.String lifecycleState) throws wt.util.WTPropertyVetoException {
      if (LIFECYCLE_STATE_UPPER_LIMIT < 1) {
         try { LIFECYCLE_STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lifecycleState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LIFECYCLE_STATE_UPPER_LIMIT = 64; }
      }
      if (lifecycleState != null && !wt.fc.PersistenceHelper.checkStoredLength(lifecycleState.toString(), LIFECYCLE_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lifecycleState"), java.lang.String.valueOf(java.lang.Math.min(LIFECYCLE_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lifecycleState", this.lifecycleState, lifecycleState));
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

   public static final long EXTERNALIZATION_VERSION_UID = 4589292226265852034L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( actionName );
      output.writeObject( groupInnerName );
      output.writeObject( groupName );
      output.writeObject( lifecycleState );
      output.writeObject( roleName );
      output.writeObject( typeInnerName );
      output.writeObject( typeName );
   }

   protected void super_writeExternal_CommonFilterConfig(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ziang.model.CommonFilterConfig) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_CommonFilterConfig(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "actionName", actionName );
      output.setString( "groupInnerName", groupInnerName );
      output.setString( "groupName", groupName );
      output.setString( "lifecycleState", lifecycleState );
      output.setString( "roleName", roleName );
      output.setString( "typeInnerName", typeInnerName );
      output.setString( "typeName", typeName );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      actionName = input.getString( "actionName" );
      groupInnerName = input.getString( "groupInnerName" );
      groupName = input.getString( "groupName" );
      lifecycleState = input.getString( "lifecycleState" );
      roleName = input.getString( "roleName" );
      typeInnerName = input.getString( "typeInnerName" );
      typeName = input.getString( "typeName" );
   }

   boolean readVersion4589292226265852034L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      actionName = (java.lang.String) input.readObject();
      groupInnerName = (java.lang.String) input.readObject();
      groupName = (java.lang.String) input.readObject();
      lifecycleState = (java.lang.String) input.readObject();
      roleName = (java.lang.String) input.readObject();
      typeInnerName = (java.lang.String) input.readObject();
      typeName = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CommonFilterConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4589292226265852034L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_CommonFilterConfig( _CommonFilterConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
