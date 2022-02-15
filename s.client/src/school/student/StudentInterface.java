package school.student;

import school.vo.StudentVO;

public interface StudentInterface {

	// login UI 초기화
	public void initLoginUI();
		
	// login event 초기화
	public void setLoginEvent();
	
	// 로그인 성공 여부 check
	public void setLoginCheck(StudentVO vo);

	// 0 : 로그인  3: 관리인 로그인
	public void receiveData(StudentVO vo);

}
