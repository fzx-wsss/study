package com.unionpay.financial.model;

import java.math.BigDecimal;
import java.util.Date;

public class limitRequest {
    /** 主键 */
    private String id;

    private Integer optimistic;

    /** 请求号 */
    private String requestId;

    private String bankInstitutionCode;

    /** 商户编号 */
    private String customerId;

    /** 版本号 */
    private String version;

    /** 额度 */
    private BigDecimal transAmount;

    /** 请求日期 */
    private Date transDate;

    /** 额度类型 */
    private String limitType;

    /** 交易方式 */
    private String tradeType;

    /** 资金类型 */
    private String capitalType;

    /** 交易时间 */
    private Date requestTime;

    /** 请求方保留域 */
    private String reqReserved;

    /** 3des密钥 */
    private String desKey;

    /** 创建时间 */
    private Date createTime;

    /** 状态 */
    private String status;

    /** 返回码 */
    private String respCode;

    /** 返回信息 */
    private String respMsg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getOptimistic() {
        return optimistic;
    }

    public void setOptimistic(Integer optimistic) {
        this.optimistic = optimistic;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId == null ? null : requestId.trim();
    }

    public String getBankInstitutionCode() {
        return bankInstitutionCode;
    }

    public void setBankInstitutionCode(String bankInstitutionCode) {
        this.bankInstitutionCode = bankInstitutionCode == null ? null : bankInstitutionCode.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType == null ? null : limitType.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    public String getCapitalType() {
        return capitalType;
    }

    public void setCapitalType(String capitalType) {
        this.capitalType = capitalType == null ? null : capitalType.trim();
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved == null ? null : reqReserved.trim();
    }

    public String getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey == null ? null : desKey.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode == null ? null : respCode.trim();
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg == null ? null : respMsg.trim();
    }
}