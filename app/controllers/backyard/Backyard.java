package controllers.backyard;

import controllers.Application;
import java.util.List;
import models.User;
import org.apache.commons.lang.math.NumberUtils;

/**
 *
 * @author zd
 */
public class Backyard extends Application {

    public static void index() {
        render();
    }

    public static void show_all_users() {
        List<User> all_users = User.findAll();
        render(all_users);
    }

    public static void edit_user(Long id) {
        User user = User.findById(id);
        render(user);
    }

    public static void delete_user_confirm(Long id) {
        User user = User.findById(id);
        render(user);
    }

    public static void delete_user(Long id) {

        User user = User.findById(id);
        user.delete();

        show_all_users();
    }

    public static void update_user() {
        Long id = NumberUtils.toLong(params.get("id"));
        String name = params.get("name");
        String email = params.get("email");
        String password = params.get("password");

        User user = new User();
        user.id = id;
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        User.udpate_user(user);
        show_all_users();
    }
}
