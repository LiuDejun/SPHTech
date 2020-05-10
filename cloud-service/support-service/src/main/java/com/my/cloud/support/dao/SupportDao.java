package com.my.cloud.support.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.cloud.support.entity.SupportEntity;

@Mapper
public interface SupportDao extends BaseMapper<SupportEntity> {

}
