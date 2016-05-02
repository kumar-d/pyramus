package fi.otavanopisto.pyramus.views.students;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import fi.internetix.smvc.controllers.PageRequestContext;
import fi.otavanopisto.pyramus.I18N.Messages;
import fi.otavanopisto.pyramus.breadcrumbs.Breadcrumbable;
import fi.otavanopisto.pyramus.dao.DAOFactory;
import fi.otavanopisto.pyramus.dao.users.StaffMemberDAO;
import fi.otavanopisto.pyramus.domainmodel.users.StaffMember;
import fi.otavanopisto.pyramus.domainmodel.users.User;
import fi.otavanopisto.pyramus.framework.PyramusViewController;
import fi.otavanopisto.pyramus.framework.UserRole;

public class SearchStudentGroupsViewController extends PyramusViewController implements Breadcrumbable {

  public void process(PageRequestContext pageRequestContext) {
    StaffMemberDAO userDAO = DAOFactory.getInstance().getStaffMemberDAO();
    
    List<StaffMember> users = userDAO.listAll();
    
    Collections.sort(users, new Comparator<User>() {
      @Override
      public int compare(User o1, User o2) {
        int cmp = o1.getLastName().compareToIgnoreCase(o2.getLastName());
        if (cmp == 0)
          cmp = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
        return cmp;
      }
    });
    
    pageRequestContext.getRequest().setAttribute("users", users);
    
    pageRequestContext.setIncludeJSP("/templates/students/searchstudentgroups.jsp");
  }

  public UserRole[] getAllowedRoles() {
    return new UserRole[] { UserRole.MANAGER, UserRole.STUDY_PROGRAMME_LEADER, UserRole.ADMINISTRATOR };
  }

  /**
   * Returns the localized name of this page. Used e.g. for breadcrumb navigation.
   * 
   * @param locale The locale to be used for the name
   * 
   * @return The localized name of this page
   */
  public String getName(Locale locale) {
    return Messages.getInstance().getText(locale, "students.searchStudentGroups.pageTitle");
  }
  
}
