package controllers.backyard;

import controllers.Application;
import java.util.List;
import models.ShareSession;

public class ShareSessionController extends Application {

    public static void index() {


        List<ShareSession> all_share_sessions = ShareSession.findAll();

        render(all_share_sessions);
    }

    public static void edit(Long id) {
    }

    public static void show(Long id) {
    }

    public static void delete_confirm(Long id) {
    }

    public static void publish(Long id) {
    }
}
