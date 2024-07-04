package ext.ziang.model;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _PanzerPart extends wt.part.WTPart implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final String RESOURCE = "ext.ziang.model.modelResource";
   static final String CLASSNAME = PanzerPart.class.getName();

   /**
    * Part description
    *
    * @see PanzerPart
    */
   public static final String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   String description;
   /**
    * Part description
    *
    * @see PanzerPart
    */
   public String getDescription() {
      return description;
   }
   /**
    * Part description
    *
    * @see PanzerPart
    */
   public void setDescription(String description) throws wt.util.WTPropertyVetoException {
      descriptionValidate(description);
      this.description = description;
   }
   void descriptionValidate(String description) throws wt.util.WTPropertyVetoException {
      if (DESCRIPTION_UPPER_LIMIT < 1) {
         try { DESCRIPTION_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("description").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESCRIPTION_UPPER_LIMIT = 1024; }
      }
      if (description != null && !wt.fc.PersistenceHelper.checkStoredLength(description.toString(), DESCRIPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description"), String.valueOf(Math.min(DESCRIPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
   }

   /**
    * IPD Status
    *
    * @see PanzerPart
    */
   public static final String IPD_STATUS = "ipdStatus";
   static int IPD_STATUS_UPPER_LIMIT = -1;
   String ipdStatus;
   /**
    * IPD Status
    *
    * @see PanzerPart
    */
   public String getIpdStatus() {
      return ipdStatus;
   }
   /**
    * IPD Status
    *
    * @see PanzerPart
    */
   public void setIpdStatus(String ipdStatus) throws wt.util.WTPropertyVetoException {
      ipdStatusValidate(ipdStatus);
      this.ipdStatus = ipdStatus;
   }
   void ipdStatusValidate(String ipdStatus) throws wt.util.WTPropertyVetoException {
      if (IPD_STATUS_UPPER_LIMIT < 1) {
         try { IPD_STATUS_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ipdStatus").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IPD_STATUS_UPPER_LIMIT = 64; }
      }
      if (ipdStatus != null && !wt.fc.PersistenceHelper.checkStoredLength(ipdStatus.toString(), IPD_STATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ipdStatus"), String.valueOf(Math.min(IPD_STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ipdStatus", this.ipdStatus, ipdStatus));
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

   public static final long EXTERNALIZATION_VERSION_UID = -9180194071473417014L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( description );
      output.writeObject( ipdStatus );
   }

   protected void super_writeExternal_PanzerPart(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (PanzerPart) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_PanzerPart(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "description", description );
      output.setString( "ipdStatus", ipdStatus );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      description = input.getString( "description" );
      ipdStatus = input.getString( "ipdStatus" );
   }

   boolean readVersion_9180194071473417014L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      description = (String) input.readObject();
      ipdStatus = (String) input.readObject();
      return true;
   }

   protected boolean readVersion( PanzerPart thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_9180194071473417014L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_PanzerPart( _PanzerPart thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
