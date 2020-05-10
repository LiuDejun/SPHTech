/**
 * 
 */
package com.my.cloud.support.control;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.cloud.support.entity.SupportEntity;
import com.my.cloud.support.form.SupportForm;
import com.my.cloud.support.service.SupportService;
import com.my.cloud.utils.PageUtils;
import com.my.cloud.utils.R;

/**
 * @author YCKJ1148
 *
 */
@RestController
@RequestMapping("/support")
public class SupportController {

	@Autowired
    private SupportService supportService;
	
	@PostMapping("add")
    public R add(@RequestBody SupportForm form){
		SupportEntity support = new SupportEntity();
		String supportId = UUID.randomUUID().toString().replaceAll("-", "");
		support.setContent(form.getContent());
		support.setCreateTime(new Date());
		support.setSupportId(supportId);
		supportService.save(support);
		return R.ok().put("supportId", supportId);
	}
	
	@PostMapping("update")
    public R update(@RequestBody SupportForm form){
		SupportEntity support = new SupportEntity();
		support.setContent(form.getContent());
		support.setUpdateTime(new Date());
		support.setSupportId(form.getSupportId());
		supportService.updateById(support);
		
		return R.ok();
	}
	
	@PostMapping("delete")
    public R delete(@RequestBody Map<String, Object> param){
		String supportId = param.get("supportId") == null ? "":param.get("supportId").toString();
		supportService.removeById(supportId);
		
		return R.ok();
	}
	
	@PostMapping("page")
    public R page(@RequestBody Map<String, Object> param){
		PageUtils page = null;
		try {
			page = supportService.queryPage(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return R.error().put("50001", "分页查询异常");
		}
		return R.ok().put("data", page);
	}
}
