package controllers.backyard;

import controllers.Application;
import forms.backyard.department.DepartmentForm;
import java.util.List;
import models.Department;
import play.data.validation.Valid;

public class DepartmentController extends Application {

    public static void index() {

        List<Department> all_departments = Department.findAll();

        render(all_departments);
    }

    public static void create_form() {

        render();
    }

    public static void show(int id) {

        render();
    }

    public static void edit(int id) {

        render();
    }

    public static void delete_confirm(int id) {

        render();
    }

    public static void create(@Valid DepartmentForm department_form) {

        Department department = Department.create(department_form);

        if (department == null) {
            params.flash();
            validation.keep();
            create_form();
        }

        //create_success();

        renderText("Success!");
    }
}
