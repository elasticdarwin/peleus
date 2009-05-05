package controllers.backyard;

import controllers.Application;
import forms.myspace.user.UserForm;
import java.util.List;
import models.Department;
import models.User;
import play.data.validation.Valid;

public class Backyard extends Application {

    public static void index() {
        render();
    }

    public static void show_all_users() {
        List<User> all_users = User.findAll();
        render(all_users);
    }

    public static void edit_user(Long id) {
        User user = User.findById(id);
        List<Department> departments = Department.findAll();

        flash.put("user_form.id", user.id);
        flash.put("user_form.name", user.name);
        flash.put("user_form.email", user.email);
        flash.put("user_form.password", user.password);
        flash.put("user_form.repeat_password", user.password);
        flash.put("user_form.department_id", user.department.id);


        render(departments);
    }

    public static void delete_user_confirm(Long id) {
        User user = User.findById(id);
        render(user);
    }

    public static void delete_user(Long id) {

        User user = User.findById(id);
        user.delete();

        show_all_users();
    }

    public static void update_user(@Valid UserForm user_form) {
        User user = User.update_user(user_form, validation);

        if (user == null) {
            params.flash();

            renderTemplate("backyard/Backyard/edit_user.html");
        }

        show_all_users();
    }
}
