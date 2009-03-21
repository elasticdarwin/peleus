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

/**
 *
 * @author arthur
 */
public class UserFacade extends Application {

    public static void join() {
        render();
    }

    public static void register(@Valid UserForm userForm) {
        if (userForm == null) {
            join();
        }

//        String prefered_lang = params.get("prefered_lang");
        if (validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            join();
        }
//        User user = User.create_user(name, email, password, repeat_password);

        User user = User.create(userForm);

        session.put(MyConstants.HAS_LOGINED, MyConstants.YES);
        session.put(MyConstants.LOGINED_USER_ID, user.getId());
        session.put(MyConstants.LOGINED_USER_NAME, user.name);

        // session.put(MyConstants.PREFERED_LANG, userForm.prefered_lang);
        register_success();
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
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request

            login();
        } else {
            loadedUser = users.get(0);
            if (validate_member(loadedUser, password)) {
                session.put(MyConstants.HAS_LOGINED, MyConstants.YES);
                session.put(MyConstants.LOGINED_USER_ID, loadedUser.getId());

                MySpace.index();
            } else {
                login();
            }
        }
    }

    public static void logout() {
        session.clear();
        render();
    }

    public static void register_success() {

        String user_id = session.get(MyConstants.LOGINED_USER_ID);

        if (StringUtils.isBlank(user_id)) {
            login();
        }

        User user = User.findById(NumberUtils.toLong(user_id));

        render(user);
    }

    private static boolean validate_member(User loadedUser, String password) {
        if (StringUtils.equals(loadedUser.password, password)) {
            return true;
        } else {
            return false;
        }
    }
}
