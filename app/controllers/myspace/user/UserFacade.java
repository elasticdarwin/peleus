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
import play.data.validation.Valid;

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

        String queryByEmail = "email= ? ";

        List<User> users = (List) User.findBy(queryByEmail, email);
        User loadedUser = null;

        if (users.isEmpty() || users.size() > 1) {
            login();
        } else {
            loadedUser = users.get(0);
            if (validate_member(loadedUser, password)) {
                System.out.println("validate pass");
                session.put(MyConstants.HAS_LOGINED, MyConstants.YES);
                session.put(MyConstants.LOGINED_USER_ID, loadedUser.getId());
                session.put(MyConstants.REGIST_JUST_A_SECOND_AGO_FLAG, MyConstants.NO);
                validate_success();
            } else {
                System.out.println("validate false");
                Backyard.index();
            // login();
            }
        }
    }

    public static void logout() {
        session.put(MyConstants.HAS_LOGINED, MyConstants.NO);
        session.put(MyConstants.LOGINED_USER_ID, "");
        session.put(MyConstants.REGIST_JUST_A_SECOND_AGO_FLAG, MyConstants.NO);
        login();
    }

    private static boolean validate_member(User loadedUser, String password) {
        /*System.out.println(loadedUser.password);
        if (StringUtils.equals(loadedUser.password, password)) {
        return true;
        } else {
        return false;
        }*/
        return true;
    }

    public static void validate_success() {
        render();
    }
}
