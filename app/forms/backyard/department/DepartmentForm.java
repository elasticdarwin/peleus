package forms.backyard.department;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

public class DepartmentForm {


    public final static String NAME = "department_form.name";

    @Required
    @MaxSize(32)
    @MinSize(3)
    public String name;
    @MaxSize(1024)
    @MinSize(8)
    public String description;

    public Long id;
}
