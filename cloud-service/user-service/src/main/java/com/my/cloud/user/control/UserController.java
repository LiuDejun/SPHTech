/**
 * 
 */
package com.my.cloud.user.control;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.cloud.user.entity.UserEntity;
import com.my.cloud.user.form.RegisterForm;
import com.my.cloud.user.service.UserService;
import com.my.cloud.utils.R;

/**
 * @author YCKJ1148
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private UserService userService;

	@PostMapping("register")
    public R register(@RequestBody RegisterForm form){

        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getMobile());
        user.setPassword(DigestUtils.md5Hex(form.getPassword()));
        Date now = new Date();
        user.setCreateTime(now);
        user.setLastVisit(now);
        userService.save(user);

        return R.ok();
    }
	
	@PostMapping("query")
    public R query(@RequestBody RegisterForm form){

        UserEntity user = new UserEntity();
        user.setUsername(form.getMobile());
        UserEntity ret = userService.queryByUsername(user);

        return R.ok().put("data", ret);
    }
}
