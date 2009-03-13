package controllers.myspace;

import constants.MyConstants;
import controllers.*;
import models.User;

/**
 *
 * @author darwin
 */
public class MySpace extends Application {

    public static void join() {
        render();
    }

    public static void register() {
        String name = params.get("name");
        String email = params.get("email");
        String password = params.get("password");
        String repeat_password = params.get("repeart_password");

        User user = User.create_user(name, email, password, repeat_password);
        session.put(MyConstants.LOGINED_USER, user);
        index();
    }

    public static void index() {
        render();
    }
}
