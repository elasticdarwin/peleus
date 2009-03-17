/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.myspace.user;

import constants.MyConstants;
import controllers.Application;
import controllers.myspace.MySpace;
import forms.UserForm;
import java.util.List;
import models.User;
import play.data.validation.Error;
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

        session.put(MyConstants.LOGINED_USER_ID, user.getId());
        session.put(MyConstants.REGIST_JUST_A_SECOND_AGO_FLAG, MyConstants.YES);
        session.put(MyConstants.PREFERED_LANG, userForm.prefered_lang);

        MySpace.index();
    }
}
