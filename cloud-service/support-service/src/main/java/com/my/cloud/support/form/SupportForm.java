/**
 * 
 */
package com.my.cloud.support.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author YCKJ1148
 *
 */
@Data
public class SupportForm {

	private String supportId;
	
	@NotBlank(message="内容不能为空")
	private String content;
}
