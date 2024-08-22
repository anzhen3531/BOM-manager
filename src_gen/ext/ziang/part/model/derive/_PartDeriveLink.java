package ext.ziang.part.model.derive;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _PartDeriveLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final String RESOURCE = "ext.ziang.part.model.derive.deriveResource";
   static final String CLASSNAME = PartDeriveLink.class.getName();

   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see PartDeriveLink
    */
   public static final String STATE = "state";
   static int STATE_UPPER_LIMIT = -1;
   String state;
   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see PartDeriveLink
    */
   public String getState() {
      return state;
   }
   /**
    * 状态
    * <p>
    * <b>Supported API: </b>true
    *
    * @see PartDeriveLink
    */
   public void setState(String state) throws wt.util.WTPropertyVetoException {
      stateValidate(state);
      this.state = state;
   }
   void stateValidate(String state) throws wt.util.WTPropertyVetoException {
      if (state == null || state.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "state") },
               new java.beans.PropertyChangeEvent(this, "state", this.state, state));
      if (STATE_UPPER_LIMIT < 1) {
         try { STATE_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("state").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STATE_UPPER_LIMIT = 64; }
      }
      if (state != null && !wt.fc.PersistenceHelper.checkStoredLength(state.toString(), STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "state"), String.valueOf(Math.min(STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "state", this.state, state));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see PartDeriveLink
    */
   public static final String DERIVE_FOR_ROLE = "deriveFor";
   /**
    * <b>Supported API: </b>true
    *
    * @see PartDeriveLink
    */
   public wt.part.WTPartMaster getDeriveFor() {
      return (wt.part.WTPartMaster) getRoleAObject();
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see PartDeriveLink
    */
   public void setDeriveFor(wt.part.WTPartMaster the_deriveFor) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_deriveFor);
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see PartDeriveLink
    */
   public static final String DERIVES_ROLE = "derives";
   /**
    * <b>Supported API: </b>true
    *
    * @see PartDeriveLink
    */
   public wt.part.WTPartMaster getDerives() {
      return (wt.part.WTPartMaster) getRoleBObject();
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see PartDeriveLink
    */
   public void setDerives(wt.part.WTPartMaster the_derives) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_derives);
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

   public static final long EXTERNALIZATION_VERSION_UID = 4274708356684973157L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( state );
   }

   protected void super_writeExternal_PartDeriveLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (PartDeriveLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_PartDeriveLink(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "state", state );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      state = input.getString( "state" );
   }

   boolean readVersion4274708356684973157L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      state = (String) input.readObject();
      return true;
   }

   protected boolean readVersion( PartDeriveLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4274708356684973157L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_PartDeriveLink( _PartDeriveLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
