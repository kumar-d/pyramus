package fi.pyramus.json.users;

import fi.internetix.smvc.controllers.JSONRequestContext;
import fi.pyramus.dao.DAOFactory;
import fi.pyramus.dao.base.PersonDAO;
import fi.pyramus.dao.users.UserDAO;
import fi.pyramus.domainmodel.base.Person;
import fi.pyramus.domainmodel.users.User;
import fi.pyramus.framework.JSONRequestController;
import fi.pyramus.framework.UserRole;

public class SetDefaultUserJSONRequestController extends JSONRequestController {

  public void process(JSONRequestContext requestContext) {
    PersonDAO personDAO = DAOFactory.getInstance().getPersonDAO();
    UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

    Person person = personDAO.findById(requestContext.getLong("personId"));
    User user = userDAO.findById(requestContext.getLong("userId"));
    
    if (user.getPerson().getId().equals(person.getId()))
      personDAO.updateDefaultUser(person, user);
    
    requestContext.setRedirectURL(requestContext.getReferer(true));
  }

  public UserRole[] getAllowedRoles() {
    return new UserRole[] { UserRole.MANAGER, UserRole.ADMINISTRATOR };
  }

}
