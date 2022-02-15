package school.dao;

import java.sql.SQLException;

import school.vo.StudentVO;


public class StudentDAOImpl extends DAO implements StudentDAO{
	String user_name;
	@Override
	public StudentVO loginStudent(StudentVO vo) {			// 로그인용
		
		String sql = "SELECT * FROM login WHERE mNum =? AND mPw =?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getmNum());
			pstmt.setString(2, vo.getmPw());
			rs = pstmt.executeQuery();
			if(rs.next()) { 						
				String num = rs.getString("mNum");
				vo.setmNum(num);
				String pass = rs.getString("mPw");
				vo.setmPw(pass);
				vo.setSuccess(true);
				user_name = rs.getString("mName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {}
		}
		return vo;
	}

	@Override
	public StudentVO findStudentNum(StudentVO vo) {			// 학번 찾기용
		
		String sql = "SELECT * FROM login WHERE mName =? AND mBir =? AND mGen =? ";
		
		try {
			pstmt = conn.prepareStatement(sql);			 
			pstmt.setString(1, vo.getmName());
			pstmt.setInt(2, vo.getmBir());
			pstmt.setInt(3, vo.getmGen());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String num = rs.getString("mNum");
				vo.setmNum(num);
				String name = rs.getString("mName");
				vo.setmName(name);
				int birth = rs.getInt("mBir");
				vo.setmBir(birth);
				int gender = rs.getInt("mGen");
				vo.setmGen(gender);
				vo.setSuccess(true);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {}
		}
		return vo;
	}

	@Override
	public StudentVO findStudentPass(StudentVO vo) {				// 비번 찾기용
		String sql = "SELECT * FROM login WHERE mNum =? AND mEmail =? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getmNum());
			pstmt.setString(2, vo.getmEmail());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String num = rs.getString("mNum");
				vo.setmNum(num);
				String email = rs.getString("mEmail");
				vo.setmEmail(email);
				//vo.setOrder(2);
				vo.setSuccess(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {}
		}
		return vo;
	}
}
