package controllers.dashboard;

import controllers.Application;
import java.util.List;
import models.ShareSession;
import models.User;

public class Dashboard extends Application {

    public static void index() {


//        String user_name = "";

//        Long user_id = NumberUtils.toLong(session.get(MyConstants.LOGINED_USER_ID), 0);
//
//        if (user_id != 0) {
//            User user = User.findById(user_id);
//            if (user != null) {
//                has_loggined = true;
//                user_name = user.name;
//            }
//        }

        boolean has_loggined = false;
        User user = fetch_user();

        if (user != null) {
            has_loggined = true;
        }

        List<ShareSession> share_sessions = ShareSession.findSessionsOnComing();

        render(has_loggined, user, share_sessions);
    }

    public static void under_construction(){
        render();
    }
}
