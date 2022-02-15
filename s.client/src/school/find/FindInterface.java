package school.find;

import school.vo.StudentVO;

public interface FindInterface {
	
	// 학번찾기 UI 초기화
	public void initFindNumUI();
		
	// 학번 찾기 event 초기화
	public void setFindNumEvent();
	
	// 학번찾기 성공 여부 check
	public void setFindNumCheck(StudentVO vo);
	
	// 비번찾기 UI 초기화
	public void initFindPassUI();
		
	// 학번 찾기 event 초기화
	public void setFindPassEvent();
	
	// 학번찾기 성공 여부 check
	public void setFindPassCheck(StudentVO vo);

	// 1 : 학번 2: 비번
	public void receiveData(StudentVO vo);

}
