package controllers.myspace;

import controllers.Application;
import controllers.dashboard.Dashboard;
import forms.myspace.ShareSessionForm;
import java.util.List;
import models.Department;
import models.ShareSession;
import models.User;
import play.data.validation.Valid;

public class ShareSessionController extends Application {

    public static void create_form() {

        List<Department> departments = Department.findAll();

        List<User> users = User.findAll();

        render(departments, users);
    }

    public static void create(@Valid ShareSessionForm sharesession_form) {
        ShareSession share_session = ShareSession.create(sharesession_form, validation);

        if (share_session == null) {
            params.flash();
            validation.keep();
            create_form();
        }

        index();
    }

    public static void index() {
        Dashboard.index();
    }
}
