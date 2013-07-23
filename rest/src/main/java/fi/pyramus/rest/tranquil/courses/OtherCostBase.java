package fi.pyramus.rest.tranquil.courses;

import fi.tranquil.TranquilModel;
import fi.tranquil.TranquilModelType;

@TranquilModel (entityClass = fi.pyramus.domainmodel.courses.OtherCost.class, entityType = TranquilModelType.BASE)
public class OtherCostBase implements fi.tranquil.TranquilModelEntity {

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public fi.pyramus.persistence.usertypes.MonetaryAmount getCost() {
    return cost;
  }

  public void setCost(fi.pyramus.persistence.usertypes.MonetaryAmount cost) {
    this.cost = cost;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  private Long id;

  private String name;

  private fi.pyramus.persistence.usertypes.MonetaryAmount cost;

  private Long version;

  public final static String[] properties = {"id","name","cost","version"};
}
