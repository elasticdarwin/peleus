package utils.statemachine;
import java.util.ArrayList;
import java.util.List;


public class ShareSessionStateMachine {


    public enum ShareSessionStatus {
        
        CREATED, DELETED, EXPIRED, CLOSED, PUBLISHED, FINISHED;

        private int state;
    }


    %%{
        machine share_session;

        action do_init { 
            if (isJustACheck) {
                fgoto *fcurs;
            } else {

                ShareSessionStatus.CREATED.state = fentry(created); 
                ShareSessionStatus.DELETED.state = fentry(deleted); 
                ShareSessionStatus.EXPIRED.state = fentry(expired); 
                ShareSessionStatus.CLOSED.state = fentry(closed); 
                ShareSessionStatus.PUBLISHED.state = fentry(published); 
                ShareSessionStatus.FINISHED.state = fentry(finished); 

                context.setCurrentStatus(ShareSessionStatus.CREATED);

                System.out.println("INIT");
            }
        }
        action do_delete { 
            if (isJustACheck) {
                fgoto *fcurs;
            } else {

                context.setCurrentStatus(ShareSessionStatus.DELETED);
                System.out.println("DELETE");
            }
        }

        action do_publish { 
            if (isJustACheck) {
                fgoto *fcurs;
            } else {

                context.setCurrentStatus(ShareSessionStatus.PUBLISHED);
                System.out.println("PUBLISH");
            }
        }
        action do_expire { 
            if (isJustACheck) {
                fgoto *fcurs;
            } else {

                context.setCurrentStatus(ShareSessionStatus.EXPIRED);
                System.out.println("EXPIRE");
            }
        }
        action do_close {
            if (isJustACheck) {
                fgoto *fcurs;
            } else {

                context.setCurrentStatus(ShareSessionStatus.CLOSED);
                System.out.println("CLOSE");
            }
        }
        action do_finish { 
            if (isJustACheck) {
                fgoto *fcurs;
            } else {

                context.setCurrentStatus(ShareSessionStatus.FINISHED);
                System.out.println("FINISH");
            }
        }
        
        action do_error {

            if (isJustACheck) {
                if (true) { // just to cheat java compiler, haha~
            
                    System.out.println("Check failed");

                    return false;
                }
            } else { 

                System.out.println("ERROR");
                throw new StateMachineException("This transition can not be accepted.\nNote: current state is <" + context.currentStatus + "> and are confronted with transition <" + ShareSessionTransition.valueOf(fc) + ">");
            }
        }


        action do_eof {
//          System.out.println("EOF");
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

    private static boolean run(ShareSessionContext context, Character[] data, boolean isJustACheck) throws StateMachineException {

        System.out.print("Running the state machine with input [");
        for (char c : data) {

            System.out.print(c + " ");
        }
        System.out.println("]");
        
        int p = 0;
        int pe = data.length;
        int eof = pe;
        int cs;

        if (context.currentStatus == null) {
            %% write init;
        } else {
            cs = context.currentStatus.state;
        }
        
        ShareSessionStatus cs_backup_for_rollback = context.currentStatus;
        
        try {

            %% write exec;
        } catch (StateMachineException e) {

            System.out.println("Rollbacking changes...");
            context.currentStatus = cs_backup_for_rollback;
            throw e; // resent it
        }

        if (!isJustACheck) {
            System.out.println("Finished. The state of the machine is: " + context.currentStatus);
            System.out.println("p: " + p + " pe: " + pe);
        } else {
            
            System.out.println("Check passed");
        }

        return true;
    }

    private static boolean run(ShareSessionContext context, ShareSessionTransition[] transitions, boolean isJustACheck) throws StateMachineException {

        List<Character> chars = new ArrayList<Character>(transitions.length);
        
        for (ShareSessionTransition transition : transitions) {
            
            if (transition == null) {continue;}
            chars.add(transition.getCode());
        }
        return run(context, chars.toArray(new Character[0]), isJustACheck);
    }

    public static void run(ShareSessionContext context, ShareSessionTransition[] transitions) throws StateMachineException {
        run(context, transitions, false);
    }

    public static void run(ShareSessionContext context, ShareSessionTransition transition) throws StateMachineException {
        run(context, new ShareSessionTransition[] {transition}, false);
    }


    public static boolean check(ShareSessionContext context, ShareSessionTransition[] transitions) throws StateMachineException {
        return run(context, transitions, true);
    }

    public static boolean check(ShareSessionContext context, ShareSessionTransition transition) throws StateMachineException {
        return run(context, new ShareSessionTransition[] {transition}, true);
    }



    public static void main(String[] args) throws StateMachineException {

        ShareSessionContext context = ShareSessionStateMachine.initContext();

        ShareSessionStateMachine.run(context, ShareSessionTransition.INIT);

        ShareSessionStateMachine.run(context, new ShareSessionTransition[] {});

        ShareSessionStateMachine.run(context, ShareSessionTransition.PUBLISH);

        try {
            // should raise a error
            ShareSessionStateMachine.run(context, new ShareSessionTransition[] {ShareSessionTransition.CLOSE, ShareSessionTransition.PUBLISH, ShareSessionTransition.PUBLISH});
            throw new RuntimeException("should raise a error");
        } catch (StateMachineException e) {

            // Noops 
        }

        boolean success = ShareSessionStateMachine.check(context, ShareSessionTransition.PUBLISH);
        if (success) {

            throw new RuntimeException("should not pass check");
        }

        success = ShareSessionStateMachine.check(context, ShareSessionTransition.CLOSE);
        if (!success) {

            throw new RuntimeException("should pass check");
        }

        ShareSessionStateMachine.run(context, new ShareSessionTransition[] {ShareSessionTransition.CLOSE, ShareSessionTransition.PUBLISH});
         
        ShareSessionStateMachine.run(context, ShareSessionTransition.FINISH);

    }

    public static class StateMachineException extends Exception {
        
        public StateMachineException(String message) {
            
            super(message);
        }
    } 


    public static ShareSessionContext initContext() {
        
        return new ShareSessionContext();
    }
    


    static class ShareSessionContext {
        
        private ShareSessionStatus currentStatus;

        public void setCurrentStatus(ShareSessionStatus currentStatus) {
            this.currentStatus = currentStatus;
        }

        public ShareSessionStatus getCurrentStatus() {
            return currentStatus;
        }
        
        
    }


    public enum ShareSessionTransition {
        
        INIT('i'), DELETE('d'), EXPIRE('e'), CLOSE('c'), PUBLISH('p'), FINISH('f');
        
        private char code;
        ShareSessionTransition(char code) {
            
            this.code = code;
        }
        
        public char getCode() {
            
            return code;
        }
        public static ShareSessionTransition valueOf(char code) {
            
            for (ShareSessionTransition transition : values()) {
                
                if (transition.getCode() == code) {
                    
                    return transition;
                }
            }
            return null;
        }
        
        
    }
}

