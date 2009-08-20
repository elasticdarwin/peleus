package models;

import forms.backyard.department.DepartmentForm;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import play.data.validation.Validation;
import play.db.jpa.Model;
import play.i18n.Messages;

@Entity
@Table(name = "departments",
uniqueConstraints = {@UniqueConstraint(columnNames = {"description"})})
public class Department extends Model {

    public String name;
    public String description;

    public static Department build(DepartmentForm department_form) {
        Department department = new Department();
        department.name = department_form.name;
        department.description = department_form.description;
        return department;
    }

    public static Department create(DepartmentForm department_form, Validation validation) {
        Department department = build(department_form);

        department.validate(validation);
        if (validation.hasErrors()) {
            return null;
        }

        department.save();
        return department;
    }

    public Department(){
        
    }

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private void validate(Validation validation) {

        validate_name(validation);
    }

    public boolean validate_name(Validation validation) {
        List<Department> found_departments = findBy("name", name);

        if (found_departments.size() == 0) {
            return true;
        }

        validation.addError(DepartmentForm.NAME, Messages.get("validation.backyard.department.name_existed"));

        return false;
    }
}
