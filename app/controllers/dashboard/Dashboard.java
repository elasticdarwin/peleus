package controllers.dashboard;

import constants.MyConstants;
import controllers.*;
import java.util.List;
import models.ShareSession;
import models.User;
import org.apache.commons.lang.math.NumberUtils;

public class Dashboard extends Application {

    public static void index() {

        boolean has_loggined = false;
        String user_name = "";

        Long user_id = NumberUtils.toLong(session.get(MyConstants.LOGINED_USER_ID), 0);

        if (user_id != 0) {
            User user = User.findById(user_id);
            if (user != null) {
                has_loggined = true;
                user_name = user.name;
            }
        }


        List<ShareSession> share_sessions = ShareSession.findAll();

        render(has_loggined, user_name, share_sessions);
    }
}
