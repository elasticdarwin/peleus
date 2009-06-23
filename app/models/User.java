package models;

import forms.myspace.user.LoginForm;
import forms.myspace.user.UserForm;
import java.util.List;
import javax.persistence.*;
import org.apache.commons.lang.StringUtils;
import play.data.validation.Validation;
import play.db.jpa.JPAModel;

@Entity
@Table(name = "users",
uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends JPAModel {

    public String name;
    public String email;
    public String password;
    @ManyToOne
    @JoinColumn(name = "department_id")
    public Department department;

    public static User update_user(UserForm user_form, Validation validation) {
        User user = build(user_form);

        user.validate(validation);

        if (validation.hasErrors()) {
            return null;
        }
        user.merge();
        return user;
    }

    public User() {
    }

    public static User build(UserForm userForm) {
        User user = new User();
        user.id = userForm.id;

        user.name = userForm.name;
        user.email = userForm.email;
        user.password = userForm.password;

        user.department = Department.findById(userForm.department_id);

        return user;
    }

    public static User create(UserForm userForm) {
        return create(userForm, null);
    }

    public static User create(UserForm userForm, Validation validation) {
        User user = build(userForm);

        if (validation != null) {
            user.validate(validation);
            if (validation.hasErrors()) {
                return null;
            }
        }

        user.save();
        return user;
    }

    public static User login(String email, String password, Validation validation) {

        List<User> foundUsers = findBy("email", email);

        if (foundUsers.size() != 1 || !check_password(foundUsers.get(0), password)) {

            validation.addError(LoginForm.EMAIL,"没有用户和此Email关联，或密码不匹配，请重试！");
            return null;
        }

        return foundUsers.get(0);
    }

    private static boolean check_password(User user, String password) {
        return StringUtils.equals(user.password, password);
    }

    private void validate(Validation validation) {

        validate_email(validation);
    }

    private boolean isCreateMode() {
        return id == null;
    }

    private void validate_email(Validation validation) {
        List<User> found_users = findBy("email", email);

        if (found_users.size() == 0) {
            return;
        }

        if (isCreateMode()) {
            validation.addError(UserForm.EMAIL, "此Email已用于注册");
            return;
        }

        if (found_users.get(0).id.longValue() == id.longValue()) {
            return;
        }

        validation.addError(UserForm.EMAIL, "此Email已用于注册");

    }
}
