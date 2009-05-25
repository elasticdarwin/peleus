package controllers.backyard;

import controllers.Application;
import java.util.List;
import models.ShareSession;

import utils.statemachine.ShareSessionStateMachine;
import utils.statemachine.ShareSessionTransition;
import utils.statemachine.StateMachineException;

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

    public static void publish(Long id) throws StateMachineException {
        ShareSession share_session = ShareSession.findById(id);
        redirectToLoginIfNull(share_session);

        if (ShareSessionStateMachine.couldAccept(share_session, ShareSessionTransition.PUBLISH)) {

            ShareSessionStateMachine.transit(share_session, ShareSessionTransition.PUBLISH);

            share_session.save();
        }

        redirectBackIfHasHTTPReferer();

        show(id);
    }

    public static void close(Long id) throws StateMachineException {
        ShareSession share_session = ShareSession.findById(id);
        redirectToLoginIfNull(share_session);

        if (ShareSessionStateMachine.couldAccept(share_session, ShareSessionTransition.CLOSE)) {

            ShareSessionStateMachine.transit(share_session, ShareSessionTransition.CLOSE);

            share_session.save();
        }


        redirectBackIfHasHTTPReferer();
        show(id);
    }
}
