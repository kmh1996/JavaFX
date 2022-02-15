package school.vo;
/*
 * 학생 정보를 저장하는 Class
 * tableView와 binding 되는  Class
 */
public class stuVO {
	private String stuName;
	private String stuID;
	private String stuDpt;
	private String stuGrade;
	private String stuBirth;
	private String stuHp;
	private String stuEmail;
	private String stuAddr;
	
	public stuVO() {}

	public stuVO(String stuName, String stuID, String stuDpt, String stuGrade, String stuBirth, String stuHp,
			String stuEmail, String stuAddr) {
		super();
		this.stuName = stuName;
		this.stuID = stuID;
		this.stuDpt = stuDpt;
		this.stuGrade = stuGrade;
		this.stuBirth = stuBirth;
		this.stuHp = stuHp;
		this.stuEmail = stuEmail;
		this.stuAddr = stuAddr;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuID() {
		return stuID;
	}

	public void setStuID(String stuID) {
		this.stuID = stuID;
	}

	public String getStuDpt() {
		return stuDpt;
	}

	public void setStuDpt(String stuDpt) {
		this.stuDpt = stuDpt;
	}

	public String getStuGrade() {
		return stuGrade;
	}

	public void setStuGrade(String stuGrade) {
		this.stuGrade = stuGrade;
	}

	public String getStuBirth() {
		return stuBirth;
	}

	public void setStuBirth(String stuBirth) {
		this.stuBirth = stuBirth;
	}

	public String getStuHp() {
		return stuHp;
	}

	public void setStuHp(String stuHp) {
		this.stuHp = stuHp;
	}

	public String getStuEmail() {
		return stuEmail;
	}

	public void setStuEmail(String stuEmail) {
		this.stuEmail = stuEmail;
	}

	public String getStuAddr() {
		return stuAddr;
	}

	public void setStuAddr(String stuAddr) {
		this.stuAddr = stuAddr;
	}

	@Override
	public String toString() {
		return "stuVO [stuName=" + stuName + ", stuID=" + stuID + ", stuDpt=" + stuDpt + ", stuGrade=" + stuGrade
				+ ", stuBirth=" + stuBirth + ", stuHp=" + stuHp + ", stuEmail=" + stuEmail + ", stuAddr=" + stuAddr
				+ "]";
	}

	
	
}
