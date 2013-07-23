package fi.pyramus.rest.tranquil.base;

import fi.tranquil.TranquilModel;
import fi.tranquil.TranquilModelType;

@TranquilModel (entityClass = fi.pyramus.domainmodel.base.Subject.class, entityType = TranquilModelType.UPDATE)
public class SubjectUpdate extends SubjectComplete {

  public void setEducationType(EducationTypeCompact educationType) {
    super.setEducationType(educationType);
  }

  public EducationTypeCompact getEducationType() {
    return (EducationTypeCompact)super.getEducationType();
  }

  public final static String[] properties = {"educationType"};
}
