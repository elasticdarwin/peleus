// line 1 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
package utils.statemachine;
import java.util.ArrayList;
import java.util.List;
import play.Logger;


public class ShareSessionStateMachine {


    public enum ShareSessionStatus {

        CREATED, DELETED, EXPIRED, CLOSED, PUBLISHED, FINISHED;

        private int stateCode;
    }

    // line 149 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"


    
// line 23 "./script/../app/utils/statemachine/ShareSessionStateMachine.java"
private static byte[] init__share_session_actions_0()
{
	return new byte [] {
	    0,    1,    0,    1,    1,    1,    2,    1,    3,    1,    4,    1,
	    5,    1,    6,    1,    7,    1,    8,    2,    8,    7
	};
}

private static final byte _share_session_actions[] = init__share_session_actions_0();


private static byte[] init__share_session_key_offsets_0()
{
	return new byte [] {
	    0,    0,    2,    5,    5,    7
	};
}

private static final byte _share_session_key_offsets[] = init__share_session_key_offsets_0();


private static char[] init__share_session_trans_keys_0()
{
	return new char [] {
	   48,  105,  100,  101,  112,   99,  102,    0
	};
}

private static final char _share_session_trans_keys[] = init__share_session_trans_keys_0();


private static byte[] init__share_session_single_lengths_0()
{
	return new byte [] {
	    0,    2,    3,    0,    2,    0
	};
}

private static final byte _share_session_single_lengths[] = init__share_session_single_lengths_0();


private static byte[] init__share_session_range_lengths_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0
	};
}

private static final byte _share_session_range_lengths[] = init__share_session_range_lengths_0();


private static byte[] init__share_session_index_offsets_0()
{
	return new byte [] {
	    0,    0,    3,    7,    8,   11
	};
}

private static final byte _share_session_index_offsets[] = init__share_session_index_offsets_0();


private static byte[] init__share_session_trans_targs_0()
{
	return new byte [] {
	    5,    2,    0,    3,    3,    4,    0,    0,    2,    3,    0,    0,
	    0
	};
}

private static final byte _share_session_trans_targs[] = init__share_session_trans_targs_0();


private static byte[] init__share_session_trans_actions_0()
{
	return new byte [] {
	    1,    3,   15,    5,    9,    7,   15,   15,   11,   13,   15,   15,
	    0
	};
}

private static final byte _share_session_trans_actions[] = init__share_session_trans_actions_0();


private static byte[] init__share_session_eof_actions_0()
{
	return new byte [] {
	    0,   19,   19,   19,   19,   17
	};
}

private static final byte _share_session_eof_actions[] = init__share_session_eof_actions_0();


static final int share_session_start = 1;
static final int share_session_first_final = 5;
static final int share_session_error = 0;

static final int share_session_en_main = 1;
static final int share_session_en_main_ShareSession_created = 2;
static final int share_session_en_main_ShareSession_deleted = 3;
static final int share_session_en_main_ShareSession_published = 4;
static final int share_session_en_main_ShareSession_finished = 3;
static final int share_session_en_main_ShareSession_closed = 2;
static final int share_session_en_main_ShareSession_expired = 3;

// line 152 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"

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
            
// line 149 "./script/../app/utils/statemachine/ShareSessionStateMachine.java"
	{
	cs = share_session_start;
	}
