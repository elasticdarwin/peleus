package controllers;

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

    protected static User fetch_user() {
        String user_id = session.get(MyConstants.LOGINED_USER_ID);
        User user = User.findById(NumberUtils.toLong(user_id, -1));

        forbiddenIfNull(user);
        return user;
    }

    protected static void forbiddenIfNull(Object obj) {
        if (obj == null) {
            forbidden("You are forbidden to access this page.");
        }
    }

    protected static void redirectBackIfHasHTTPReferer() {
        String referer_url = request.headers.get(MyConstants.HTTP_REFERER).value();
        if (StringUtils.isNotBlank(referer_url)) {
            redirect(referer_url);
        }
    }
}