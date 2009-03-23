package forms;

import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;


public class UserForm {

    @Required
    @MaxSize(20)
    @MinSize(2)
    public String name;
    @Required
    @MaxSize(20)
    @MinSize(6)
    @Email
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
