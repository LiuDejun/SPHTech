package com.my.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.cloud.user.entity.UserEntity;

public interface UserService extends IService<UserEntity> {

	UserEntity queryByUsername(UserEntity user);
}
