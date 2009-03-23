package models;

import forms.UserForm;
import java.util.List;
import javax.persistence.*;
import play.data.validation.Validation;
import play.db.jpa.JPAModel;
import play.i18n.Messages;

@Entity
@Table(name = "users",
uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends JPAModel {

    public String name;
    public String email;
    public String password;

    public User() {
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

    public static User udpate_user(User user) {
        user.merge();
        return user;
    }

    /**
     * transfer form object to model object
     * @param userForm
     * @return
     */
    public static User build(UserForm userForm) {
        User user = new User();
        user.name = userForm.name;
        user.email = userForm.email;
        user.password = userForm.password;
        return user;
    }

    public boolean validate(Validation validation) {

        return validate_email(validation);
    }

    private boolean validate_email(Validation validation) {
        List<User> found_users = findBy("email", email);

        if (found_users.size() == 0) {
            return true;
        }

        validation.addError(UserForm.EMAIL, Messages.get("validation.email.occupied"));

        return false;
    }
}
