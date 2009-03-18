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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    private User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User create_user(String name, String email, String password) {

        //TODO add validate password repeated
        //TODO add password md5 encrypt
        User user = new User(null, name, email, password);
        user.save();
        return user;
    }

    public static User udpate_user(User user) {
        user.merge();
        return user;
    }
}
