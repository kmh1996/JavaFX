package school.vo;

import java.io.Serializable;


//회원정보를 저장하고 통신에 사용할 Class
public class StudentVO implements Serializable{

	// class가 재컴파일되어도
	// 동일한 설게도로 인식할 수 있도록 지정
	private static final long serialVersionUID = 1L;
	
	// 명령번호
	private int order;
	
	// 학번
	private String mNum;
	
	// 비밀번호
	private String mPw;
	
	// 이름
	private String mName;
	
	// 생년월일
	private int mBir;
	
	// 성별
	private int mGen;
	
	// 이메일
	private String mEmail;
	
	// 명령에 따른 처리 성공 여부
	private boolean success;


	// 로그인용
	public StudentVO(String mNum, String mPw) {
		this.mNum = mNum;
		this.mPw = mPw;
	}
	
	// 비번찾기 용 
	public StudentVO(int order, String mNum, String mEmail) {
		this.order = order;
		this.mNum = mNum;
		this.mEmail = mEmail;
	}

	// 학번 찾기용
	public StudentVO(String mName, int mBir, int mGen) {
		this.mName = mName;
		this.mBir = mBir;
		this.mGen = mGen;
	}
	
	public StudentVO(String mNum, String mName, int mBir, int mGen) {
		this.mNum = mNum;
		this.mName = mName;
		this.mBir = mBir;
		this.mGen = mGen;
	}


	// 모든 정보 저장할 생성자
	public StudentVO(String mNum, String mPw, String mName, int mBir, int mGen) {
		super();
		this.mNum = mNum;
		this.mPw = mPw;
		this.mName = mName;
		this.mBir = mBir;
		this.mGen = mGen;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getmNum() {
		return mNum;
	}

	public void setmNum(String mNum) {
		this.mNum = mNum;
	}

	public String getmPw() {
		return mPw;
	}

	public void setmPw(String mPw) {
		this.mPw = mPw;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public int getmBir() {
		return mBir;
	}

	public void setmBir(int mBir) {
		this.mBir = mBir;
	}

	public int getmGen() {
		return mGen;
	}

	public void setmGen(int mGen) {
		this.mGen = mGen;
	}

	public String getmEmail() {
		return mEmail;
	}

	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof StudentVO) {
			StudentVO m = (StudentVO)obj;
			if(this.mNum == m.getmNum()
				&& 	
				this.mPw.equals(m.getmPw())	
				) {
				// mNum 와 mPw가 일치하면 동일한 객체로 확인
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "StudentVO [order=" + order + ", mNum=" + mNum + ", mPw=" + mPw + ", mName=" + mName + ", mBir=" + mBir
				+ ", mGen=" + mGen + ", mEmail=" + mEmail + ", success=" + success + "]";
	}


	
}

