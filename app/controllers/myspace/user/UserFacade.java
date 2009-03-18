/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.myspace.user;

import constants.MyConstants;
import controllers.Application;
import controllers.myspace.MySpace;
import models.User;

/**
 *
 * @author arthur
 */
public class UserFacade extends Application {

    public static void join() {
        render();
    }

    public static void register() {
        String name = params.get("name");
        String email = params.get("email");
        String password = params.get("password");
        String repeat_password = params.get("repeat_password");
        String prefered_lang = params.get("prefered_lang");

        User user = User.create_user(name, email, password);
        session.put(MyConstants.LOGINED_USER_ID, user.getId());
        session.put(MyConstants.REGIST_JUST_A_SECOND_AGO_FLAG, MyConstants.YES);
        session.put(MyConstants.PREFERED_LANG, prefered_lang);

        MySpace.index();
    }
}
