package controllers.myspace;

import constants.MyConstants;
import controllers.*;
import controllers.myspace.user.UserFacade;
import models.User;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 *
 * @author darwin
 */
public class MySpace extends Application {

    public static void index() {

        String has_logined_flag = session.get(MyConstants.HAS_LOGINED);

        if (StringUtils.isEmpty(has_logined_flag)) {
            return;
        }

        if (!has_logined_flag.equalsIgnoreCase(MyConstants.YES)) {
            UserFacade.login();
        } else {
            String user_id = session.get(MyConstants.LOGINED_USER_ID);
            // TODO catch the number parsing exception
            User user = User.findById(NumberUtils.toLong(user_id));

            render(user);
        }
    }
}
