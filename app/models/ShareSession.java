package models;

import forms.myspace.ShareSessionForm;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import play.data.validation.Validation;
import play.db.jpa.Model;
import play.i18n.Messages;
import utils.statemachine.ShareSessionContext;
import utils.statemachine.ShareSessionStateMachine;
import utils.statemachine.ShareSessionStateMachine.ShareSessionStatus;
import utils.statemachine.ShareSessionTransition;

@Entity
@Table(name = "share_sessions")
public class ShareSession extends Model implements ShareSessionContext {

    @ManyToOne
    @JoinColumn(name = "department_id")
    public Department department;
    @ManyToMany
    @JoinTable(name = "share_session_contributor",
    joinColumns =
    @JoinColumn(name = "share_session_id", table = "share_sessions", referencedColumnName = "id"),
    inverseJoinColumns =
    @JoinColumn(name = "contributor_id", table = "users", referencedColumnName = "id"))
    public List<User> contributors;
    public String subject;
    public String audiences;
    public Date start;
    public Date end;
    public String address;
    @Column(length = 2048)
    public String description;
    public int audiences_limit;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    public User creator;
    @Enumerated(value = EnumType.STRING)
    @Column(length = 16)
    public ShareSessionStatus status;
    @OneToMany(mappedBy = "share_session")
    public List<Attachment> attachments;

    public void setStatus(String string) {

        status = ShareSessionStatus.valueOf(string);
    }

    public static ShareSession create(ShareSessionForm userForm, User creator) {
        return create(userForm, creator, null);
    }

    public static ShareSession create(ShareSessionForm share_session_form, User creator, Validation validation) {
        ShareSession share_session = build(share_session_form, creator);

        if (validation != null) {
            share_session.validate(validation);
            if (validation.hasErrors()) {
                return null;
            }
        }

        share_session.save();
        return share_session;
    }

    public static ShareSession update(ShareSessionForm share_session_form, User creator, Validation validation) {

        ShareSession share_session = ShareSession.findById(share_session_form.id);

        if (!share_session.creator.equals(creator)) {
            return null;
        }

        share_session = build(share_session, share_session_form, creator);

        if (validation != null) {
            share_session.validate(validation);
            if (validation.hasErrors()) {
                return share_session;
            }
        }

        share_session.save();

        return share_session;
    }

    public static ShareSession update(ShareSessionForm share_session_form, Validation validation) {

        ShareSession share_session = ShareSession.findById(share_session_form.id);


        share_session = build(share_session, share_session_form);

        if (validation != null) {
            share_session.validate(validation);
            if (validation.hasErrors()) {
                return share_session;
            }
        }

        share_session.save();

        return share_session;
    }

    public static List<ShareSession> findSessionsOnComing() {
        return ShareSession.findBy("status = ? order by start", ShareSessionStatus.PUBLISHED);
    }

    public static List<ShareSession> findMyShareSessions(User creator) {
        return ShareSession.findBy("status != ? and creator = ? order by start", ShareSessionStatus.DELETED, creator);
    }

    public static List<ShareSession> findActiveShareSessions() {
        return ShareSession.findBy("status != ? order by start", ShareSessionStatus.DELETED);
    }

    public void setCurrentStatus(ShareSessionStatus currentStatus) {
        status = currentStatus;
    }

    public ShareSessionStatus getCurrentStatus() {
        return status;
    }



    private static ShareSession build(ShareSessionForm share_session_form, User creator) {

        return build(new ShareSession(), share_session_form, creator);
    }

    private static ShareSession build(ShareSession share_session, ShareSessionForm share_session_form) {

        share_session.department = Department.findById(share_session_form.department_id);
        share_session.subject = share_session_form.subject;
        share_session.audiences = share_session_form.audiences;
        share_session.start = share_session_form.start_date;
        share_session.end = share_session_form.end_date;
        share_session.address = share_session_form.address;

        share_session.audiences_limit = share_session_form.audiences_limit;
        share_session.description = share_session_form.description;

        if (share_session.status == null) {
            ShareSessionStateMachine.transit(share_session, ShareSessionTransition.INIT);
        }

        User contributor_1 = User.findById(share_session_form.contributor_1_id);

        User contributor_2 = null;
        if (share_session_form.contributor_2_id != null && share_session_form.contributor_2_id > 0) {
            contributor_2 = User.findById(share_session_form.contributor_2_id);
        }


        User contributor_3 = null;
        if (share_session_form.contributor_3_id != null && share_session_form.contributor_3_id > 0) {
            contributor_3 = User.findById(share_session_form.contributor_3_id);
        }


        share_session.contributors = Arrays.asList(contributor_1, contributor_2, contributor_3);

        return share_session;
    }

    private static ShareSession build(ShareSession share_session, ShareSessionForm share_session_form, User creator) {

        share_session.creator = creator;

        return build(share_session, share_session_form);
    }

    private void validate(Validation validation) {
        validate_contributors_exist(validation);
    }

    private void validate_contributors_exist(Validation validation) {

        for (User user : contributors) {

            if (user != null) {

                return;
            }
        }
        validation.addError(ShareSessionForm.CONTRIBUTOR, Messages.get("validation.contributor.not.exists"));
    }

    public String getHumanReadableStartTime() {
        return renderHumanReadableTime(start);
    }

    public String getHumanReadableEndTime() {
        return renderHumanReadableTime(end);
    }

    private String renderHumanReadableTime(Date time) {

        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd E HH:mm");
        return format.format(time);
    }
}
