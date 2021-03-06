package fi.otavanopisto.pyramus.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fi.otavanopisto.pyramus.domainmodel.base.Email;
import fi.otavanopisto.pyramus.domainmodel.users.User;
import fi.otavanopisto.pyramus.rest.annotation.RESTPermit;
import fi.otavanopisto.pyramus.rest.annotation.Unsecure;
import fi.otavanopisto.pyramus.rest.controller.permissions.SystemPermissions;
import fi.otavanopisto.pyramus.rest.model.WhoAmI;
import fi.otavanopisto.pyramus.security.impl.SessionController;

@Path("/system")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateful
@RequestScoped
public class SystemRESTService extends AbstractRESTService {

  @Inject
  private SessionController sessionController;
  
  @GET
  @Unsecure
  @Path ("/ping")
  @Produces (MediaType.TEXT_PLAIN)
  public Response getPing() {
    return Response.ok("pong").build();
  }
  
  @GET
  @Path ("/whoami")
  @RESTPermit (SystemPermissions.WHOAMI)
  public Response getWhoAmI() {
    User loggedUser = sessionController.getUser();
    if (loggedUser == null) {
      return Response.status(Status.FORBIDDEN).build();
    }
    
    List<String> emails = new ArrayList<>();
    for (Email email : loggedUser.getContactInfo().getEmails()) {
      if (Boolean.TRUE.equals(email.getContactType().getNonUnique())) {
        continue;
      }
      emails.add(email.getAddress());
    }
    
    return Response.ok(new WhoAmI(loggedUser.getId(), loggedUser.getFirstName(), loggedUser.getLastName(), emails)).build();
  }

}
