package com.my.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.cloud.user.entity.UserEntity;

@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}
