/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.myspace.user;

import play.data.validation.Required;

public class LoginForm {

    public static final String EMAIL = "login_form.email";
    @Required(message="请输入注册时填写的Email和密码！")
    public String email;
    @Required(message="请输入密码!")
    public String password;
}
