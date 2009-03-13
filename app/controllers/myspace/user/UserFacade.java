/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers.myspace.user;

import constants.MyConstants;
import controllers.myspace.MySpace;
import models.User;

/**
 *
 * @author arthur
 */
public class UserFacade extends MySpace{

        public static void join() {
        render();
    }

    public static void register() {
        String name = params.get("name");
        String email = params.get("email");
        String password = params.get("password");
        String repeat_password = params.get("repeart_password");

        User user = User.create_user(name, email, password, repeat_password);
        session.put(MyConstants.LOGINED_USER, user);
        MySpace.index();
    }

}
