package fi.pyramus.rest.controller.permissions;

import java.util.List;

import fi.otavanopisto.security.Scope;
import fi.pyramus.security.impl.AbstractPyramusPermissionCollection;
import fi.pyramus.security.impl.DefaultPermissionRoles;
import fi.pyramus.security.impl.PermissionScope;
import fi.pyramus.security.impl.PyramusPermissionCollection;

public class SystemPermissions extends AbstractPyramusPermissionCollection implements PyramusPermissionCollection {
  
  @Scope (PermissionScope.ENVIRONMENT)
  @DefaultPermissionRoles ({ ADMINISTRATOR, MANAGER, USER, GUEST, STUDENT })
  public static final String WHOAMI = "WHOAMI";
  
  @Override
  public List<String> listPermissions() {
    return super.listPermissions(SystemPermissions.class);
  }

  @Override
  public boolean containsPermission(String permission) {
    return listPermissions().contains(permission);
  }

  @Override
  public String getPermissionScope(String permission) throws NoSuchFieldException {
    return super.getPermissionScope(SystemPermissions.class, permission);
  }

  @Override
  public String[] getDefaultRoles(String permission) throws NoSuchFieldException {
    return super.getDefaultRoles(SystemPermissions.class, permission);
  }

}
