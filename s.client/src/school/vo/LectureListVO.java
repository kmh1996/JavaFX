package school.vo;

public class LectureListVO {
	
	private String lecGrade;
	private String lecCode;
	private String lecture;
	private String divide;
	private String lecProfessor;
	private String lecCount;
	private String lecTime;
	private String lecClass;
	private String lecScore;
	
	
	public LectureListVO() {
		
	}
	
	public LectureListVO(String lecGrade, String lecCode, String lecture, String divide, String lecProfessor,
			String lecCount, String lecTime, String lecClass, String lecScore) {
		super();
		this.lecGrade = lecGrade;
		this.lecCode = lecCode;
		this.lecture = lecture;
		this.divide = divide;
		this.lecProfessor = lecProfessor;
		this.lecCount = lecCount;
		this.lecTime = lecTime;
		this.lecClass = lecClass;
		this.lecScore = lecScore;
	}
	public String getLecGrade() {
		return lecGrade;
	}
	public void setLecGrade(String lecGrade) {
		this.lecGrade = lecGrade;
	}
	public String getLecCode() {
		return lecCode;
	}
	public void setLecCode(String lecCode) {
		this.lecCode = lecCode;
	}
	public String getLecture() {
		return lecture;
	}
	public void setLecture(String lecture) {
		this.lecture = lecture;
	}
	public String getDivide() {
		return divide;
	}
	public void setDivide(String divide) {
		this.divide = divide;
	}
	public String getLecProfessor() {
		return lecProfessor;
	}
	public void setLecProfessor(String lecProfessor) {
		this.lecProfessor = lecProfessor;
	}
	public String getLecCount() {
		return lecCount;
	}
	public void setLecCount(String lecCount) {
		this.lecCount = lecCount;
	}
	public String getLecTime() {
		return lecTime;
	}
	public void setLecTime(String lecTime) {
		this.lecTime = lecTime;
	}
	public String getLecClass() {
		return lecClass;
	}
	public void setLecClass(String lecClass) {
		this.lecClass = lecClass;
	}
	public String getLecScore() {
		return lecScore;
	}
	public void setLecScore(String lecScore) {
		this.lecScore = lecScore;
	}
	@Override
	public String toString() {
		return "LectureListVO [lecGrade=" + lecGrade + ", lecCode=" + lecCode + ", lecture=" + lecture + ", divide="
				+ divide + ", lecProfessor=" + lecProfessor + ", lecCount=" + lecCount + ", lecTime=" + lecTime
				+ ", lecClass=" + lecClass + ", lecScore=" + lecScore + "]";
	}
	
	
}
