package com.my.cloud.user.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class RegisterForm {
    @NotBlank(message="手机号不能为空")
    private String mobile;

    @NotBlank(message="密码不能为空")
    private String password;

}
