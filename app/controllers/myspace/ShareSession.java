package controllers.myspace;

import controllers.Application;
import forms.myspace.ShareSessionForm;
import java.util.List;
import models.Department;
import models.User;
import play.data.validation.Valid;

public class ShareSession extends Application {

    public static void create_form() {

        List<Department> departments = Department.findAll();

        List<User> users = User.findAll();

        render(departments, users);
    }

    public static void create(@Valid ShareSessionForm share_session_form) {


        create_form();
    }
}
