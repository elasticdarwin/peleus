/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.statemachine;

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