package models;

import forms.backyard.department.DepartmentForm;
import javax.persistence.Entity;
import javax.persistence.Table;
import play.db.jpa.JPAModel;

@Entity
@Table(name = "departments")
public class Department extends JPAModel {

    public String name;
    public String description;

    public static Department build(DepartmentForm department_form) {
        Department department = new Department();
        department.name = department_form.name;
        department.description = department_form.description;
        return department;
    }

    public static Department create(DepartmentForm department_form) {
        Department department = build(department_form);

        department.save();
        return department;
    }

}
