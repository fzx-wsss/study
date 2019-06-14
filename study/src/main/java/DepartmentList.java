
import java.util.List;

public class DepartmentList extends DingTalkCommonResp {
	private List<Department> department;

	public List<Department> getDepartment() {
		return department;
	}

	public void setDepartment(List<Department> department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "DepartmentList [department=" + department + "]";
	}

}
