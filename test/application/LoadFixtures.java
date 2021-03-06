package application;

import forms.myspace.ShareSessionForm;
import forms.myspace.user.UserForm;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import models.Contribution;
import models.Department;
import models.ShareSession;
import models.Attachment;
import models.User;
import org.junit.*;
import play.test.FunctionalTest;


public class LoadFixtures extends FunctionalTest {

    @Before
    public void cleanup() {

        Contribution.deleteAll();

        Attachment.deleteAll();

        ShareSession.deleteAll();

        User.deleteAll();

        Department.deleteAll();

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
        rubySession.department_id = ((Department)Department.findOneBy("name", "cpm")).id;
        rubySession.description = "This is an advanced ruby topic on how to make full use of the meta programming paradigm of ruby .";
        try {
            rubySession.start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-07-01 09:30:00");
            rubySession.end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-07-01 11:30:00");

        } catch (ParseException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        rubySession.subject = "Ruby Meta Programming";
        User arthur = User.findOneBy("name", "Arthur");
        User darwin = User.findOneBy("name", "Darwin");
        
        rubySession.contributor_1_id = arthur.id;
        rubySession.contributor_2_id = darwin.id;



        ShareSession.create(rubySession,arthur);

        ShareSessionForm designSession = new ShareSessionForm();

        designSession.address = "T2-T3";
        designSession.audiences = "Java Design Pattern Fans";
        designSession.audiences_limit = 200;
        designSession.department_id = ((Department)Department.findOneBy("name", "cpc")).id;
        designSession.description = "This is an advanced design topic on how to apply Gof design patterns with java programming.";
        try {
            designSession.start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-08-01 19:30:00");
            designSession.end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-08-01 21:30:00");
        } catch (ParseException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        designSession.subject = "Gof Design Patterns with Java";
        designSession.contributor_1_id = ((User)User.findOneBy("name", "Darwin")).id;
        designSession.contributor_2_id = ((User)User.findOneBy("name", "Arthur")).id;

        ShareSession.create(designSession,darwin);

        assertEquals(2L, ShareSession.count());

    }

    private void load_users() {

        UserForm arthurForm = new UserForm();
        arthurForm.name = "Arthur";
        arthurForm.email = "a@b.cn";
        arthurForm.password = "arthur";
        arthurForm.repeat_password = "arthur";
        arthurForm.department_id = ((Department)Department.findOneBy("name", "cpm")).id;


        UserForm darwinForm = new UserForm();
        darwinForm.name = "Darwin";
        darwinForm.email = "d@a.cn";
        darwinForm.password = "darwin";
        darwinForm.repeat_password = "darwin";
        darwinForm.department_id = ((Department)Department.findOneBy("name", "cpc")).id;

        User.create(arthurForm);
        User.create(darwinForm);


        assertEquals(2L, User.count());
    }

    private void load_departements() {
        Department dep1 = new Department("cpm", "myteam");
        Department dep2 = new Department("cpc", "your team");
        dep1.save();
        dep2.save();
        assertEquals(2L, Department.count());
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

