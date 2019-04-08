
package com.wsss.basic.test.model;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * 体检中心 
 * 
 * @author demo
 * @version 1.0
 * @since 1.0
 */

public class CenterInfo implements java.io.Serializable{
		
	private static final long serialVersionUID = 1L;
	
	/** 体检中心编号*/
	private java.lang.String centerCode;
	
	/** 体检中心名字*/
	private java.lang.String name;
	
	/** 创建时间*/
	@JsonIgnore
	private java.util.Date createTime;
	
	/** 管理员姓名*/
	private java.lang.String admin;
	
	/** 所属医院*/
	private java.lang.String hospital;
	
	/** 登录密码*/
	@JsonIgnore
	private java.lang.String password;
	
	/** 备注*/
	private java.lang.String remark;
	
	/** 创建人*/
	private java.lang.String creater;
	
	/** logo*/
	private java.lang.String logo;
	
	/** 医院等级*/
	private java.lang.String hospitalLevel;
	
	private java.lang.String servicePhone;
	
	private java.lang.String serviceTime;
	

	public java.lang.String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(java.lang.String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public java.lang.String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(java.lang.String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public void setCenterCode(java.lang.String value) {
		this.centerCode = value;
	}
	
	public java.lang.String getCenterCode() {
		return this.centerCode;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.lang.String getAdmin() {
		return this.admin;
	}
	
	public void setAdmin(java.lang.String value) {
		this.admin = value;
	}
	
	public java.lang.String getHospital() {
		return this.hospital;
	}
	
	public void setHospital(java.lang.String value) {
		this.hospital = value;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}
	
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	public java.lang.String getCreater() {
		return this.creater;
	}
	
	public void setCreater(java.lang.String value) {
		this.creater = value;
	}
	
	public java.lang.String getLogo() {
		return this.logo;
	}
	
	public void setLogo(java.lang.String value) {
		this.logo = value;
	}
	
	public java.lang.String getHospitalLevel() {
		return this.hospitalLevel;
	}
	
	public void setHospitalLevel(java.lang.String value) {
		this.hospitalLevel = value;
	}
	
	
	public String toString() {
		return new StringBuilder()
			.append("\r\nCenterCode:" + getCenterCode())
			.append("\r\nName:" + getName())
			.append("\r\nCreateTime:" + getCreateTime())
			.append("\r\nAdmin:" + getAdmin())
			.append("\r\nHospital:" + getHospital())
			.append("\r\nPassword:" + getPassword())
			.append("\r\nRemark:" + getRemark())
			.append("\r\nCreater:" + getCreater())
			.append("\r\nLogo:" + getLogo())
			.append("\r\nHospitalLevel:" + getHospitalLevel())
			.toString();
	}
}

