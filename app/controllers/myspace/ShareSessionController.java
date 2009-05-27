package controllers.myspace;

import controllers.Application;
import forms.myspace.ShareSessionForm;
import java.util.Arrays;
import java.util.List;
import models.Department;
import models.ShareSession;

import models.User;
import play.data.validation.Valid;
import utils.statemachine.ShareSessionStateMachine;
import utils.statemachine.ShareSessionTransition;
import utils.statemachine.StateMachineException;

public class ShareSessionController extends Application {

    public static void create_form() {

        List<Department> departments = Department.findAll();

        List<User> users = User.findAll();

        render(departments, users);
    }

    public static void create(@Valid ShareSessionForm sharesession_form) {

        User creator = fetch_user_or_redirect_to_login();

        ShareSession share_session = ShareSession.create(sharesession_form, creator, validation);

        if (share_session == null) {
            params.flash();
            validation.keep();
            create_form();
        }

        index();
    }

    public static void index() {

        User user = fetch_user_or_redirect_to_login();
        List<ShareSession> my_share_sessions = ShareSession.findMyShareSessions(user);

        render(user, my_share_sessions);
    }

    public static void show(Long id) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void publish(Long id) throws StateMachineException {
        //TODO : refactor with Filter
        User user = fetch_user_or_redirect_to_login();
        redirectToLoginIfNo(user);

        ShareSession share_session = ShareSession.findById(id);
        forbiddenIfNo(share_session);

        //TODO : consider to handle @ more higher level
        if (share_session.creator != user) {
            forbidden("You don't have permissions to access this page!");
        }

        ShareSessionStateMachine.transit(share_session, ShareSessionTransition.PUBLISH);

        share_session.save();

        redirectBackIfHasHTTPReferer();

        show(id);
    }

    public static void close(Long id) throws StateMachineException {

        ShareSession share_session = ShareSession.findById(id);
        redirectToLoginIfNo(share_session);

        ShareSessionStateMachine.transit(share_session, ShareSessionTransition.CLOSE);

        share_session.save();

        redirectBackIfHasHTTPReferer();
        show(id);
    }

    public static void delete_confirm(Long id) {
    }

    public static void edit(Long id) {

        ShareSession share_session = ShareSession.findById(id);

        redirectToLoginIfNo(share_session);

        fetch_for_edit(share_session);

        List<Department> departments = Department.findAll();
        List<User> users = User.findAll();

        render(departments, users);
    }

    public static void update(@Valid ShareSessionForm sharesession_form) {

        ShareSession share_session = ShareSession.update(sharesession_form, fetch_user_or_redirect_to_login());

        forbiddenIfNo(share_session);

        //show(share_session.id);
        index();
    }

    private static void fetch_for_edit(ShareSession share_session) {
        flash.put("sharesession_form.subject", share_session.subject);
        flash.put("sharesession_form.description", share_session.description);
        flash.put("sharesession_form.audiences", share_session.audiences);
        flash.put("sharesession_form.audiences_limit", share_session.audiences_limit);
        flash.put("sharesession_form.start_date", share_session.start);
        flash.put("sharesession_form.end_date", share_session.end);
        flash.put("sharesession_form.address", share_session.address);
        flash.put("sharesession_form.id", share_session.id);
        flash.put("sharesession_form.department_id", share_session.department.id);
        List<User> contributors = share_session.contributors;
        List<String> keys = Arrays.asList("sharesession_form.contributor_1_id", "sharesession_form.contributor_2_id", "sharesession_form.contributor_3_id");
        for (int index = 0; index < contributors.size(); index++) {
            flash.put(keys.get(index), contributors.get(index).id);
        }
        for (int index = contributors.size(); index < 3; index++) {
            flash.put(keys.get(index), -1L);
        }
    }
}
