package controllers;

import controllers.myspace.user.UserFacade;
import utils.MyConstants;
import models.User;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import play.mvc.*;

public class Application extends Controller {

//    @Before
//    static void set_language() {
//
//        String prefered_lang = session.get(MyConstants.PREFERED_LANG);
//
//        if (StringUtils.isNotBlank(prefered_lang)) {
//            Lang.change(prefered_lang);
//        }
//
//    }
    protected static boolean has_logined() {
        String logined_user_id = session.get(MyConstants.LOGINED_USER_ID);
        return StringUtils.isNotBlank(logined_user_id);
    }

    protected static User fetch_user_or_redirect_to_login() {

        User user = fetch_user();

        redirectToLoginIfNo(user);

        return user;
    }

    protected static User fetch_user() {
        Long user_id = NumberUtils.toLong(session.get(MyConstants.LOGINED_USER_ID), 0);

        User user = null;
        if (user_id != 0) {
            user = User.findById(user_id);
        }

        return user;
    }

    protected static void redirectToLoginIfNo(Object obj) {
        if (obj == null) {

            UserFacade.login();
        }
    }

    protected static void forbiddenIfNo(Object obj) {
        if (obj == null) {
            forbidden("Got Nil Object ! Target is missing!");
        }
    }

    protected static void redirectBackIfHasHTTPReferer() {
        String referer_url = request.headers.get(MyConstants.HTTP_REFERER).value();
        if (StringUtils.isNotBlank(referer_url)) {
            redirect(referer_url);
        }
    }
}