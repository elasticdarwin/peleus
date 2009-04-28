package models;

import forms.myspace.user.LoginForm;
import forms.myspace.user.UserForm;
import java.util.List;
import javax.persistence.*;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.data.validation.Validation;
import play.db.jpa.JPAModel;
import play.i18n.Messages;

@Entity
@Table(name = "users",
uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends JPAModel {

    public static User update_user(UserForm user_form, Validation validation) {
        User user = build(user_form);

        user.validate(validation);


        if (validation.hasErrors()) {

            return null;
        }
        user.merge();
        return user;
    }
    public String name;
    public String email;
    public String password;

    public User() {
    }

    public static User build(UserForm userForm) {
        User user = new User();
        user.id = userForm.id;

        user.name = userForm.name;
        user.email = userForm.email;
        user.password = userForm.password;

        return user;
    }

    public static User create(UserForm userForm, Validation validation) {
        User user = build(userForm);

        user.validate(validation);
        if (validation.hasErrors()) {
            return null;
        }

        user.save();
        return user;
    }

    public static User login(String email, String password, Validation validation) {

        List<User> foundUsers = findBy("email", email);

        if (foundUsers.size() != 1 || !check_password(foundUsers.get(0), password)) {

            validation.addError(LoginForm.EMAIL, Messages.get("validation.email.notexist"));
            return null;
        }

        return foundUsers.get(0);
    }

    private static boolean check_password(User user, String password) {
        return StringUtils.equals(user.password, password);
    }

    public boolean validate(Validation validation) {

        return validate_email(validation);
    }

    private boolean validate_email(Validation validation) {
        List<User> found_users = findBy("email", email);

        if (found_users.size() == 0) {
            return true;
        }


        if (found_users.get(0).id.longValue() == getId().longValue()) {
            return true;
        }

        validation.addError(UserForm.EMAIL, Messages.get("validation.email.occupied"));

        return false;
    }
}
