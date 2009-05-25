/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.statemachine;

import play.Logger;
import utils.statemachine.ShareSessionStateMachine.ShareSessionStatus;

public interface ShareSessionContext {

    void setCurrentStatus(ShareSessionStatus currentStatus);

    ShareSessionStatus getCurrentStatus();
}
