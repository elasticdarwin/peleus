package utils.statemachine;
import java.util.ArrayList;
import java.util.List;
import play.Logger;


public class ShareSessionStateMachine {


    public enum ShareSessionStatus {
        
        CREATED, DELETED, EXPIRED, CLOSED, PUBLISHED, FINISHED;

        private int stateCode;
    }


    %%{
        machine share_session;

        action do_init { 
            if (!isJustACheck) {

                ShareSessionStatus.CREATED.stateCode = fentry(created); 
                ShareSessionStatus.DELETED.stateCode = fentry(deleted); 
                ShareSessionStatus.EXPIRED.stateCode = fentry(expired); 
                ShareSessionStatus.CLOSED.stateCode = fentry(closed); 
                ShareSessionStatus.PUBLISHED.stateCode = fentry(published); 
                ShareSessionStatus.FINISHED.stateCode = fentry(finished); 

                context.setCurrentStatus(ShareSessionStatus.CREATED);

                Logger.info("INIT");
            }
        }
        action do_delete { 
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.DELETED);
                Logger.info("DELETE");
            }
        }

        action do_publish { 
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.PUBLISHED);
                Logger.info("PUBLISH");
            }
        }
        action do_expire { 
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.EXPIRED);
                Logger.info("EXPIRE");
            }
        }
        action do_close {
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.CLOSED);
                Logger.info("CLOSE");
            }
        }
        action do_finish { 
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.FINISHED);
                Logger.info("FINISH");
            }
        }
        
        action do_error {

            if (isJustACheck) {
                if (true) { // just to cheat java compiler, haha~
            
                    Logger.info("Check failed");

                    return false;
                }
            } else { 

                Logger.info("ERROR");
                throw new StateMachineException("This transition can not be accepted.\nNote: current state is <" + context.getCurrentStatus() + "> and are confronted with transition <" + ShareSessionTransition.valueOf(fc) + ">");
            }
        }


        action do_eof {
//          Logger.info("EOF");
            fbreak;
        }


        init = 'i';
        delete = 'd';
        publish = 'p';
        expire = 'e';
        close = 'c';
        finish = 'f';

        ShareSession = (
            start: (
                init @do_init -> created
            ),

            created: ( 
                delete @do_delete -> deleted |
                publish @do_publish -> published |
                expire @do_expire -> expired
            ),

            deleted: (
                null   
            ),


            published: (
                close @do_close -> closed |
                finish @do_finish -> finished
            ),

            finished: (
                null   
            ),

            closed: (
                delete @do_delete -> deleted |
                publish @do_publish -> published | 
                expire @do_expire -> expired
            ),

            expired: (
                null 
            )

        ) $/do_eof $!do_error;


        main := ShareSession;

    }%%

    %% write data;

    private static boolean transit(ShareSessionContext context, Character[] data, boolean isJustACheck) {

        StringBuilder buffer = new StringBuilder();
        buffer.append("Running the state machine with input [");
        for (char c : data) {

            buffer.append(c + " ");
        }
        buffer.append("]");
        Logger.info(buffer.toString());
        
        int p = 0;
        int pe = data.length;
        int eof = pe;
        int cs;

        if (context.getCurrentStatus() == null) {
            %% write init;
        } else {
            cs = context.getCurrentStatus().stateCode;
        }
        
        ShareSessionStatus cs_backup_for_rollback = context.getCurrentStatus();
        
        try {

            %% write exec;
        } catch (StateMachineException e) {

            Logger.info("Rollbacking changes...");
            context.setCurrentStatus(cs_backup_for_rollback);
            throw e; // resent it
        }

        if (!isJustACheck) {
            Logger.info("Finished. The state of the machine is: " + context.getCurrentStatus());
            Logger.info("p: " + p + " pe: " + pe);
        } else {
            
            Logger.info("Check passed");
        }

        return true;
    }

    private static boolean transit(ShareSessionContext context, ShareSessionTransition[] transitions, boolean isJustACheck) {

        List<Character> chars = new ArrayList<Character>(transitions.length);
        
        for (ShareSessionTransition transition : transitions) {
            
            if (transition == null) {continue;}
            chars.add(transition.getCode());
        }
        return transit(context, chars.toArray(new Character[0]), isJustACheck);
    }

    public static void transit(ShareSessionContext context, ShareSessionTransition[] transitions) {
        transit(context, transitions, false);
    }

    public static void transit(ShareSessionContext context, ShareSessionTransition transition) {
        transit(context, new ShareSessionTransition[] {transition}, false);
    }


    public static boolean couldAccept(ShareSessionContext context, ShareSessionTransition[] transitions) {
        return transit(context, transitions, true);
    }

    public static boolean couldAccept(ShareSessionContext context, ShareSessionTransition transition) {
        return transit(context, new ShareSessionTransition[] {transition}, true);
    }

}

