package controllers.myspace.user;

import utils.MyConstants;
import controllers.Application;
import controllers.myspace.MySpace;
import forms.myspace.user.LoginForm;
import forms.myspace.user.UserForm;
import java.util.List;
import models.Department;
import models.User;
import play.Logger;
import play.data.validation.Valid;

public class UserFacade extends Application {

    public static void join() {
        if (has_logined()) {
            session.clear();
        }


        List<Department> departments = Department.findAll();

        render(departments);

    }

    public static void register(@Valid UserForm user_form) {
        User user = User.create(user_form, validation);

        if (user == null) {
            params.flash();
            validation.keep();
            join();
        }

        record_session(user);
        register_success();
    }

    public static void register_success() {
        if (!has_logined()) {
            login();
        }

        User user = fetch_user_or_redirect_to_login();

        render(user);
    }

    public static void login() {
        render();
    }

    public static void login_validate(@Valid LoginForm login_form) {

        User user = User.login(login_form.email, login_form.password, validation);

        if (user == null) {
            params.flash();
            validation.keep();

            login();
        }

        record_session(user);
        MySpace.index();
    }

    public static void logout() {
        session.clear();
        render();
    }

    private static void record_session(User user) {
        session.put(MyConstants.LOGINED_USER_ID, user.getId());
    }

    public static void show(Long id) {
        
        forbiddenIfNo(id);
        User user = User.findById(id);
        forbiddenIfNo(user);

        render(user);
    }

    public static void my_profile(){
        User current_user = fetch_user_or_redirect_to_login();

        render(current_user);
    }

    public static void edit_profile(){
        User user = fetch_user_or_redirect_to_login();
        List<Department> departments = Department.findAll();

        flash.put("user_form.id", user.id);
        flash.put("user_form.name", user.name);
        flash.put("user_form.email", user.email);
        flash.put("user_form.password", user.password);
        flash.put("user_form.repeat_password", user.password);
        flash.put("user_form.department_id", user.department.id);

        render(departments);
    }

    public static void update_profile(@Valid UserForm user_form){

        User user = User.update_user(user_form,fetch_user_or_redirect_to_login(),validation);

        forbiddenIfNo(user);

       if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            edit_profile();
        }

        my_profile();

    }
}
