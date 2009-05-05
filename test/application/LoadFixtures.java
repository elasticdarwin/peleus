package application;

import forms.myspace.user.UserForm;
import models.Department;
import models.User;
import org.junit.*;
import play.test.ApplicationTest;

public class LoadFixtures extends ApplicationTest {

    @Before
    public void cleanup() {
        User.deleteAll();
        Department.deleteAll();
    }

    @Test
    public void load_fixtures() {

        load_departements();
        load_users();
    }

    private void load_users() {

        UserForm arthurForm = new UserForm();
        arthurForm.name = "Arthur";
        arthurForm.email = "arthur.zhangxz@alibaba-inc.com";
        arthurForm.password = "arthur";
        arthurForm.repeat_password = "arthur";
        arthurForm.prefered_lang = "en";
        arthurForm.department_id = Department.findBy("name", "cpm").get(0).id;


        UserForm darwinForm = new UserForm();
        darwinForm.name = "Darwin";
        darwinForm.email = "darwin.liz@alibaba-inc.com";
        darwinForm.password = "darwin";
        darwinForm.repeat_password = "darwin";
        darwinForm.prefered_lang = "en";
        darwinForm.department_id = Department.findBy("name", "cpc").get(0).id;

        User.create(arthurForm);
        User.create(darwinForm);


        assertEquals(new Long(2), User.count());
    }

    private void load_departements() {
        Department dep1 = new Department("cpm", "myteam");
        Department dep2 = new Department("cpc", "your team");
        dep1.save();
        dep2.save();
        assertEquals(new Long(2), Department.count());
    }
//  @Test
//  public void indexTest() {
//    // make a request on the application (embedded)
//    Response response = GET("/");
//    // check the HTTP response status is OK
//    assertStatus(200, response);
//    // check the response is HTML
//    assertContentType("text/html", response);
//    // check the declared charset encoding
//    assertCharset("utf-8", response);
//    // check some content in the page. may also test a regexp
//    assertContentMatch("<h1>It works !</h1>", response);
//  }
}

