package school.vo;

public class GradesListVO {
	
	private String gradeName;
	private String gradeId;
	private int gradeYear;
	private String division;
	private int gradeGrade;
	private String gradeLecture;
	private String gradeProfessor;
	private int gradecreditsCom;
	private String gradeGrades;
	
	public GradesListVO() {
		
	}

	
	public GradesListVO(String gradeName, String gradeId, int gradeYear, String division, int gradeGrade,
			String gradeLecture, String gradeProfessor, int gradecreditsCom, String gradeGrades) {
		super();
		this.gradeName = gradeName;
		this.gradeId = gradeId;
		this.gradeYear = gradeYear;
		this.division = division;
		this.gradeGrade = gradeGrade;
		this.gradeLecture = gradeLecture;
		this.gradeProfessor = gradeProfessor;
		this.gradecreditsCom = gradecreditsCom;
		this.gradeGrades = gradeGrades;
	}

	
	
	public String getGradeName() {
		return gradeName;
	}


	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}


	public String getGradeId() {
		return gradeId;
	}


	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}


	public int getGradeYear() {
		return gradeYear;
	}


	public void setGradeYear(int gradeYear) {
		this.gradeYear = gradeYear;
	}


	public String getDivision() {
		return division;
	}


	public void setDivision(String division) {
		this.division = division;
	}


	public int getGradeGrade() {
		return gradeGrade;
	}


	public void setGradeGrade(int gradeGrade) {
		this.gradeGrade = gradeGrade;
	}


	public String getGradeLecture() {
		return gradeLecture;
	}


	public void setGradeLecture(String gradeLecture) {
		this.gradeLecture = gradeLecture;
	}


	public String getGradeProfessor() {
		return gradeProfessor;
	}


	public void setGradeProfessor(String gradeProfessor) {
		this.gradeProfessor = gradeProfessor;
	}


	public int getGradecreditsCom() {
		return gradecreditsCom;
	}


	public void setGradecreditsCom(int gradecreditsCom) {
		this.gradecreditsCom = gradecreditsCom;
	}


	public String getGradeGrades() {
		return gradeGrades;
	}


	public void setGradeGrades(String gradeGrades) {
		this.gradeGrades = gradeGrades;
	}


	@Override
	public String toString() {
		return "GradesListVO [gradeName=" + gradeName + ", gradeId=" + gradeId + ", gradeYear=" + gradeYear
				+ ", division=" + division + ", gradeGrade=" + gradeGrade + ", gradeLecture=" + gradeLecture
				+ ", gradeProfessor=" + gradeProfessor + ", gradecreditsCom=" + gradecreditsCom + ", gradeGrades="
				+ gradeGrades + "]";
	}
	
	
}
