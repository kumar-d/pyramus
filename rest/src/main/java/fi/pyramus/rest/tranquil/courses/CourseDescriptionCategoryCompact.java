package fi.pyramus.rest.tranquil.courses;

import fi.tranquil.TranquilModel;
import fi.tranquil.TranquilModelType;

@TranquilModel  (entityClass = fi.pyramus.domainmodel.courses.CourseDescriptionCategory.class, entityType = TranquilModelType.COMPACT)
public class CourseDescriptionCategoryCompact extends CourseDescriptionCategoryBase {

  public final static String[] properties = {};
}
