package com.my.cloud.user.service.impl;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.cloud.user.dao.UserDao;
import com.my.cloud.user.entity.UserEntity;
import com.my.cloud.user.service.UserService;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	@Override
	public UserEntity queryByUsername(UserEntity user) {
		QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>();
		queryWrapper.eq("username", user.getUsername());
		
		UserEntity ret = baseMapper.selectOne(queryWrapper);
		return ret;
	}

}
