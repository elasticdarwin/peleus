/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.myspace.user;

import constants.MyConstants;
import controllers.Application;
import controllers.backyard.Backyard;
import controllers.myspace.MySpace;
import forms.UserForm;
import java.util.List;
import models.User;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.data.validation.Valid;
import play.i18n.Messages;

/**
 *
 * @author arthur
 */
public class UserFacade extends Application {

    public static void join() {
        render();
    }

    public static void register(@Valid UserForm userForm) {
//        String prefered_lang = params.get("prefered_lang");
        if (validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            join();
        }


//        User user = User.create_user(name, email, password, repeat_password);

        User user = User.create(userForm);

        session.put(MyConstants.HAS_LOGINED, MyConstants.YES);
        session.put(MyConstants.LOGINED_USER_ID, user.getId());
        session.put(MyConstants.REGIST_JUST_A_SECOND_AGO_FLAG, MyConstants.YES);
        session.put(MyConstants.PREFERED_LANG, userForm.prefered_lang);

        MySpace.index();
    }

    public static void login() {
        render();
    }

    public static void login_validate() {
        String email = params.get("email");
        String password = params.get("password");

        String queryByEmail = "email";

        List<User> users = (List) User.findBy(queryByEmail, email);
        User loadedUser = null;


        Logger.error("email is <" + email + ">");
        Logger.error("password is <" + password + ">");


        if (users.isEmpty() || users.size() > 1) {


            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request


            login();
        } else {
            loadedUser = users.get(0);
            if (validate_member(loadedUser, password)) {
                session.put(MyConstants.HAS_LOGINED, MyConstants.YES);
                session.put(MyConstants.LOGINED_USER_ID, loadedUser.getId());
                session.put(MyConstants.REGIST_JUST_A_SECOND_AGO_FLAG, MyConstants.NO);

                MySpace.index();
            } else {

                Backyard.index();
            // login();
            }
        }
    }

    public static void logout() {
        session.clear();
        render();
    }

    private static boolean validate_member(User loadedUser, String password) {
        Logger.error(loadedUser.password);
        if (StringUtils.equals(loadedUser.password, password)) {
            return true;
        } else {
            return false;
        }
    }
}
