/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import models.ShareSession;
import org.junit.Test;
import play.test.UnitTest;
import utils.statemachine.ShareSessionContext;
import utils.statemachine.ShareSessionStateMachine;
import utils.statemachine.ShareSessionStateMachine.ShareSessionStatus;
import utils.statemachine.ShareSessionTransition;
import utils.statemachine.StateMachineException;

public class StateMachineTest extends UnitTest {

    @Test
    public void basic_flow() throws StateMachineException {

        ShareSessionContext context = new ShareSession();

        ShareSessionStateMachine.transit(context, ShareSessionTransition.INIT);
        assertEquals(context.getCurrentStatus(), ShareSessionStatus.CREATED);

        ShareSessionStateMachine.transit(context, new ShareSessionTransition[]{});
        assertEquals(context.getCurrentStatus(), ShareSessionStatus.CREATED);


        ShareSessionStateMachine.transit(context, ShareSessionTransition.PUBLISH);
        assertEquals(context.getCurrentStatus(), ShareSessionStatus.PUBLISHED);

        try {
            // should raise a error
            ShareSessionStateMachine.transit(context, new ShareSessionTransition[]{ShareSessionTransition.CLOSE, ShareSessionTransition.PUBLISH, ShareSessionTransition.PUBLISH});
            fail("should raise a error");
        } catch (StateMachineException e) {

            // Noops
        }

        boolean success = ShareSessionStateMachine.couldAccept(context, ShareSessionTransition.PUBLISH);

        assertFalse("should not pass couldAccept", success);
        assertEquals(context.getCurrentStatus(), ShareSessionStatus.PUBLISHED);



        success = ShareSessionStateMachine.couldAccept(context, ShareSessionTransition.CLOSE);
        assertTrue("should pass couldAccept", success);
        assertEquals(context.getCurrentStatus(), ShareSessionStatus.PUBLISHED);


        ShareSessionStateMachine.transit(context, new ShareSessionTransition[]{ShareSessionTransition.CLOSE, ShareSessionTransition.PUBLISH});
        assertEquals(context.getCurrentStatus(), ShareSessionStatus.PUBLISHED);


        ShareSessionStateMachine.transit(context, ShareSessionTransition.FINISH);
        assertEquals(context.getCurrentStatus(), ShareSessionStatus.FINISHED);

    }

    @Test
    public void publish_before_init_it() throws StateMachineException {

        ShareSessionContext context = new ShareSession();

        try {
            // should raise a error
            ShareSessionStateMachine.transit(context, ShareSessionTransition.PUBLISH);

            fail("should raise a error");
        } catch (StateMachineException e) {
            assertNull(context.getCurrentStatus());
        // Noops
        }
    }

    @Test
    public void could_delete_when_closed() throws StateMachineException {

        ShareSessionContext context = new ShareSession();

        ShareSessionStateMachine.transit(context, ShareSessionTransition.INIT);

        boolean success = ShareSessionStateMachine.couldAccept(context, ShareSessionTransition.DELETE);

        assertTrue("should pass couldAccept", success);


        ShareSessionStateMachine.transit(context, ShareSessionTransition.PUBLISH);
        success = ShareSessionStateMachine.couldAccept(context, ShareSessionTransition.DELETE);
        assertFalse("should not pass couldAccept", success);


        ShareSessionStateMachine.transit(context, ShareSessionTransition.CLOSE);
        success = ShareSessionStateMachine.couldAccept(context, ShareSessionTransition.DELETE);
        assertTrue("should pass couldAccept", success);

    }

    @Test
    public void could_accept() throws StateMachineException {

        ShareSessionContext context = new ShareSession();


        boolean success = ShareSessionStateMachine.couldAccept(context, ShareSessionTransition.INIT);

        assertTrue("should pass couldAccept", success);
        assertNull(context.getCurrentStatus());


        success = ShareSessionStateMachine.couldAccept(context, new ShareSessionTransition[]{ShareSessionTransition.INIT});
        assertTrue("should pass couldAccept", success);
        assertNull(context.getCurrentStatus());


        success = ShareSessionStateMachine.couldAccept(context, new ShareSessionTransition[]{ShareSessionTransition.INIT, ShareSessionTransition.PUBLISH});
        assertTrue("should pass couldAccept", success);
        assertNull(context.getCurrentStatus());



//        ShareSessionStateMachine.transit(context, ShareSessionTransition.INIT);
//        assertEquals(context.getCurrentStatus(), ShareSessionStatus.CREATED);
//
//        ShareSessionStateMachine.transit(context, new ShareSessionTransition[]{});
//        assertEquals(context.getCurrentStatus(), ShareSessionStatus.CREATED);
//
//
//        ShareSessionStateMachine.transit(context, ShareSessionTransition.PUBLISH);
//        assertEquals(context.getCurrentStatus(), ShareSessionStatus.PUBLISHED);
//
//        try {
//            // should raise a error
//            ShareSessionStateMachine.transit(context, new ShareSessionTransition[]{ShareSessionTransition.CLOSE, ShareSessionTransition.PUBLISH, ShareSessionTransition.PUBLISH});
//            fail("should raise a error");
//        } catch (StateMachineException e) {
//
//            // Noops
//        }
//
//        boolean success = ShareSessionStateMachine.couldAccept(context, ShareSessionTransition.PUBLISH);
//
//        assertFalse("should not pass couldAccept", success);
//        assertEquals(context.getCurrentStatus(), ShareSessionStatus.PUBLISHED);
//
//
//
//        success = ShareSessionStateMachine.couldAccept(context, ShareSessionTransition.CLOSE);
//        assertTrue("should pass couldAccept", success);
//        assertEquals(context.getCurrentStatus(), ShareSessionStatus.PUBLISHED);
//
//
//        ShareSessionStateMachine.transit(context, new ShareSessionTransition[]{ShareSessionTransition.CLOSE, ShareSessionTransition.PUBLISH});
//        assertEquals(context.getCurrentStatus(), ShareSessionStatus.PUBLISHED);
//
//
//        ShareSessionStateMachine.transit(context, ShareSessionTransition.FINISH);
//        assertEquals(context.getCurrentStatus(), ShareSessionStatus.FINISHED);

    }
}
