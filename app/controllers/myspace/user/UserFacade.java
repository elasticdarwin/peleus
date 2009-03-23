package controllers.myspace.user;

import constants.MyConstants;
import controllers.Application;
import controllers.myspace.MySpace;
import forms.LoginForm;
import forms.RegisterForm;
import models.User;
import play.data.validation.Valid;

public class UserFacade extends Application {

    public static void join() {
        if (has_logined()) {
            session.clear();
        }
        
        render();
    }

    public static void register(@Valid RegisterForm register_form) {
        User user = User.create(register_form, validation);

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

        User user = fetch_user();

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


}
