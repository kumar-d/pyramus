package fi.otavanopisto.pyramus.views.settings;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import fi.internetix.smvc.controllers.PageRequestContext;
import fi.otavanopisto.pyramus.I18N.Messages;
import fi.otavanopisto.pyramus.breadcrumbs.Breadcrumbable;
import fi.otavanopisto.pyramus.dao.DAOFactory;
import fi.otavanopisto.pyramus.dao.base.EducationTypeDAO;
import fi.otavanopisto.pyramus.dao.base.SubjectDAO;
import fi.otavanopisto.pyramus.domainmodel.base.EducationType;
import fi.otavanopisto.pyramus.domainmodel.base.Subject;
import fi.otavanopisto.pyramus.framework.PyramusViewController;
import fi.otavanopisto.pyramus.framework.UserRole;
import fi.otavanopisto.pyramus.util.JSONArrayExtractor;
import fi.otavanopisto.pyramus.util.StringAttributeComparator;

/**
 * The controller responsible of the Manage Subjects view of the application.
 * 
 * @see fi.otavanopisto.pyramus.json.settings.SaveSubjectsJSONRequestController
 */
public class SubjectsViewController extends PyramusViewController implements Breadcrumbable {

  /**
   * Processes the page request by including the corresponding JSP page to the response.
   * 
   * @param pageRequestContext Page request context
   */
  public void process(PageRequestContext pageRequestContext) {
    SubjectDAO subjectDAO = DAOFactory.getInstance().getSubjectDAO();
    EducationTypeDAO educationTypeDAO = DAOFactory.getInstance().getEducationTypeDAO();    

    List<Subject> subjects = subjectDAO.listUnarchived();
    Collections.sort(subjects, new StringAttributeComparator("getName"));

    List<EducationType> educationTypes = educationTypeDAO.listUnarchived();
    Collections.sort(educationTypes, new StringAttributeComparator("getName"));
    
    JSONArray jsonSubjects = new JSONArrayExtractor("name", "code", "id").extract(subjects);
    for (int i=0; i<jsonSubjects.size(); i++) {
      JSONObject jsonStudyProgrammeCategory = jsonSubjects.getJSONObject(i);
      if (subjects.get(i).getEducationType() != null)
        jsonStudyProgrammeCategory.put("educationTypeId", subjects.get(i).getEducationType().getId());
    }
    String jsonEducationTypes = new JSONArrayExtractor("name", "id").extractString(educationTypes);

    this.setJsDataVariable(pageRequestContext, "subjects", jsonSubjects.toString());
    this.setJsDataVariable(pageRequestContext, "educationTypes", jsonEducationTypes);
    pageRequestContext.setIncludeJSP("/templates/settings/subjects.jsp");
  }

  /**
   * Returns the roles allowed to access this page.
   * 
   * @return The roles allowed to access this page
   */
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
    return Messages.getInstance().getText(locale, "settings.subjects.pageTitle");
  }

}
