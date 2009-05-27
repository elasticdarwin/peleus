package forms.myspace;

import java.util.Date;
import play.data.validation.*;

public class ShareSessionForm {

    @Required
    @Range(min = 1, message = "common.basic.actions.not_selected_yet")
    public Long department_id;
    @Required
    @MaxSize(128)
    @MinSize(6)
    public String subject;
    @Required
    @MaxSize(1024)
    @MinSize(6)
    public String audiences;
    @Required
    public Date start_date;
    @Required
    public Date end_date;
    @Required
    @MaxSize(512)
    @MinSize(3)
    public String address;
    @Required
    @Range(min = 1)
    public int audiences_limit;
    @Required
    @MaxSize(8192)
    @MinSize(6)
    public String description;
    @Required
    @Range(min = 1, message = "common.basic.actions.not_selected_yet")
    public Long contributor_1_id;

//    @Range(min = 1, message="common.basic.actions.not_selected_yet")
    public Long contributor_2_id;

//    @Range(min = 1, message="common.basic.actions.not_selected_yet")
    public Long contributor_3_id;

    public Long id;
}
