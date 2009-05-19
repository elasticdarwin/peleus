package application;

import forms.myspace.ShareSessionForm;
import forms.myspace.user.UserForm;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import models.Department;
import models.ShareSession;
import models.User;
import org.junit.*;
import play.test.ApplicationTest;

public class LoadFixtures extends ApplicationTest {

    @Before
    public void cleanup() {

        ShareSession.deleteAllWithDenpendencies();

        Department.deleteAll();

        User.deleteAll();

    }

    @Test
    public void load_fixtures() {

        load_departements();
        load_users();

        load_share_sessions();
    }

    private void load_share_sessions() {
        ShareSessionForm rubySession = new ShareSessionForm();

        rubySession.address = "Coffee Room @ 7floor";
        rubySession.audiences = "Ruby Fans";
        rubySession.audiences_limit = 100;
        rubySession.department_id = Department.findOneBy("name", "cpm").id;
        rubySession.description = "This is an advanced ruby topic on how to make full use of the meta programming paradigm of ruby .";
        try {
            rubySession.start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-07-01 09:30:00");
            rubySession.end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-07-01 11:30:00");

        } catch (ParseException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        rubySession.subject = "Ruby Meta Programming";
        rubySession.user_1_id = User.findOneBy("name", "Arthur").id;

        rubySession.user_2_id = User.findOneBy("name", "Darwin").id;


        ShareSession.create(rubySession);

        ShareSessionForm designSession = new ShareSessionForm();

        designSession.address = "T2-T3";
        designSession.audiences = "Java Design Pattern Fans";
        designSession.audiences_limit = 200;
        designSession.department_id = Department.findOneBy("name", "cpc").id;
        designSession.description = "This is an advanced design topic on how to apply Gof design patterns with java programming.";
        try {
            designSession.start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-08-01 19:30:00");
            designSession.end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-08-01 21:30:00");
        } catch (ParseException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        designSession.subject = "Gof Design Patterns with Java";
        designSession.user_1_id = User.findOneBy("name", "Darwin").id;
        designSession.user_2_id = User.findOneBy("name", "Arthur").id;

        ShareSession.create(designSession);

        assertEquals(new Long(2), ShareSession.count());

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

