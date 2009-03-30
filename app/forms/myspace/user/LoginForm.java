/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.myspace.user;

import play.data.validation.Required;

public class LoginForm {

    public static final String EMAIL = "login_form.email";
    @Required
    public String email;
    @Required
    public String password;
}
