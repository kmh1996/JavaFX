package application;
/*
 * 학생 정보를 저장하는 Class
 * tableView와 binding 되는  Class
 */
public class StudentVO {
	
	private String name;
	private int kor;
	private int math;
	private int eng;
	
	public StudentVO() {}

	public StudentVO(String name, int kor, int math, int eng) {
		this.name = name;
		this.kor = kor;
		this.math = math;
		this.eng = eng;
	}
	// 이하 getter & setter toString
	// alt + s + r  // alt+s+s+s
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	@Override
	public String toString() {
		return "StudentVO [name=" + name + ", kor=" + kor + ", math=" + math + ", eng=" + eng + "]";
	}
	
}







