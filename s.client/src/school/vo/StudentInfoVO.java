package school.vo;

public class StudentInfoVO {

	private String txtname, txtclass;
	private int txtnum, txtyear, txthap;
	public StudentInfoVO(String txtname, String txtclass, int txtnum, int txtyear, int txthap) {
		this.txtname = txtname;
		this.txtclass = txtclass;
		this.txtnum = txtnum;
		this.txtyear = txtyear;
		this.txthap = txthap;
	}
	public String getTxtname() {
		return txtname;
	}
	public void setTxtname(String txtname) {
		this.txtname = txtname;
	}
	public String getTxtclass() {
		return txtclass;
	}
	public void setTxtclass(String txtclass) {
		this.txtclass = txtclass;
	}
	public int getTxtnum() {
		return txtnum;
	}
	public void setTxtnum(int txtnum) {
		this.txtnum = txtnum;
	}
	public int getTxtyear() {
		return txtyear;
	}
	public void setTxtyear(int txtyear) {
		this.txtyear = txtyear;
	}
	public int getTxthap() {
		return txthap;
	}
	public void setTxthap(int txthap) {
		this.txthap = txthap;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "StudentInfo [txtname= " + txtname + ", txtclass= " + txtclass + ", txtnum = " + txtnum + ", txtyear = " + txtyear + 
				", txthap= " + txthap + "]";
	}
	
	
	
}
