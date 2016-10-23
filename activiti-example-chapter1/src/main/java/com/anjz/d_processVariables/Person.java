package com.anjz.d_processVariables;

import java.io.Serializable;

/**
 * @author shuai.ding
 * @date 2016年10月22日下午4:33:11
 */
public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -254249058041823882L;
	private Integer id;//编号
	private String name;//姓名
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
