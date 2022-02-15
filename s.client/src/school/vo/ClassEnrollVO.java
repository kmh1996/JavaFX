package school.vo;

public class ClassEnrollVO {

	private String code, classname, div, pname, time, classroom;
	private String year, num, hack;
	
	
	
	
	
	
	public ClassEnrollVO(String code, String classname, String div, String pname, String time, String classroom,
String year, String num, String hack) {
		super();
		this.code = code;
		this.classname = classname;
		this.div = div;
		this.pname = pname;
		this.time = time;
		this.classroom = classroom;
		this.year = year;
		this.num = num;
		this.hack = hack;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getDiv() {
		return div;
	}
	public void setDiv(String div) {
		this.div = div;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getClassroom() {
		return classroom;
	}
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getHack() {
		return hack;
	}
	public void setHack(String hack) {
		this.hack = hack;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ClassEnrollVO [code= " + code + ", classname= " + classname + ", div= " + div + ", pname= " + pname + 
				", time= " + time + ", clasroom= " + classroom+", year= " + year+", num= "+num+", hack= "+hack +"]";
	}

	
	
	
	
}
