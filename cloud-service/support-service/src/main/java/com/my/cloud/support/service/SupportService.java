package com.my.cloud.support.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.cloud.support.entity.SupportEntity;
import com.my.cloud.utils.PageUtils;

public interface SupportService extends IService<SupportEntity> {

	public PageUtils queryPage(Map<String, Object> params) throws Exception;
}
