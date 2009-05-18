package forms.myspace.user;

import play.data.validation.*;

public class UserForm {

    public static final String EMAIL = "user_form.email";
    public Long id;
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
    @Range(min = 1, message = "common.basic.actions.not_selected_yet")
    public Long department_id;
    @Required
    @MaxSize(20)
    @MinSize(6)
    public String password;
    @Required
    @Equals(value = "password")
    @MaxSize(20)
    @MinSize(6)
    public String repeat_password;
//    @Required
    public String prefered_lang;
}
