package fi.pyramus.rest.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.pyramus.dao.base.TagDAO;
import fi.pyramus.dao.courses.CourseDAO;
import fi.pyramus.dao.modules.ModuleDAO;
import fi.pyramus.dao.projects.ProjectDAO;
import fi.pyramus.domainmodel.base.Tag;
import fi.pyramus.domainmodel.courses.Course;
import fi.pyramus.domainmodel.modules.Module;
import fi.pyramus.domainmodel.projects.Project;
import fi.pyramus.persistence.search.SearchResult;

@Dependent
@Stateless
public class TagController {
  @Inject
  private TagDAO tagDAO;
  @Inject
  private ProjectDAO projectDAO;
  @Inject
  private ModuleDAO moduleDAO;
  @Inject
  private CourseDAO courseDAO;

  public Tag createTag(String text) {
    Tag tag = tagDAO.findByText(text);
    if (tag == null) {
      tag = tagDAO.create(text);
    }
    return tag;
  }

  public List<Tag> findTags() {
    List<Tag> tags = tagDAO.listAll();
    return tags;
  }

  public Tag findTagById(Long id) {
    Tag tag = tagDAO.findById(id);
    return tag;
  }
  
  public Tag findTagByText(String text) {
    Tag tag = tagDAO.findByText(text);
    return tag;
  }
  
  public SearchResult<Course> findCoursesByTag(int resultsPerPage, int page, String tags, boolean filterArchived) {
    SearchResult<Course> courses = courseDAO.searchCourses(resultsPerPage, page, null, tags, null, null, null, null, null, null, null, filterArchived);
    return courses;
  }
  
  public SearchResult<Project> findProjectsByTag(int resultsPerPage, int page, String tag, boolean filterArchived) {
    SearchResult<Project> projects = projectDAO.searchProjects(resultsPerPage, page, null, null, tag, filterArchived);
    return projects;
  }
  
  public SearchResult<Module> findModulesByTag(int resultsPerPage, int page, String tags, boolean filterArchived) {
    SearchResult<Module> modules = moduleDAO.searchModules(resultsPerPage, page, null, null, tags, null, null, null, null, filterArchived);
    return modules;
  }
  
  public Tag updateTagText(Tag tag, String text) {
    tagDAO.updateText(tag, text);
    return tag;
  }
  
  public void deleteTag(Tag tag) {
    tagDAO.delete(tag);
  }
}
