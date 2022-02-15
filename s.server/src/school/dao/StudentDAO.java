package school.dao;

import school.vo.StudentVO;

public interface StudentDAO {

	/* 로그인 : 0
	 * @param : StudentVO - id, pass
	 * @return : StudentVO 로그인 성공 시 회원정보, 실패시 null
	 */
	public StudentVO loginStudent(StudentVO vo);
	
	// 학번 찾기 : 1
	public StudentVO findStudentNum(StudentVO vo);

	// 비번 찾기 : 2
	public StudentVO findStudentPass(StudentVO vo);
}
