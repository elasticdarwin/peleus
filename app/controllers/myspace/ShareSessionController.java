package controllers.myspace;

import controllers.Application;
import forms.myspace.ShareSessionForm;
import java.util.Arrays;
import java.util.List;
import models.Department;
import models.ShareSession;
import models.ShareSession.ShareSessionStatus;
import models.User;
import play.Logger;
import play.data.validation.Valid;

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

    public static void delete_confirm(Long id) {
    }

    public static void publish(Long id) {
        User user = fetch_user_or_redirect_to_login();
        redirectToLoginIfNull(user);

        ShareSession share_session = ShareSession.findById(id);
        forbiddenIfNull(share_session);


        if (share_session.creator != user) {
            forbidden("You don't have permissions to access this page!");
        }


        if (share_session.status == ShareSessionStatus.CREATED || share_session.status == ShareSessionStatus.CLOSED) {
            share_session.status = ShareSessionStatus.PUBLISHED;
            share_session.save();
        }

        redirectBackIfHasHTTPReferer();

        show(id);
    }

    public static void close(Long id) {
        // TODO access control

        ShareSession share_session = ShareSession.findById(id);
        redirectToLoginIfNull(share_session);

        if (share_session.status == ShareSessionStatus.PUBLISHED) {
            share_session.status = ShareSessionStatus.CLOSED;
            share_session.save();
        }


        redirectBackIfHasHTTPReferer();
        show(id);
    }

    public static void show(Long id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void edit(Long id) {

        ShareSession share_session = ShareSession.findById(id);

        redirectToLoginIfNull(share_session);

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

        List<String> keys = Arrays.asList("sharesession_form.user_1_id", "sharesession_form.user_2_id", "sharesession_form.user_3_id");

        for (int index = 0; index < contributors.size(); index++) {
            flash.put(keys.get(index), contributors.get(index).id);
        }

        for (int index = contributors.size(); index < 3 ; index++) {
            flash.put(keys.get(index), -1L);
        }




        List<Department> departments = Department.findAll();
        List<User> users = User.findAll();


        render(departments, users);
    }

    public static void update(@Valid ShareSessionForm sharesession_form) {

        ShareSession share_session = ShareSession.update(sharesession_form, fetch_user_or_redirect_to_login());

        Logger.info("start is ################################### " + sharesession_form.start_date);

        //
//        share_session.save();

        index();





//        render();

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
