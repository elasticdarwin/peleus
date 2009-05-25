package models;

import forms.myspace.ShareSessionForm;
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
import javax.persistence.Table;
import play.data.validation.Validation;
import play.db.jpa.JPAModel;
import utils.statemachine.ShareSessionContext;
import utils.statemachine.ShareSessionStateMachine.ShareSessionStatus;

@Entity
@Table(name = "share_sessions")
public class ShareSession extends JPAModel implements ShareSessionContext {

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

    public void setStatus(String string) {
        status = ShareSessionStatus.valueOf(string);
    }

    public static ShareSession update(ShareSessionForm share_session_form, User creator) {

        ShareSession share_session = ShareSession.findById(share_session_form.id);

        share_session = build(share_session, share_session_form, creator);

        share_session.save();

        return share_session;
    }

    public static ShareSession build(ShareSessionForm share_session_form, User creator) {

        return build(new ShareSession(), share_session_form, creator);
    }

    private static ShareSession build(ShareSession share_session, ShareSessionForm share_session_form, User creator) {


        share_session.creator = creator;


        share_session.department = Department.findById(share_session_form.department_id);
        share_session.subject = share_session_form.subject;
        share_session.audiences = share_session_form.audiences;
        share_session.start = share_session_form.start_date;
        share_session.end = share_session_form.end_date;
        share_session.address = share_session_form.address;

        share_session.audiences_limit = share_session_form.audiences_limit;
        share_session.description = share_session_form.description;
        share_session.status = ShareSessionStatus.CREATED;


        User user_1 = User.findById(share_session_form.user_1_id);

        User user_2 = null;
        if (share_session_form.user_2_id != null && share_session_form.user_2_id > 0) {
            user_2 = User.findById(share_session_form.user_2_id);
        }


        User user_3 = null;
        if (share_session_form.user_3_id != null && share_session_form.user_3_id > 0) {
            user_3 = User.findById(share_session_form.user_3_id);
        }
        share_session.contributors = Arrays.asList(user_1, user_2, user_3);

        return share_session;
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

    private void validate(Validation validation) {
        // Noops
    }

    public static int deleteAllWithDenpendencies() {
        List<ShareSession> share_sessions = ShareSession.findAll();
        for (ShareSession share_session : share_sessions) {
            share_session.delete();
        }
        return share_sessions.size();
    }

    public static List<ShareSession> findSessionsOnComing() {
        return ShareSession.findBy("status = ? order by start", ShareSessionStatus.PUBLISHED);
    }

    public static List<ShareSession> findMyShareSessions(User creator) {
        return ShareSession.findBy("status != ? and creator = ? order by start", ShareSessionStatus.DELETED, creator);
    }

    public void setCurrentStatus(ShareSessionStatus currentStatus) {
        status = currentStatus;
    }

    public ShareSessionStatus getCurrentStatus() {
        return status;
    }
}
