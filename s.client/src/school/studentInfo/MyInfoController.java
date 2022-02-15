package school.studentInfo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MyInfoController implements Initializable {

	@FXML private TextField name, num, birthtxt, phonetxt, emailtxt, addrtxt, newpwd, pwdcheck;
	@FXML private Button btnalt, btncancel;
	String stu_id = null;
	private Object stage;
	String sql;
	int TxtsHp;
	String Txtemail;
	String Txtsaddr;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Statement stmt = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			Reader reader = new FileReader("c:/Temp/studentId.txt");
			char[] buffer = new char[100];
			int readCharNum = reader.read(buffer);
			String data = new String(buffer, 0, readCharNum);
			stu_id = data;
		} catch (FileNotFoundException e1) { e1.printStackTrace(); }
		  catch (IOException e1) { e1.printStackTrace(); }
		String driver ="com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://192.168.1.173:3306/student";
		String user = "_admin";
		String password = "12345";
		
		
		
		try {
			//Class.forName(driver);
			System.out.println("Driver Load 완료");
			conn = DriverManager.getConnection(
							url, user, password
						);
			System.out.println("DB 연결 완료 : " + conn);
			
			stmt = conn.createStatement();
			
			sql = "SELECT * FROM student.student where sID = '"+stu_id+"' ";
			// 매개변수로 넘겨 받은 질의를 실행
			// ResultSet 검색 질의에 대한 결과를 저장하는 객체
			ResultSet rs1 = stmt.executeQuery(sql);
			System.out.println(rs1);
			
			
			sql = "SELECT * FROM student.student WHERE sID =  '"+stu_id+"' ";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String Txtname = rs.getString("sName");
				String Txtclass = rs.getString("sDpt");
				Txtemail = rs.getString("sEmail");
				Txtsaddr = rs.getString("sAddr");
				int Txtnum = rs.getInt("sID");
				int Txtyear = rs.getInt("sGrade");
				TxtsHp = rs.getInt("sHp");
				name.setText(Txtname);
				num.setText(Txtnum+"");
				birthtxt.setText(String.valueOf(Txtyear));
				phonetxt.setText(TxtsHp+"");
				emailtxt.setText(Txtemail);
				addrtxt.setText(Txtsaddr);
				
			}
			
			btnalt.setOnMouseClicked(evetnt->{
				
				
				
				
				try {
					int phone = Integer.parseInt(phonetxt.getText());
				} catch (NumberFormatException e) {
					
					System.err.println("휴대폰 번호에 숫자를 입력해주세요");
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("휴대폰 번호 에러");
					alert.setHeaderText("휴대폰 번호를 예시와 같이 입력해주세요!");
					alert.show();
					phonetxt.clear();
					
				} 
				
				if(phonetxt.getText() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("공백 에러");
					alert.setHeaderText("학번을 예시와 같이 입력해주세요!");
					alert.show();
				}
				
				
				try {
					String Emailtxt = emailtxt.getText();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("이메일 에러");
					alert.setHeaderText("이메일을 입력해주세요!");
					alert.show();
					emailtxt.clear();
				}
				if(emailtxt.getText() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("공백 에러");
					alert.setHeaderText("이메일을 입력해주세요!");
					alert.show();
				}
				
				
				try {
					String Addr = addrtxt.getText();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("주소값 에러");
					alert.setHeaderText("주소를 입력해주세요!");
					alert.show();
					addrtxt.clear();			
				}
				if(addrtxt.getText() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("공백 에러");
					alert.setHeaderText("주소를 입력해주세요!");
					alert.show();
				}
				
				try {
					String Newpwd = newpwd.getText();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("새 비밀번호 입력 에러");
					alert.setHeaderText("새 비밀번호를 입력해주세요!");
					alert.show();
					newpwd.clear();
				}
				
				if(newpwd.getText() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("공백 에러");
					alert.setHeaderText("새 비밀번호를 입력해주세요!");
					alert.show();
				}
				
				try {
					String Pwdcheck = pwdcheck.getText();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("새 비밀번호 확인 에러");
					alert.setHeaderText("새 비밀번호를 입력해주세요!");
					alert.show();
					phonetxt.clear();
				}
				if(pwdcheck.getText() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("공백 에러");
					alert.setHeaderText("새 비밀번호 확인을 입력해주세요!");
					alert.show();
				}
				
				
				if((newpwd.getText()).equals(pwdcheck.getText())) {
					
				}else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("새 비밀번호 확인 에러");
					alert.setHeaderText("비밀번호가 일치하지 않습니다.");
					alert.show();
					pwdcheck.clear();
				}
				
				
				// DB에 수정 넣기
				System.out.println("Driver Load 완료");
				try {
					conn = DriverManager.getConnection(
									url, user, password
								);
					stmt = conn.createStatement();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				System.out.println("DB 연결 완료 : " + conn);
				
				
				sql = "UPDATE student.student SET sHp=?, sEmail=?, sAddr=?, spwd=? WHERE  '\"+stu_id+\"'";
				
				try {
					
					String email =emailtxt.getText();
					String addr =addrtxt.getText();
					String phone =String.valueOf((phonetxt.getText())); 
					String pwsd = newpwd.getText();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, phone);
					pstmt.setString(2, email);
					pstmt.setString(3, addr);
					pstmt.setString(4, pwsd);
					pstmt.executeUpdate();
					
					System.out.println(email);
					System.out.println(addr);
					System.out.println(phone);
					System.out.println(pwsd);
					
					System.out.println("수정 성공");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				System.out.println("alt");
				
				
			});
			
			btncancel.setOnMouseClicked(evetnt->{
				Platform.exit();
				System.out.println("취소선택");
			});
			
			
			
			
		
			
			rs.close();
			stmt.close();
			conn.close();	
			

		}catch (SQLException e) {
			e.printStackTrace();
		}

			
		
		
		
		
		
		
	}
}

