package fi.pyramus.rest.tranquil.base;

import fi.tranquil.TranquilModel;
import fi.tranquil.TranquilModelType;

@TranquilModel (entityClass = fi.pyramus.domainmodel.base.Nationality.class, entityType = TranquilModelType.UPDATE)
public class NationalityUpdate extends NationalityComplete {

  public final static String[] properties = {};
}
