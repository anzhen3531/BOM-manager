package ext.ziang.model;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _CommonFilterConfig extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final String RESOURCE = "ext.ziang.model.modelResource";
   static final String CLASSNAME = CommonFilterConfig.class.getName();

   /**
    * 按钮名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public static final String ACTION_NAME = "actionName";
   static int ACTION_NAME_UPPER_LIMIT = -1;
   String actionName;
   /**
    * 按钮名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public String getActionName() {
      return actionName;
   }
   /**
    * 按钮名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public void setActionName(String actionName) throws wt.util.WTPropertyVetoException {
      actionNameValidate(actionName);
      this.actionName = actionName;
   }
   void actionNameValidate(String actionName) throws wt.util.WTPropertyVetoException {
      if (ACTION_NAME_UPPER_LIMIT < 1) {
         try { ACTION_NAME_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("actionName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTION_NAME_UPPER_LIMIT = 64; }
      }
      if (actionName != null && !wt.fc.PersistenceHelper.checkStoredLength(actionName.toString(), ACTION_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionName"), String.valueOf(Math.min(ACTION_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "actionName", this.actionName, actionName));
   }

   /**
    * 角色
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public static final String ROLE_NAME = "roleName";
   static int ROLE_NAME_UPPER_LIMIT = -1;
   String roleName;
   /**
    * 角色
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public String getRoleName() {
      return roleName;
   }
   /**
    * 角色
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public void setRoleName(String roleName) throws wt.util.WTPropertyVetoException {
      roleNameValidate(roleName);
      this.roleName = roleName;
   }
   void roleNameValidate(String roleName) throws wt.util.WTPropertyVetoException {
      if (ROLE_NAME_UPPER_LIMIT < 1) {
         try { ROLE_NAME_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_NAME_UPPER_LIMIT = 64; }
      }
      if (roleName != null && !wt.fc.PersistenceHelper.checkStoredLength(roleName.toString(), ROLE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleName"), String.valueOf(Math.min(ROLE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleName", this.roleName, roleName));
   }

   /**
    * 团队
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public static final String GROUP_NAME = "groupName";
   static int GROUP_NAME_UPPER_LIMIT = -1;
   String groupName;
   /**
    * 团队
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public String getGroupName() {
      return groupName;
   }
   /**
    * 团队
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public void setGroupName(String groupName) throws wt.util.WTPropertyVetoException {
      groupNameValidate(groupName);
      this.groupName = groupName;
   }
   void groupNameValidate(String groupName) throws wt.util.WTPropertyVetoException {
      if (GROUP_NAME_UPPER_LIMIT < 1) {
         try { GROUP_NAME_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("groupName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { GROUP_NAME_UPPER_LIMIT = 64; }
      }
      if (groupName != null && !wt.fc.PersistenceHelper.checkStoredLength(groupName.toString(), GROUP_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "groupName"), String.valueOf(Math.min(GROUP_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "groupName", this.groupName, groupName));
   }

   /**
    * 团队内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public static final String GROUP_INNER_NAME = "groupInnerName";
   static int GROUP_INNER_NAME_UPPER_LIMIT = -1;
   String groupInnerName;
   /**
    * 团队内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public String getGroupInnerName() {
      return groupInnerName;
   }
   /**
    * 团队内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public void setGroupInnerName(String groupInnerName) throws wt.util.WTPropertyVetoException {
      groupInnerNameValidate(groupInnerName);
      this.groupInnerName = groupInnerName;
   }
   void groupInnerNameValidate(String groupInnerName) throws wt.util.WTPropertyVetoException {
      if (GROUP_INNER_NAME_UPPER_LIMIT < 1) {
         try { GROUP_INNER_NAME_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("groupInnerName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { GROUP_INNER_NAME_UPPER_LIMIT = 64; }
      }
      if (groupInnerName != null && !wt.fc.PersistenceHelper.checkStoredLength(groupInnerName.toString(), GROUP_INNER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "groupInnerName"), String.valueOf(Math.min(GROUP_INNER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "groupInnerName", this.groupInnerName, groupInnerName));
   }

   /**
    * 类型名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public static final String TYPE_NAME = "typeName";
   static int TYPE_NAME_UPPER_LIMIT = -1;
   String typeName;
   /**
    * 类型名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public String getTypeName() {
      return typeName;
   }
   /**
    * 类型名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public void setTypeName(String typeName) throws wt.util.WTPropertyVetoException {
      typeNameValidate(typeName);
      this.typeName = typeName;
   }
   void typeNameValidate(String typeName) throws wt.util.WTPropertyVetoException {
      if (TYPE_NAME_UPPER_LIMIT < 1) {
         try { TYPE_NAME_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("typeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TYPE_NAME_UPPER_LIMIT = 64; }
      }
      if (typeName != null && !wt.fc.PersistenceHelper.checkStoredLength(typeName.toString(), TYPE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "typeName"), String.valueOf(Math.min(TYPE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "typeName", this.typeName, typeName));
   }

   /**
    * 类型内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public static final String TYPE_INNER_NAME = "typeInnerName";
   static int TYPE_INNER_NAME_UPPER_LIMIT = -1;
   String typeInnerName;
   /**
    * 类型内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public String getTypeInnerName() {
      return typeInnerName;
   }
   /**
    * 类型内部名称
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public void setTypeInnerName(String typeInnerName) throws wt.util.WTPropertyVetoException {
      typeInnerNameValidate(typeInnerName);
      this.typeInnerName = typeInnerName;
   }
   void typeInnerNameValidate(String typeInnerName) throws wt.util.WTPropertyVetoException {
      if (TYPE_INNER_NAME_UPPER_LIMIT < 1) {
         try { TYPE_INNER_NAME_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("typeInnerName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TYPE_INNER_NAME_UPPER_LIMIT = 128; }
      }
      if (typeInnerName != null && !wt.fc.PersistenceHelper.checkStoredLength(typeInnerName.toString(), TYPE_INNER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "typeInnerName"), String.valueOf(Math.min(TYPE_INNER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "typeInnerName", this.typeInnerName, typeInnerName));
   }

   /**
    * 生命周期状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public static final String LIFECYCLE_STATE = "lifecycleState";
   static int LIFECYCLE_STATE_UPPER_LIMIT = -1;
   String lifecycleState;
   /**
    * 生命周期状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public String getLifecycleState() {
      return lifecycleState;
   }
   /**
    * 生命周期状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see CommonFilterConfig
    */
   public void setLifecycleState(String lifecycleState) throws wt.util.WTPropertyVetoException {
      lifecycleStateValidate(lifecycleState);
      this.lifecycleState = lifecycleState;
   }
   void lifecycleStateValidate(String lifecycleState) throws wt.util.WTPropertyVetoException {
      if (LIFECYCLE_STATE_UPPER_LIMIT < 1) {
         try { LIFECYCLE_STATE_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lifecycleState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LIFECYCLE_STATE_UPPER_LIMIT = 64; }
      }
      if (lifecycleState != null && !wt.fc.PersistenceHelper.checkStoredLength(lifecycleState.toString(), LIFECYCLE_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lifecycleState"), String.valueOf(Math.min(LIFECYCLE_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lifecycleState", this.lifecycleState, lifecycleState));
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

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (CommonFilterConfig) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_CommonFilterConfig(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
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

   boolean readVersion4589292226265852034L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      actionName = (String) input.readObject();
      groupInnerName = (String) input.readObject();
      groupName = (String) input.readObject();
      lifecycleState = (String) input.readObject();
      roleName = (String) input.readObject();
      typeInnerName = (String) input.readObject();
      typeName = (String) input.readObject();
      return true;
   }

   protected boolean readVersion( CommonFilterConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4589292226265852034L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_CommonFilterConfig( _CommonFilterConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
