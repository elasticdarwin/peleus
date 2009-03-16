package controllers.myspace;

import constants.MyConstants;
import controllers.*;
import models.User;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 *
 * @author darwin
 */
public class MySpace extends Application {


    public static void index() {

        String user_id = session.get(MyConstants.LOGINED_USER_ID);

        // TODO catch the number parsing exception
        User user = User.findById(NumberUtils.toLong(user_id));


        boolean is_new_user = false;

        if (StringUtils.equalsIgnoreCase(MyConstants.YES, session.get(MyConstants.REGIST_JUST_A_SECOND_AGO_FLAG))) {
            is_new_user = true;
        }

        render(user, is_new_user);
    }
}
