package ext.ziang.model;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _UserExtendInformation extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ziang.model.modelResource";
   static final java.lang.String CLASSNAME = UserExtendInformation.class.getName();

   /**
    * 用户名
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public static final java.lang.String USERNAME = "username";
   static int USERNAME_UPPER_LIMIT = -1;
   java.lang.String username;
   /**
    * 用户名
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public java.lang.String getUsername() {
      return username;
   }
   /**
    * 用户名
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public void setUsername(java.lang.String username) throws wt.util.WTPropertyVetoException {
      usernameValidate(username);
      this.username = username;
   }
   void usernameValidate(java.lang.String username) throws wt.util.WTPropertyVetoException {
      if (username == null || username.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "username") },
               new java.beans.PropertyChangeEvent(this, "username", this.username, username));
      if (USERNAME_UPPER_LIMIT < 1) {
         try { USERNAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("username").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { USERNAME_UPPER_LIMIT = 128; }
      }
      if (username != null && !wt.fc.PersistenceHelper.checkStoredLength(username.toString(), USERNAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "username"), java.lang.String.valueOf(java.lang.Math.min(USERNAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "username", this.username, username));
   }

   /**
    * 密码
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public static final java.lang.String PASSWORD = "password";
   static int PASSWORD_UPPER_LIMIT = -1;
   java.lang.String password;
   /**
    * 密码
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public java.lang.String getPassword() {
      return password;
   }
   /**
    * 密码
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public void setPassword(java.lang.String password) throws wt.util.WTPropertyVetoException {
      passwordValidate(password);
      this.password = password;
   }
   void passwordValidate(java.lang.String password) throws wt.util.WTPropertyVetoException {
      if (password == null || password.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "password") },
               new java.beans.PropertyChangeEvent(this, "password", this.password, password));
      if (PASSWORD_UPPER_LIMIT < 1) {
         try { PASSWORD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("password").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PASSWORD_UPPER_LIMIT = 128; }
      }
      if (password != null && !wt.fc.PersistenceHelper.checkStoredLength(password.toString(), PASSWORD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "password"), java.lang.String.valueOf(java.lang.Math.min(PASSWORD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "password", this.password, password));
   }

   /**
    * 别名1
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public static final java.lang.String ALTERNATE_USER_NAME1 = "alternateUserName1";
   static int ALTERNATE_USER_NAME1_UPPER_LIMIT = -1;
   java.lang.String alternateUserName1;
   /**
    * 别名1
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public java.lang.String getAlternateUserName1() {
      return alternateUserName1;
   }
   /**
    * 别名1
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public void setAlternateUserName1(java.lang.String alternateUserName1) throws wt.util.WTPropertyVetoException {
      alternateUserName1Validate(alternateUserName1);
      this.alternateUserName1 = alternateUserName1;
   }
   void alternateUserName1Validate(java.lang.String alternateUserName1) throws wt.util.WTPropertyVetoException {
      if (ALTERNATE_USER_NAME1_UPPER_LIMIT < 1) {
         try { ALTERNATE_USER_NAME1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("alternateUserName1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ALTERNATE_USER_NAME1_UPPER_LIMIT = 128; }
      }
      if (alternateUserName1 != null && !wt.fc.PersistenceHelper.checkStoredLength(alternateUserName1.toString(), ALTERNATE_USER_NAME1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "alternateUserName1"), java.lang.String.valueOf(java.lang.Math.min(ALTERNATE_USER_NAME1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "alternateUserName1", this.alternateUserName1, alternateUserName1));
   }

   /**
    * 别名2
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public static final java.lang.String ALTERNATE_USER_NAME2 = "alternateUserName2";
   static int ALTERNATE_USER_NAME2_UPPER_LIMIT = -1;
   java.lang.String alternateUserName2;
   /**
    * 别名2
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public java.lang.String getAlternateUserName2() {
      return alternateUserName2;
   }
   /**
    * 别名2
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public void setAlternateUserName2(java.lang.String alternateUserName2) throws wt.util.WTPropertyVetoException {
      alternateUserName2Validate(alternateUserName2);
      this.alternateUserName2 = alternateUserName2;
   }
   void alternateUserName2Validate(java.lang.String alternateUserName2) throws wt.util.WTPropertyVetoException {
      if (ALTERNATE_USER_NAME2_UPPER_LIMIT < 1) {
         try { ALTERNATE_USER_NAME2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("alternateUserName2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ALTERNATE_USER_NAME2_UPPER_LIMIT = 128; }
      }
      if (alternateUserName2 != null && !wt.fc.PersistenceHelper.checkStoredLength(alternateUserName2.toString(), ALTERNATE_USER_NAME2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "alternateUserName2"), java.lang.String.valueOf(java.lang.Math.min(ALTERNATE_USER_NAME2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "alternateUserName2", this.alternateUserName2, alternateUserName2));
   }

   /**
    * 别名3
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public static final java.lang.String ALTERNATE_USER_NAME3 = "alternateUserName3";
   static int ALTERNATE_USER_NAME3_UPPER_LIMIT = -1;
   java.lang.String alternateUserName3;
   /**
    * 别名3
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public java.lang.String getAlternateUserName3() {
      return alternateUserName3;
   }
   /**
    * 别名3
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public void setAlternateUserName3(java.lang.String alternateUserName3) throws wt.util.WTPropertyVetoException {
      alternateUserName3Validate(alternateUserName3);
      this.alternateUserName3 = alternateUserName3;
   }
   void alternateUserName3Validate(java.lang.String alternateUserName3) throws wt.util.WTPropertyVetoException {
      if (ALTERNATE_USER_NAME3_UPPER_LIMIT < 1) {
         try { ALTERNATE_USER_NAME3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("alternateUserName3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ALTERNATE_USER_NAME3_UPPER_LIMIT = 128; }
      }
      if (alternateUserName3 != null && !wt.fc.PersistenceHelper.checkStoredLength(alternateUserName3.toString(), ALTERNATE_USER_NAME3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "alternateUserName3"), java.lang.String.valueOf(java.lang.Math.min(ALTERNATE_USER_NAME3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "alternateUserName3", this.alternateUserName3, alternateUserName3));
   }

   /**
    * 别名4
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public static final java.lang.String ALTERNATE_USER_NAME4 = "alternateUserName4";
   static int ALTERNATE_USER_NAME4_UPPER_LIMIT = -1;
   java.lang.String alternateUserName4;
   /**
    * 别名4
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public java.lang.String getAlternateUserName4() {
      return alternateUserName4;
   }
   /**
    * 别名4
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public void setAlternateUserName4(java.lang.String alternateUserName4) throws wt.util.WTPropertyVetoException {
      alternateUserName4Validate(alternateUserName4);
      this.alternateUserName4 = alternateUserName4;
   }
   void alternateUserName4Validate(java.lang.String alternateUserName4) throws wt.util.WTPropertyVetoException {
      if (ALTERNATE_USER_NAME4_UPPER_LIMIT < 1) {
         try { ALTERNATE_USER_NAME4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("alternateUserName4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ALTERNATE_USER_NAME4_UPPER_LIMIT = 128; }
      }
      if (alternateUserName4 != null && !wt.fc.PersistenceHelper.checkStoredLength(alternateUserName4.toString(), ALTERNATE_USER_NAME4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "alternateUserName4"), java.lang.String.valueOf(java.lang.Math.min(ALTERNATE_USER_NAME4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "alternateUserName4", this.alternateUserName4, alternateUserName4));
   }

   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public static final java.lang.String STATE = "state";
   static int STATE_UPPER_LIMIT = -1;
   java.lang.Integer state;
   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public java.lang.Integer getState() {
      return state;
   }
   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see ext.ziang.model.UserExtendInformation
    */
   public void setState(java.lang.Integer state) throws wt.util.WTPropertyVetoException {
      stateValidate(state);
      this.state = state;
   }
   void stateValidate(java.lang.Integer state) throws wt.util.WTPropertyVetoException {
      if (state != null && ((java.lang.Number) state).floatValue() > 2)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "state"), java.lang.String.valueOf(java.lang.Math.min(STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "state", this.state, state));
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

   public static final long EXTERNALIZATION_VERSION_UID = 2220197413769775524L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( alternateUserName1 );
      output.writeObject( alternateUserName2 );
      output.writeObject( alternateUserName3 );
      output.writeObject( alternateUserName4 );
      output.writeObject( password );
      output.writeObject( state );
      output.writeObject( username );
   }

   protected void super_writeExternal_UserExtendInformation(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ziang.model.UserExtendInformation) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_UserExtendInformation(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "alternateUserName1", alternateUserName1 );
      output.setString( "alternateUserName2", alternateUserName2 );
      output.setString( "alternateUserName3", alternateUserName3 );
      output.setString( "alternateUserName4", alternateUserName4 );
      output.setString( "password", password );
      output.setIntObject( "state", state );
      output.setString( "username", username );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      alternateUserName1 = input.getString( "alternateUserName1" );
      alternateUserName2 = input.getString( "alternateUserName2" );
      alternateUserName3 = input.getString( "alternateUserName3" );
      alternateUserName4 = input.getString( "alternateUserName4" );
      password = input.getString( "password" );
      state = input.getIntObject( "state" );
      username = input.getString( "username" );
   }

   boolean readVersion2220197413769775524L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      alternateUserName1 = (java.lang.String) input.readObject();
      alternateUserName2 = (java.lang.String) input.readObject();
      alternateUserName3 = (java.lang.String) input.readObject();
      alternateUserName4 = (java.lang.String) input.readObject();
      password = (java.lang.String) input.readObject();
      state = (java.lang.Integer) input.readObject();
      username = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( UserExtendInformation thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2220197413769775524L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_UserExtendInformation( _UserExtendInformation thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
