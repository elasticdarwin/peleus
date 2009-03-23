package controllers.myspace.user;

import constants.MyConstants;
import controllers.Application;
import controllers.myspace.MySpace;
import forms.UserForm;
import java.util.List;
import models.User;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import play.data.validation.Valid;

public class UserFacade extends Application {

    public static void join() {
        if (has_logined()) {
            session.clear();
        }
        
        render();
    }

    public static void register(@Valid UserForm user_form) {
        User user = User.create(user_form, validation);

        if (user == null) {
            params.flash();
            validation.keep();
            join();
        }

        record_session_for_login(user);
        register_success();
    }

    public static void register_success() {
        if (!has_logined()) {
            login();
        }

        String user_id = session.get(MyConstants.LOGINED_USER_ID);
        User user = User.findById(NumberUtils.toLong(user_id));

        notFoundIfNull(user);
        render(user);
    }

    public static void login() {
        render();
    }

    public static void login_validate() {
        String email = params.get("email");
        String password = params.get("password");

        String queryByEmail = "email";

        List<User> users = (List) User.findBy(queryByEmail, email);
        User loadedUser = null;

        if (users.isEmpty() || users.size() > 1) {
            params.flash();
            validation.keep();
            login();
        } else {
            loadedUser = users.get(0);
            if (validate_member(loadedUser, password)) {
                record_session_for_login(loadedUser);
                MySpace.index();
            } else {
                login();
            }
        }
    }

    private static boolean validate_member(User loadedUser, String password) {
        if (StringUtils.equals(loadedUser.password, password)) {
            return true;
        } else {
            return false;
        }
    }

    public static void logout() {
        session.clear();
        render();
    }

    private static void record_session_for_login(User user) {
        session.put(MyConstants.LOGINED_USER_ID, user.getId());
    }

    private static boolean has_logined() {
        String logined_user_id = session.get(MyConstants.LOGINED_USER_ID);
        return StringUtils.isNotBlank(logined_user_id);
    }
}
