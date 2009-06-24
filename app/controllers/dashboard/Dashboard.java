package controllers.dashboard;

import controllers.Application;
import java.util.List;
import models.ShareSession;
import models.User;

public class Dashboard extends Application {

    public static void index() {
        boolean has_loggined = false;
        User user = fetch_user();

        if (user != null) {
            has_loggined = true;
        }

        List<ShareSession> share_sessions = ShareSession.findSessionsOnComing();

        render(has_loggined, user, share_sessions);
    }

    public static void show(Long id) {
        forbiddenIfNo(id);

        ShareSession share_session = ShareSession.findById(id);

        forbiddenIfNo(share_session);
        
        render(share_session);
    }

    public static void under_construction() {
        render();
    }
}
