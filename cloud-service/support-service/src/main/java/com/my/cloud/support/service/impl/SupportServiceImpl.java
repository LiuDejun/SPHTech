/**
 * 
 */
package com.my.cloud.support.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.cloud.support.dao.SupportDao;
import com.my.cloud.support.entity.SupportEntity;
import com.my.cloud.support.service.SupportService;
import com.my.cloud.utils.PageUtils;
import com.my.cloud.utils.QueryUtils;

/**
 * @author YCKJ1148
 *
 */
@Service("supportService")
public class SupportServiceImpl  extends ServiceImpl<SupportDao, SupportEntity> implements SupportService  {

	@Override
	public PageUtils queryPage(Map<String, Object> params) throws Exception {
		QueryWrapper<SupportEntity> queryWrapper = new QueryWrapper<SupportEntity>();
    	
    	if(params.containsKey("content")) {
    		String content = params.get("content")==null?"":params.get("content").toString();
    		queryWrapper.like(StringUtils.isNotBlank(content),"content", content);
    	}
    	
    	if(params.containsKey("beginTime")) {
    		String beginTime = params.get("begin_time")==null?"":params.get("begin_time").toString();
    		queryWrapper.ge(StringUtils.isNotBlank(beginTime),"begin_time", beginTime);
    	}
    	
    	if(params.containsKey("endTime")) {
    		String endTime = params.get("endTime")==null?"":params.get("endTime").toString();
    		queryWrapper.le(StringUtils.isNotBlank(endTime),"end_time", endTime);
    	}
    	
    	queryWrapper.orderByDesc("create_time");
    	
    	IPage<SupportEntity> page = baseMapper.selectPage(
    			new QueryUtils<SupportEntity>().getPage(params), 
    			queryWrapper
    	);
    	
    	return new PageUtils(page);
	}

}
