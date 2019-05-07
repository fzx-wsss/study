package com.wsss.algorithm.hot.spot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TotalLimitFlow implements Serializable {
	private static final long serialVersionUID = -3720479033004210338L;

	/** 主键CODE */
	private String code;

	/** 版本号 */
	private Integer optimistic;

	/** 创建日期 */
	private Date createDate;

	/** 日使用流量 */
	private BigDecimal dayFlow;

	/** 月使用流量 */
	private BigDecimal monthFlow;

	/**
	 * 总限额CODE
	 * 参照表：TOTAL_LIMIT主键
	 */
	private String limitCode;

	/** 备注 */
	private String remark;

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

	public BigDecimal getDayFlow() {
		return dayFlow;
	}

	public void setDayFlow(BigDecimal dayFlow) {
		this.dayFlow = dayFlow;
	}

	public BigDecimal getMonthFlow() {
		return monthFlow;
	}

	public void setMonthFlow(BigDecimal monthFlow) {
		this.monthFlow = monthFlow;
	}

	public String getLimitCode() {
		return limitCode;
	}

	public void setLimitCode(String limitCode) {
		this.limitCode = limitCode == null ? null : limitCode.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}