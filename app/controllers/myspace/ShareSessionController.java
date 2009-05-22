package controllers.myspace;

import controllers.Application;
import forms.myspace.ShareSessionForm;
import java.util.List;
import models.Department;
import models.ShareSession;
import models.ShareSession.ShareSessionStatus;
import models.User;
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

        throw new UnsupportedOperationException("Not yet implemented");

    }

    public static void update(@Valid ShareSessionForm sharesession_form) {

//        Department department = Department.findById(department_form.id);
//
//        department.name = department_form.name;
//        department.description = department_form.description;
//
//
//        if (department.validate_name(validation)) {
//            params.flash();
//            validation.keep();
//            edit(department_form.id);
//        }
//
//        department.save();

//        render();

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
