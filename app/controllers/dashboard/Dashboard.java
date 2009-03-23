package controllers.dashboard;

import constants.MyConstants;
import controllers.*;
import org.apache.commons.lang.StringUtils;


public class Dashboard extends Application {

    public static void index() {

        boolean has_loggined = false;
        String user_name = "";

        String has_logined_flag_in_session = session.get(MyConstants.HAS_LOGINED);

        if (StringUtils.isNotEmpty(has_logined_flag_in_session)) {
            if (has_logined_flag_in_session.equalsIgnoreCase(MyConstants.YES)) {
                has_loggined = true;
                user_name = "hard code user name";
            }
        }

        render(has_loggined, user_name);
    }
}
