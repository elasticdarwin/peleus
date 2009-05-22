package controllers.backyard;

import controllers.Application;
import java.util.List;
import models.ShareSession;
import models.ShareSession.ShareSessionStatus;

public class ShareSessionController extends Application {

    public static void index() {


        List<ShareSession> all_share_sessions = ShareSession.findAll();

        render(all_share_sessions);
    }

    public static void edit(Long id) {
    }

    public static void show(Long id) {

        ShareSession share_session = ShareSession.findById(id);


        redirectToLoginIfNull(share_session);

        render(share_session);

    }

    public static void delete_confirm(Long id) {
    }

    public static void publish(Long id) {
        ShareSession share_session = ShareSession.findById(id);
        redirectToLoginIfNull(share_session);

        if (share_session.status == ShareSessionStatus.CREATED || share_session.status == ShareSessionStatus.CLOSED) {
            share_session.status = ShareSessionStatus.PUBLISHED;
            share_session.save();
        }

        redirectBackIfHasHTTPReferer();

        show(id);
    }

    public static void close(Long id) {
        ShareSession share_session = ShareSession.findById(id);
        redirectToLoginIfNull(share_session);

        if (share_session.status == ShareSessionStatus.PUBLISHED) {
            share_session.status = ShareSessionStatus.CLOSED;
            share_session.save();
        }


        redirectBackIfHasHTTPReferer();
        show(id);
    }
}
