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

    public static void show(Long id) {

        Department department = Department.findById(id);

        redirectToLoginIfNull(department);

        render(department);
    }

    public static void update(@Valid DepartmentForm department_form) {

        Department department = Department.findById(department_form.id);

        department.name = department_form.name;
        department.description = department_form.description;


        if (department.validate_name(validation)) {
            params.flash();
            validation.keep();
            edit(department_form.id);
        }

        department.save();

        index();
    }

    public static void edit(Long id) {

        Department department = Department.findById(id);

        redirectToLoginIfNull(department);

        flash.put("department_form.name", department.name);
        flash.put("department_form.description", department.description);
        flash.put("department_form.id", department.id);
        render();
    }

    public static void delete_confirm(Long id) {

        render();
    }

    public static void create(@Valid DepartmentForm department_form) {

        Department department = Department.create(department_form, validation);

        if (department == null) {
            params.flash();
            validation.keep();
            create_form();
        }

        index();
    }
}
