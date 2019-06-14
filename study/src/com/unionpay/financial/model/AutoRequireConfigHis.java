package com.unionpay.financial.model;

import java.math.BigDecimal;
import java.util.Date;

public class AutoRequireConfigHis {
    private String id;

    private String configId;

    private Integer optimistic;

    /** 商户号 */
    private String customerId;

    /** 交易方式 */
    private String transMode;

    /** 资金类型 */
    private String capitalType;

    /** 状态，是否启用 */
    private String status;

    /** 类型 */
    private String type;

    /** 阈值 */
    private BigDecimal threshold;

    /** 追加 */
    private BigDecimal addTo;

    /** 备注 */
    private String remark;

    /** 最后更新时间 */
    private Date lastUpdateTime;

    /** 创建时间 */
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId == null ? null : configId.trim();
    }

    public Integer getOptimistic() {
        return optimistic;
    }

    public void setOptimistic(Integer optimistic) {
        this.optimistic = optimistic;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getTransMode() {
        return transMode;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode == null ? null : transMode.trim();
    }

    public String getCapitalType() {
        return capitalType;
    }

    public void setCapitalType(String capitalType) {
        this.capitalType = capitalType == null ? null : capitalType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    public BigDecimal getAddTo() {
        return addTo;
    }

    public void setAddTo(BigDecimal addTo) {
        this.addTo = addTo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}