// line 171 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
        } else {

            cs = context.getCurrentStatus().stateCode;
        }
        
        ShareSessionStatus cs_backup_for_rollback = context.getCurrentStatus();
        
        try {

            
// line 164 "./script/../app/utils/statemachine/ShareSessionStateMachine.java"
	{
	int _klen;
	int _trans = 0;
	int _acts;
	int _nacts;
	int _keys;
	int _goto_targ = 0;

	_goto: while (true) {
	switch ( _goto_targ ) {
	case 0:
	if ( p == pe ) {
		_goto_targ = 4;
		continue _goto;
	}
	if ( cs == 0 ) {
		_goto_targ = 5;
		continue _goto;
	}
case 1:
	_match: do {
	_keys = _share_session_key_offsets[cs];
	_trans = _share_session_index_offsets[cs];
	_klen = _share_session_single_lengths[cs];
	if ( _klen > 0 ) {
		int _lower = _keys;
		int _mid;
		int _upper = _keys + _klen - 1;
		while (true) {
			if ( _upper < _lower )
				break;

			_mid = _lower + ((_upper-_lower) >> 1);
			if ( data[p] < _share_session_trans_keys[_mid] )
				_upper = _mid - 1;
			else if ( data[p] > _share_session_trans_keys[_mid] )
				_lower = _mid + 1;
			else {
				_trans += (_mid - _keys);
				break _match;
			}
		}
		_keys += _klen;
		_trans += _klen;
	}

	_klen = _share_session_range_lengths[cs];
	if ( _klen > 0 ) {
		int _lower = _keys;
		int _mid;
		int _upper = _keys + (_klen<<1) - 2;
		while (true) {
			if ( _upper < _lower )
				break;

			_mid = _lower + (((_upper-_lower) >> 1) & ~1);
			if ( data[p] < _share_session_trans_keys[_mid] )
				_upper = _mid - 2;
			else if ( data[p] > _share_session_trans_keys[_mid+1] )
				_lower = _mid + 2;
			else {
				_trans += ((_mid - _keys)>>1);
				break _match;
			}
		}
		_trans += _klen;
	}
	} while (false);

	cs = _share_session_trans_targs[_trans];

	if ( _share_session_trans_actions[_trans] != 0 ) {
		_acts = _share_session_trans_actions[_trans];
		_nacts = (int) _share_session_actions[_acts++];
		while ( _nacts-- > 0 )
	{
			switch ( _share_session_actions[_acts++] )
			{
	case 0:
// line 21 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
	{
            ShareSessionStatus.CREATED.stateCode = 2;
            ShareSessionStatus.DELETED.stateCode = 3;
            ShareSessionStatus.EXPIRED.stateCode = 3;
            ShareSessionStatus.CLOSED.stateCode = 2;
            ShareSessionStatus.PUBLISHED.stateCode = 4;
            ShareSessionStatus.FINISHED.stateCode = 3;

            Logger.info("STATE MACHINE INITIALIZED\t\tjust a cheat~");
        }
	break;
	case 1:
// line 32 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
	{ 
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.CREATED);
                Logger.info("INIT");
            }
        }
	break;
	case 2:
// line 39 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
	{ 
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.DELETED);
                Logger.info("DELETE");
            }
        }
	break;
	case 3:
// line 47 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
	{ 
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.PUBLISHED);
                Logger.info("PUBLISH");
            }
        }
	break;
	case 4:
// line 54 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
	{ 
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.EXPIRED);
                Logger.info("EXPIRE");
            }
        }
	break;
	case 5:
// line 61 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
	{
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.CLOSED);
                Logger.info("CLOSE");
            }
        }
	break;
	case 6:
// line 68 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
	{ 
            if (!isJustACheck) {

                context.setCurrentStatus(ShareSessionStatus.FINISHED);
                Logger.info("FINISH");
            }
        }
	break;
	case 7:
// line 76 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
	{

            if (isJustACheck) {
                if (true) { // just to cheat java compiler, haha~
            
                    Logger.info("Check failed");

                    return false;
                }
            } else { 

                Logger.info("ERROR");
                throw new StateMachineException("This transition can not be accepted.\nNote: current state is <" + context.getCurrentStatus() + "> and are confronted with transition <" + ShareSessionTransition.valueOf(data[p]) + ">");
            }
        }
	break;
// line 334 "./script/../app/utils/statemachine/ShareSessionStateMachine.java"
			}
		}
	}

case 2:
	if ( cs == 0 ) {
		_goto_targ = 5;
		continue _goto;
	}
	if ( ++p != pe ) {
		_goto_targ = 1;
		continue _goto;
	}
case 4:
	if ( p == eof )
	{
	int __acts = _share_session_eof_actions[cs];
	int __nacts = (int) _share_session_actions[__acts++];
	while ( __nacts-- > 0 ) {
		switch ( _share_session_actions[__acts++] ) {
	case 7:
// line 76 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
	{

            if (isJustACheck) {
                if (true) { // just to cheat java compiler, haha~
            
                    Logger.info("Check failed");

                    return false;
                }
            } else { 

                Logger.info("ERROR");
                throw new StateMachineException("This transition can not be accepted.\nNote: current state is <" + context.getCurrentStatus() + "> and are confronted with transition <" + ShareSessionTransition.valueOf(data[p]) + ">");
            }
        }
	break;
	case 8:
// line 93 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
	{
//          Logger.info("EOF");
            { p += 1; _goto_targ = 5; if (true)  continue _goto;}
        }
	break;
// line 380 "./script/../app/utils/statemachine/ShareSessionStateMachine.java"
		}
	}
	}

case 5:
	}
	break; }
	}
// line 181 "/home/darwin/NetBeansProjects/peleus/script/ShareSessionStateMachine.rl"
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



    static {

        transit(new ShareSessionContext() {
            public void setCurrentStatus(ShareSessionStatus currentStatus) {}

            public ShareSessionStatus getCurrentStatus() {return null;}
        }, new Character[]{'0'}, false);
    }

}

