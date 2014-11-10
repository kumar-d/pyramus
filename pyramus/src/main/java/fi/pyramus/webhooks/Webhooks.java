package fi.pyramus.webhooks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;

import fi.pyramus.events.CourseArchivedEvent;
import fi.pyramus.events.CourseCreatedEvent;
import fi.pyramus.events.CourseStaffMemberCreatedEvent;
import fi.pyramus.events.CourseStaffMemberDeletedEvent;
import fi.pyramus.events.CourseStudentArchivedEvent;
import fi.pyramus.events.CourseStudentCreatedEvent;
import fi.pyramus.events.CourseUpdatedEvent;
import fi.pyramus.events.StaffMemberCreatedEvent;
import fi.pyramus.events.StaffMemberDeletedEvent;
import fi.pyramus.events.StaffMemberUpdatedEvent;
import fi.pyramus.events.StudentArchivedEvent;
import fi.pyramus.events.StudentCreatedEvent;
import fi.pyramus.events.StudentUpdatedEvent;

@ApplicationScoped 
@Stateful
public class Webhooks {

  @Inject
  private WebhookController webhookController;

  @Inject
  private WebhookSessionData sessionData;
  
  @PostConstruct
  public void init() {
    webhooks = new ArrayList<>();
  }
  
  public void addWebhook(String url, String secret) {
    String signature = DigestUtils.md5Hex(secret);
    webhooks.add(new fi.pyramus.webhooks.Webhook(url, signature));
  }
  
  /* Courses */
  
  public void onCourseCreated(@Observes(during=TransactionPhase.AFTER_SUCCESS) CourseCreatedEvent event) {
    webhookController.sendWebhookNotifications(webhooks, new WebhookCourseCreatePayload(event.getCourseId()));
  }
  
  public void onCourseUpdatedBeforeCompletion(@Observes(during=TransactionPhase.BEFORE_COMPLETION) CourseUpdatedEvent event) {
    sessionData.addUpdatedCourseId(event.getCourseId());
  }

  public void onCourseUpdatedAfterFailure(@Observes(during=TransactionPhase.AFTER_FAILURE) CourseUpdatedEvent event) {
    sessionData.clearUpdatedCourseIds();
  }

  public void onCourseUpdatedAfterSuccess(@Observes(during=TransactionPhase.AFTER_SUCCESS) CourseUpdatedEvent event) {
    List<Long> updatedCourseIds = sessionData.retrieveUpdatedCourseIds();
    
    for (Long updatedCourseId : updatedCourseIds) {
      webhookController.sendWebhookNotifications(webhooks, new WebhookCourseUpdatePayload(updatedCourseId));
    }
  }

  public void onCourseArchived(@Observes(during=TransactionPhase.AFTER_SUCCESS) CourseArchivedEvent event) {
    webhookController.sendWebhookNotifications(webhooks, new WebhookCourseArchivePayload(event.getCourseId()));
  }
  
  /* Student */
  
  public void onStudentCreated(@Observes(during=TransactionPhase.AFTER_SUCCESS) StudentCreatedEvent event) {
    webhookController.sendWebhookNotifications(webhooks, new WebhookStudentCreatePayload(event.getStudentId()));
  }
  
  public void onStudentUpdatedBeforeCompletion(@Observes(during=TransactionPhase.BEFORE_COMPLETION) StudentUpdatedEvent event) {
    sessionData.addUpdatedStudentId(event.getStudentId());
  }

  public void onStudentUpdatedAfterFailure(@Observes(during=TransactionPhase.AFTER_FAILURE) StudentUpdatedEvent event) {
    sessionData.clearUpdatedStudentIds();
  }

  public void onStudentUpdatedAfterSuccess(@Observes(during=TransactionPhase.AFTER_SUCCESS) StudentUpdatedEvent event) {
    List<Long> updatedStudentIds = sessionData.retrieveUpdatedStudentIds();
    
    for (Long updatedStudentId : updatedStudentIds) {
      webhookController.sendWebhookNotifications(webhooks, new WebhookStudentUpdatePayload(updatedStudentId));
    }
  }

  public void onStudentArchived(@Observes(during=TransactionPhase.AFTER_SUCCESS) StudentArchivedEvent event) {
    webhookController.sendWebhookNotifications(webhooks, new WebhookStudentArchivePayload(event.getStudentId()));
  }
  
  /* Course Staff Member */
  
  public void onCourseStaffMemberCreated(@Observes(during=TransactionPhase.AFTER_SUCCESS) CourseStaffMemberCreatedEvent event) {
    webhookController.sendWebhookNotifications(webhooks, new WebhookCourseStaffMemberCreatePayload(event.getCourseStaffMemberId(), event.getCourseId(), event.getStaffMemberId()));
  }
  
  public void onCourseStaffMemberDeleted(@Observes(during=TransactionPhase.AFTER_SUCCESS) CourseStaffMemberDeletedEvent event) {
    webhookController.sendWebhookNotifications(webhooks, new WebhookCourseStaffMemberDeletePayload(event.getCourseStaffMemberId(), event.getCourseId(), event.getStaffMemberId()));
  }
  
  /* Course Student */
  
  public void onCourseStudentCreated(@Observes(during=TransactionPhase.AFTER_SUCCESS) CourseStudentCreatedEvent event) {
    webhookController.sendWebhookNotifications(webhooks, new WebhookCourseStudentCreatePayload(event.getCourseStudentId(), event.getCourseId(), event.getStudentId()));
  }
  
  public void onCourseStudentArchived(@Observes(during=TransactionPhase.AFTER_SUCCESS) CourseStudentArchivedEvent event) {
    webhookController.sendWebhookNotifications(webhooks, new WebhookCourseStudentArchivePayload(event.getCourseStudentId(), event.getCourseId(), event.getStudentId()));
  }
  
  /* StaffMember */
  
  public void onStaffMemberCreated(@Observes(during=TransactionPhase.AFTER_SUCCESS) StaffMemberCreatedEvent event) {
    webhookController.sendWebhookNotifications(webhooks, new WebhookStaffMemberCreatePayload(event.getStaffMemberId()));
  }
  
  public void onStaffMemberUpdatedBeforeCompletion(@Observes(during=TransactionPhase.BEFORE_COMPLETION) StaffMemberUpdatedEvent event) {
    sessionData.addUpdatedStaffMemberId(event.getStaffMemberId());
  }

  public void onStaffMemberUpdatedAfterFailure(@Observes(during=TransactionPhase.AFTER_FAILURE) StaffMemberUpdatedEvent event) {
    sessionData.clearUpdatedStaffMemberIds();
  }

  public void onStaffMemberUpdatedAfterSuccess(@Observes(during=TransactionPhase.AFTER_SUCCESS) StaffMemberUpdatedEvent event) {
    List<Long> updatedStaffMemberIds = sessionData.retrieveUpdatedStaffMemberIds();
    
    for (Long updatedStaffMemberId : updatedStaffMemberIds) {
      webhookController.sendWebhookNotifications(webhooks, new WebhookStaffMemberUpdatePayload(updatedStaffMemberId));
    }
  }

  public void onStaffMemberDeleted(@Observes(during=TransactionPhase.AFTER_SUCCESS) StaffMemberDeletedEvent event) {
    webhookController.sendWebhookNotifications(webhooks, new WebhookStaffMemberDeletePayload(event.getStaffMemberId()));
  }
 
  private List<fi.pyramus.webhooks.Webhook> webhooks;

}
