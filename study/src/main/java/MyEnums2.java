
public enum MyEnums2 {
	EE("1"),AA("2");
	
	private String number;
	private MyEnums2(String number) {
		this.number = number;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
