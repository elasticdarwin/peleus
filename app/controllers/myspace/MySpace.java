package controllers.myspace;


import controllers.Application;
import controllers.myspace.user.UserFacade;
import models.User;


public class MySpace extends Application {

    public static void index() {

        if (!has_logined()) {

            UserFacade.login();
        } else {
            User user = fetch_user_or_redirect_to_login();
            render(user);
        }
    }

}
