/*
 * Powered By [uwin-framework]
 * Web Site: http://www.uwin.cc
 * Since 2013 - 2014
 */





/**
 * 导入人员信息 
 * 
 * @author demo
 * @version 1.0
 * @since 1.0
 */

public class UwinAllusers implements java.io.Serializable{
		
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		System.out.println(Math.ceil(1.5));
		
	}
	/** 人员ID*/
	private java.lang.Long userid;
	
	/** 人员ID*/
	private java.lang.Long sysuserId;
	
	/** 项目ID*/
	private java.lang.Long projectid;
	
	/** 名称*/
	private java.lang.String name;
	
	/** 性别*/
	private java.lang.String sex;
	
	/** 手机*/
	private java.lang.String phone;
	
	/** 邮箱*/
	private java.lang.String email;
	
	/** 地址*/
	private java.lang.String address;
	
	/** 公司*/
	private java.lang.String company;
	
	/** 部门*/
	private java.lang.String department;
	
	/** 职位*/
	private java.lang.String position;
	
	/** 头像*/
	private java.lang.String headimg;
	
	/** 头像style*/
	private java.lang.String imgstyle;
	
	/** 时间*/
	private java.util.Date createtime;
	
	/** appid*/
	private java.lang.Long appid;
	
	/** 是否签到  */
	private java.lang.String isSign;
	/** 签到时间  */
	private java.util.Date signTime;
	/** 是否是出场*/
	private java.lang.String signintype;
	/** 签到地点ID*/
	private java.lang.Long signinpointid;
	/** 签到地点*/
	private java.lang.String signinpointname;
	
	/** 英文名*/
	private java.lang.String englishName;
	
	/** 别名（艺名、昵称）*/
	private java.lang.String aliasName;
	
	/** 国籍*/
	private java.lang.String nationality;
	
	/** 星座*/
	private java.lang.String constellation;
	
	/** 血型*/
	private java.lang.String bloodType;
	
	/** 身高(cm)*/
	private java.lang.Integer stature;
	
	/** 体重(kg)*/
	private java.lang.Double weight;
	
	/** 出生地*/
	private java.lang.String birthplace;
	
	/** 祖籍*/
	private java.lang.String ancestralHome;
	
	/** 出生日期*/
	private java.lang.String birthDate;
	
	/** 学位*/
	private java.lang.String degree;
	
	/** 职业*/
	private java.lang.String occupation;
	
	/** 毕业院校*/
	private java.lang.String graduateSchool;
	
	/** 经纪公司*/
	private java.lang.String brokerageCompany;
	
	/** 唱片公司*/
	private java.lang.String recordCompany;
	
	/** 代表作品*/
	private java.lang.String representativeWorks;
	
	/** 主要成就*/
	private java.lang.String achievement;
	
	/** 选择状态 **/
	private String selection;
	
	/** 个人简历*/
	private java.lang.String description;
	/** 行业 */
	private java.lang.String industry;
	
	/** code*/
	private java.lang.String code;
	
	
	public java.lang.Long getSysuserId() {
		return sysuserId;
	}

	public void setSysuserId(java.lang.Long sysuserId) {
		this.sysuserId = sysuserId;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getIndustry() {
		return industry;
	}

	public void setIndustry(java.lang.String industry) {
		this.industry = industry;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public java.lang.String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(java.lang.String englishName) {
		this.englishName = englishName;
	}

	public java.lang.String getAliasName() {
		return aliasName;
	}

	public void setAliasName(java.lang.String aliasName) {
		this.aliasName = aliasName;
	}

	public java.lang.String getNationality() {
		return nationality;
	}

	public void setNationality(java.lang.String nationality) {
		this.nationality = nationality;
	}

	public java.lang.String getConstellation() {
		return constellation;
	}

	public void setConstellation(java.lang.String constellation) {
		this.constellation = constellation;
	}

	public java.lang.String getSigninpointname() {
		return signinpointname;
	}

	public void setSigninpointname(java.lang.String signinpointname) {
		this.signinpointname = signinpointname;
	}

	public java.lang.String getBloodType() {
		return bloodType;
	}

	public void setBloodType(java.lang.String bloodType) {
		this.bloodType = bloodType;
	}

	public java.lang.Integer getStature() {
		return stature;
	}

	public void setStature(java.lang.Integer stature) {
		this.stature = stature;
	}

	public java.lang.Double getWeight() {
		return weight;
	}

	public void setWeight(java.lang.Double weight) {
		this.weight = weight;
	}

	public java.lang.String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(java.lang.String birthplace) {
		this.birthplace = birthplace;
	}

	public java.lang.String getAncestralHome() {
		return ancestralHome;
	}

	public void setAncestralHome(java.lang.String ancestralHome) {
		this.ancestralHome = ancestralHome;
	}

	public java.lang.String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(java.lang.String birthDate) {
		this.birthDate = birthDate;
	}

	public java.lang.String getDegree() {
		return degree;
	}

	public void setDegree(java.lang.String degree) {
		this.degree = degree;
	}

	public java.lang.String getOccupation() {
		return occupation;
	}

	public void setOccupation(java.lang.String occupation) {
		this.occupation = occupation;
	}

	public java.lang.String getGraduateSchool() {
		return graduateSchool;
	}

	public void setGraduateSchool(java.lang.String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public java.lang.String getBrokerageCompany() {
		return brokerageCompany;
	}

	public void setBrokerageCompany(java.lang.String brokerageCompany) {
		this.brokerageCompany = brokerageCompany;
	}

	public java.lang.String getRecordCompany() {
		return recordCompany;
	}

	public void setRecordCompany(java.lang.String recordCompany) {
		this.recordCompany = recordCompany;
	}

	public java.lang.String getRepresentativeWorks() {
		return representativeWorks;
	}

	public void setRepresentativeWorks(java.lang.String representativeWorks) {
		this.representativeWorks = representativeWorks;
	}

	public java.lang.String getAchievement() {
		return achievement;
	}

	public void setAchievement(java.lang.String achievement) {
		this.achievement = achievement;
	}

	public java.util.Date getSignTime() {
		return signTime;
	}

	public void setSignTime(java.util.Date signTime) {
		this.signTime = signTime;
	}

	public java.lang.String getIsSign() {
		return isSign;
	}
	
	public void setIsSign(java.lang.String isSign) {
		this.isSign = isSign;
	}
	
	public java.lang.String getIsSignExplain()
	{
		String explain = "未签到";
		if(this.isSign != null && "true".equals(this.isSign))
		{
			explain = "已签到";
		}
		return explain;
	}
	
	public java.lang.String getIsSend()
	{
		String explain = "未发送";
		if(this.isSign != null && "true".equals(this.isSign))
		{
			explain = "已发送";
		}
		return explain;
	}
	
	public void setUserid(java.lang.Long value) {
		this.userid = value;
	}
	
	public java.lang.Long getUserid() {
		return this.userid;
	}
	
	public java.lang.Long getProjectid() {
		return this.projectid;
	}
	
	public void setProjectid(java.lang.Long value) {
		this.projectid = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getSex() {
		return this.sex;
	}
	
	public void setSex(java.lang.String value) {
		this.sex = value;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
	public java.lang.String getCompany() {
		return this.company;
	}
	
	public void setCompany(java.lang.String value) {
		this.company = value;
	}
	
	public java.lang.String getDepartment() {
		return this.department;
	}
	
	public void setDepartment(java.lang.String value) {
		this.department = value;
	}
	
	public java.lang.String getPosition() {
		return this.position;
	}
	
	public void setPosition(java.lang.String value) {
		this.position = value;
	}
	
	public java.lang.String getHeadimg() {
		return this.headimg;
	}
	
	public void setHeadimg(java.lang.String value) {
		this.headimg = value;
	}
	
	public java.lang.String getImgstyle() {
		return this.imgstyle;
	}
	
	public void setImgstyle(java.lang.String value) {
		this.imgstyle = value;
	}
	
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	public void setCreatetime(java.util.Date value) {
		this.createtime = value;
	}
	
	public java.lang.Long getAppid() {
		return this.appid;
	}
	
	public void setAppid(java.lang.Long value) {
		this.appid = value;
	}
	
	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.Long getSigninpointid() {
		return signinpointid;
	}

	public void setSigninpointid(java.lang.Long signinpointid) {
		this.signinpointid = signinpointid;
	}

	public java.lang.String getSignintype() {
		return signintype;
	}

	public void setSignintype(java.lang.String signintype) {
		this.signintype = signintype;
	}

	public String toString() {
		return new StringBuilder()
		.append("\r\nUserid:" + getUserid())
		.append("\r\nProjectid:" + getProjectid())
		.append("\r\nName:" + getName())
		.append("\r\nSex:" + getSex())
		.append("\r\nPhone:" + getPhone())
		.append("\r\nEmail:" + getEmail())
		.append("\r\nAddress:" + getAddress())
		.append("\r\nCompany:" + getCompany())
		.append("\r\nDepartment:" + getDepartment())
		.append("\r\nPosition:" + getPosition())
		.append("\r\nHeadimg:" + getHeadimg())
		.append("\r\nImgstyle:" + getImgstyle())
		.append("\r\nCreatetime:" + getCreatetime())
		.append("\r\nAppid:" + getAppid())
		.append("\r\nEnglishName:" + getEnglishName())
		.append("\r\nAliasName:" + getAliasName())
		.append("\r\nNationality:" + getNationality())
		.append("\r\nConstellation:" + getConstellation())
		.append("\r\nBloodType:" + getBloodType())
		.append("\r\nStature:" + getStature())
		.append("\r\nWeight:" + getWeight())
		.append("\r\nBirthplace:" + getBirthplace())
		.append("\r\nAncestralHome:" + getAncestralHome())
		.append("\r\nBirthDate:" + getBirthDate())
		.append("\r\nDegree:" + getDegree())
		.append("\r\nOccupation:" + getOccupation())
		.append("\r\nGraduateSchool:" + getGraduateSchool())
		.append("\r\nBrokerageCompany:" + getBrokerageCompany())
		.append("\r\nRecordCompany:" + getRecordCompany())
		.append("\r\nRepresentativeWorks:" + getRepresentativeWorks())
		.append("\r\nAchievement:" + getAchievement())
		.toString();
	}
}

