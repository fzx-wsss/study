package com.wsss.algorithm.hot.spot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RequestRecord implements Serializable {
	private static final long serialVersionUID = 1513548229931023833L;

	/** 主键CODE */
	private String code;

	/** 版本号 */
	private Integer optimistic;

	/** 创建日期 */
	private Date createDate;

	/** 用户编号 */
	private String userCode;

	/**
	 * 用户角色
	 * '00'-商户
	 * '01'-服务商
	 * '02'-合伙人
	 */
	private String userRole;

	/** 系统编号 */
	private String sysCode;

	/**
	 * 产品编号
	 */
	private String productCode;
	/**
	 * 产品类型
	 * 相同类型的产品拥有共有限额
	 * 00:秒到产品
	 * 01:增值产品
	 * 02:结算产品
	 */
	private String productType;

	/**
	 * 业务类型
	 * 辅助查询明细产品类型
	 */
	private String businessCode;

	/** 请求流水号 */
	private String requestId;

	/** 交易订单号 */
	private String transOrder;

	/**
	 * 请求日期
	 * 记录业务系统的请求发起日期
	 */
	private Date requestDate;

	/**
	 * 交易类型
	 * '00'-正常
	 * '01'-撤销
	 */
	private String transType;

	/** 交易金额 */
	private BigDecimal transAmt;

	/** 备注 */
	private String remark;

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public Integer getOptimistic() {
		return optimistic;
	}

	public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode == null ? null : userCode.trim();
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode == null ? null : sysCode.trim();
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode == null ? null : productCode.trim();
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode == null ? null : businessCode.trim();
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId == null ? null : requestId.trim();
	}

	public String getTransOrder() {
		return transOrder;
	}

	public void setTransOrder(String transOrder) {
		this.transOrder = transOrder == null ? null : transOrder.trim();
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * @return the transType
	 */
	public String getTransType() {
		return transType;
	}

	/**
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestRecord [code=" + code + ", optimistic=" + optimistic + ", createDate=" + createDate + ", userCode=" + userCode + ", userRole=" + userRole
				+ ", sysCode=" + sysCode + ", productCode=" + productCode + ", productType=" + productType + ", businessCode=" + businessCode + ", requestId="
				+ requestId + ", transOrder=" + transOrder + ", requestDate=" + requestDate + ", transType=" + transType + ", transAmt=" + transAmt + ", remark="
				+ remark + "]";
	}

}