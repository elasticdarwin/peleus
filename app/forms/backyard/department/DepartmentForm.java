package forms.backyard.department;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

public class DepartmentForm {

    @Required
    @MaxSize(32)
    @MinSize(3)
    public String name;
    @MaxSize(1024)
    @MinSize(8)
    public String description;
}
