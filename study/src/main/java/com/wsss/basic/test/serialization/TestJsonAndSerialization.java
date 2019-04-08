package com.wsss.basic.test.serialization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

import com.wsss.basic.util.json.JsonUtil;
import com.wsss.basic.util.serialize.ObjectSerializeUtil;

/**
 * 测试jdk自带序列化与json的性能和空间差异
 * 结果：json方式速度是序列化的3倍，但是空间使用比序列化也大了3倍
 * @author Administrator
 *
 */
public class TestJsonAndSerialization {
	public static void main(String[] args) throws IOException {
		
		File file = createFile("C://test/SerializationObject");
		File file2 = createFile("C://test/JsonObject");
		File file3 = createFile("C://test/byteArrayObject");
		
		
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		long start = System.currentTimeMillis();
		for(int i=0;i<100000;i++) {
			OrderMarster om = createOrder();
			
			oos.writeObject(om);
			oos.flush();
		}
		oos.close();fos.close();
		long end = System.currentTimeMillis();
		System.out.println("serialization:" + (end - start));
		start = end;
		
		
		FileOutputStream fos2 = new FileOutputStream(file2);
		for(int i=0;i<100000;i++) {
			OrderMarster om = createOrder();
			
			fos2.write(JsonUtil.object2Json(om).getBytes());
			fos2.flush();
		}
		fos2.close();
		end = System.currentTimeMillis();
		System.out.println("josn:"+(end - start));
		
		
		FileOutputStream fos3 = new FileOutputStream(file3);
		for(int i=0;i<100000;i++) {
			OrderMarster om = createOrder();
			
			fos3.write(ObjectSerializeUtil.getStrFromObj(om));
			fos3.flush();
		}
		fos3.close();
		end = System.currentTimeMillis();
		System.out.println("util:"+(end - start));
	}
	
	public static OrderMarster createOrder() {
		OrderMarster om = new OrderMarster();
		om.setCancelTime(new Date());
		om.setCenterCode("100001");
		om.setCenterName("中科");
		om.setDivisionId(30L);
		om.setExamId("p12423565");
		om.setExamUserName("fan");
		om.setExamUserPhone("17090050104");
		om.setVip("false");
		om.setCreateTime(new Date());
		om.setComboName("12e412e412");
		om.setCompanyCode("1234412");
		om.setFavourMoney(10D);
		om.setIdcard("58219326515641");
		om.setOrderMoney(10D);
		om.setUserName("qafqawedf");
		om.setUserId(124L);
		om.setUserDelete("false");
		om.setType("asdaf");
		om.setShopDelete("false");
		om.setRemainder(1);
		om.setRemark("124e12r1234213");
		om.setPayTime(new Date());
		om.setOrderId(1242135L);
		om.setIsExport("false");
		om.setImg("12ed12d12e2142");
		
		return om;
	}
	
	public static File createFile(String path) throws IOException {
		File file = new File(path);
		if(!file.exists()) {
			file.createNewFile();
		}
		return file;
	}
}

class OrderMarster implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 订单id*/
	private java.lang.Long orderId;
	
	/** 交易表*/
	private java.lang.Long transactionId;
	
	/** 体检中心*/
	private java.lang.String centerCode;
	
	/** 用户id*/
	private java.lang.Long userId;
	
	/** 订单类型*/
	private java.lang.String type;
	
	/** 金额*/
	private java.lang.Double money;
	
	private Double orderMoney;
	
	private Double favourMoney;
	
	/** 支付方式*/
	private java.lang.String payMode;
	
	/** 支付时间*/
	private java.util.Date payTime;
	
	/** 取消时间*/
	private java.util.Date cancelTime;
	
	/** 用户是否删除*/
	private java.lang.String userDelete;
	
	/** 商家是否删除*/
	private java.lang.String shopDelete;
	
	/** 订单状态*/
	private java.lang.String status;
	
	/** 备注*/
	private java.lang.String remark;
	
	/** 创建时间*/
	private java.util.Date createTime;
	
	/**所属分部id*/
	private Long divisionId;
	
	private String centerName;
	private String vip;
	
	private Date examTime;
	private String userName;
	private String phone;
	private String img;
	private String comboName;
	private String isExport;
	private Integer remainder;
	private String examId;
	
	private String examUserName;
	private String examUserPhone;
	private String idcard;
	
	private String companyName;
	private String companyCode;
	
	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getExamUserPhone() {
		return examUserPhone;
	}

	public void setExamUserPhone(String examUserPhone) {
		this.examUserPhone = examUserPhone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public Integer getRemainder() {
		return remainder;
	}

	public void setRemainder(Integer remainder) {
		this.remainder = remainder;
	}

	public String getIsExport() {
		return isExport;
	}

	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}

	public String getExamUserName() {
		return examUserName;
	}

	public void setExamUserName(String examUserName) {
		this.examUserName = examUserName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Double getFavourMoney() {
		return favourMoney;
	}

	public void setFavourMoney(Double favourMoney) {
		this.favourMoney = favourMoney;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getExamTime() {
		return examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}

	public void setOrderId(java.lang.Long value) {
		this.orderId = value;
	}
	
	public java.lang.Long getOrderId() {
		return this.orderId;
	}
	
	public java.lang.Long getTransactionId() {
		return this.transactionId;
	}
	
	public void setTransactionId(java.lang.Long value) {
		this.transactionId = value;
	}
	
	public java.lang.String getCenterCode() {
		return this.centerCode;
	}
	
	public void setCenterCode(java.lang.String value) {
		this.centerCode = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	public java.lang.Double getMoney() {
		return this.money;
	}
	
	public void setMoney(java.lang.Double value) {
		this.money = value;
	}
	
	public java.lang.String getPayMode() {
		return this.payMode;
	}
	
	public void setPayMode(java.lang.String value) {
		this.payMode = value;
	}
	
	public java.util.Date getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(java.util.Date value) {
		this.payTime = value;
	}
	
	public java.util.Date getCancelTime() {
		return this.cancelTime;
	}
	
	public void setCancelTime(java.util.Date value) {
		this.cancelTime = value;
	}
	
	public java.lang.String getUserDelete() {
		return this.userDelete;
	}
	
	public void setUserDelete(java.lang.String value) {
		this.userDelete = value;
	}
	
	public java.lang.String getShopDelete() {
		return this.shopDelete;
	}
	
	public void setShopDelete(java.lang.String value) {
		this.shopDelete = value;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	
	public String toString() {
		return new StringBuilder()
			.append("\r\nOrderId:" + getOrderId())
			.append("\r\nTransactionId:" + getTransactionId())
			.append("\r\nCenterCode:" + getCenterCode())
			.append("\r\nUserId:" + getUserId())
			.append("\r\nType:" + getType())
			.append("\r\nMoney:" + getMoney())
			.append("\r\nPayMode:" + getPayMode())
			.append("\r\nPayTime:" + getPayTime())
			.append("\r\nCancelTime:" + getCancelTime())
			.append("\r\nUserDelete:" + getUserDelete())
			.append("\r\nShopDelete:" + getShopDelete())
			.append("\r\nStatus:" + getStatus())
			.append("\r\nRemark:" + getRemark())
			.append("\r\nCreateTime:" + getCreateTime())
			.append("\r\nDivisionId:" + getDivisionId())
			.append("\r\nCenterName:" + getCenterName())
			.toString();
	}
}