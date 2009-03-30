package forms.myspace.user;

import play.data.validation.*;

public class RegisterForm {

    public static final String EMAIL  = "register_form.email";
    
    @Required
    @MaxSize(20)
    @MinSize(2)
    public String name;
    @Required
    @MaxSize(20)
    @MinSize(6)
    @Email(message = "validation.email.invalid")
    public String email;
    @Required
    @MaxSize(20)
    @MinSize(6)
    public String password;
    @Required
    @Equals(value = "password")
    @MaxSize(20)
    @MinSize(6)
    public String repeat_password;
    @Required
    public String prefered_lang;
}
