package forms.myspace.user;

import play.data.validation.*;

public class UserForm {

    public static final String EMAIL = "user_form.email";
    public Long id;
    @Required(message = "请输入用户名！")
    @MaxSize(value=32,message = "用户名超出限定长度！")
    @MinSize(value=2,message="用户名太短！至少要2个以上字符！")
    public String name;
    @Required(message = "请输入Email！")
    @MaxSize(value=48,message="Email超出最大长度!")
    @MinSize(value=5,message="Email太短！看起来应该不是一个正确的Email地址！")
    @Email(message = "不是正确的Email格式！")
    public String email;
    @Required(message = "请选择一个部门！")
    @Range(min = 1, message = "请选择一个部门！")
    public Long department_id;
    @Required(message = "请设置密码！")
    @MaxSize(value=20,message="密码太长，密码最大长度为20！")
    @MinSize(value=5,message="密码要求至少有5个字符！")
    public String password;
    @Required(message = "请再次输入密码！")
    @Equals(value = "password",message="两次输入的密码不一致！请重输!")
    @MaxSize(value=20,message="密码太长，密码最大长度为20！")
    @MinSize(value=5,message="密码要求至少有5个字符！")
    public String repeat_password;
}
