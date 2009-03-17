/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import forms.UserForm;
import javax.persistence.Entity;
import javax.persistence.Table;
import play.db.jpa.JPAModel;

/**
 *
 * @author darwin
 */
@Entity
@Table(name = "users")
public class User extends JPAModel {

    /**
     * 
     * @param userForm
     * @return
     */
    public static User create(UserForm userForm) {
        User user = build(userForm);
        user.save();

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
    public String name;
    public String email;
    public String password;

    public User() {
    }

    private User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User create_user(String name, String email, String password, String repeat_password) {

        //TODO add validate password repeated
        //TODO add password md5 encrypt
        User user = new User(name, email, password);
        user.save();
        return user;
    }
}
