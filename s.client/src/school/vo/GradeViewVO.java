package school.vo;

public class GradeViewVO {
	private String stuName;
	private String stuID;
	private String lecYear;
	private String lecDiv;
	private String stuGrade;
	private String lecName;
	private String profName;
	private String lecComplete;
	private String lecGrade;
	
	public GradeViewVO() {}

	public GradeViewVO(String stuName, String stuID, String lecYear, String lecDiv, String stuGrade, String lecName,
			String profName, String lecComplete, String lecGrade) {
		super();
		this.stuName = stuName;
		this.stuID = stuID;
		this.lecYear = lecYear;
		this.lecDiv = lecDiv;
		this.stuGrade = stuGrade;
		this.lecName = lecName;
		this.profName = profName;
		this.lecComplete = lecComplete;
		this.lecGrade = lecGrade;
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

	public String getLecYear() {
		return lecYear;
	}

	public void setLecYear(String lecYear) {
		this.lecYear = lecYear;
	}

	public String getLecDiv() {
		return lecDiv;
	}

	public void setLecDiv(String lecDiv) {
		this.lecDiv = lecDiv;
	}

	public String getStuGrade() {
		return stuGrade;
	}

	public void setStuGrade(String stuGrade) {
		this.stuGrade = stuGrade;
	}

	public String getLecName() {
		return lecName;
	}

	public void setLecName(String lecName) {
		this.lecName = lecName;
	}

	public String getProfName() {
		return profName;
	}

	public void setProfName(String profName) {
		this.profName = profName;
	}

	public String getLecComplete() {
		return lecComplete;
	}

	public void setLecComplete(String lecComplete) {
		this.lecComplete = lecComplete;
	}

	public String getLecGrade() {
		return lecGrade;
	}

	public void setLecGrade(String lecGrade) {
		this.lecGrade = lecGrade;
	}

	@Override
	public String toString() {
		return "GradeViewVO [stuName=" + stuName + ", stuID=" + stuID + ", lecYear=" + lecYear + ", lecDiv=" + lecDiv
				+ ", stuGrade=" + stuGrade + ", lecName=" + lecName + ", profName=" + profName + ", lecComplete="
				+ lecComplete + ", lecGrade=" + lecGrade + "]";
	}
	
	
}
