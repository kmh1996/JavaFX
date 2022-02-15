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
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import school.vo.ClassEnrollVO;
import school.vo.StudentInfoVO;

public class ClassEnrollController implements Initializable {

	String stu_id = null;
	@FXML private TableView<ClassEnrollVO> tableclass;
	@FXML private TableView<ClassEnrollVO> tablelist;
	@FXML private Button btnEnroll, btnCancel;
	@FXML private TableColumn<ClassEnrollVO,?> year, year2, code, code2, classname, classname2, div, div2, 
								pname, pname2, num, num2, time, time2, classroom, classroom2, hack, hack2;
	@FXML private TextField txtnum, txtname, txtclass, txtyear, txthap;
	
	
	ObservableList<ClassEnrollVO> list;
	ObservableList<ClassEnrollVO> list2;
	ObservableList<StudentInfoVO> info;
	Stage dialog;
	int sum = 0;
	int temp = 0;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	Statement state = null;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Reader reader = new FileReader("c:/Temp/studentId.txt");
			char[] buffer = new char[100];
			int readCharNum = reader.read(buffer);
			String data = new String(buffer, 0, readCharNum);
			stu_id = data;
		} catch (FileNotFoundException e1) { e1.printStackTrace(); }
		  catch (IOException e1) { e1.printStackTrace(); }
		
		
	
		list = FXCollections.observableArrayList();
		list2 = FXCollections.observableArrayList();
		info = FXCollections.observableArrayList();
		
	
	
		
		year.setCellValueFactory(
			new PropertyValueFactory<>("year")
				);
		
		code.setCellValueFactory(
				new PropertyValueFactory<>("code")
					);
		
		classname.setCellValueFactory(
				new PropertyValueFactory<>("classname")
					);
		
		div.setCellValueFactory(
				new PropertyValueFactory<>("div")
					);
		
		pname.setCellValueFactory(
				new PropertyValueFactory<>("pname")
					);
		
		num.setCellValueFactory(
				new PropertyValueFactory<>("num")
					);
		
		time.setCellValueFactory(
				new PropertyValueFactory<>("time")
					);
		
		classroom.setCellValueFactory(
				new PropertyValueFactory<>("classroom")
					);
		
		hack.setCellValueFactory(
				new PropertyValueFactory<>("hack")
					);
		
		
		year2.setCellValueFactory(
				new PropertyValueFactory<>("year")
					);
			
			code2.setCellValueFactory(
					new PropertyValueFactory<>("code")
						);
			
			classname2.setCellValueFactory(
					new PropertyValueFactory<>("classname")
						);
			
			div2.setCellValueFactory(
					new PropertyValueFactory<>("div")
						);
			
			pname2.setCellValueFactory(
					new PropertyValueFactory<>("pname")
						);
			
			num2.setCellValueFactory(
					new PropertyValueFactory<>("num")
						);
			
			time2.setCellValueFactory(
					new PropertyValueFactory<>("time")
						);
			
			classroom2.setCellValueFactory(
					new PropertyValueFactory<>("classroom")
						);
			
			hack2.setCellValueFactory(
					new PropertyValueFactory<>("hack")
						);
		
		

		try {
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
				"jdbc:mysql://192.168.1.173/student",
				"student1",
				"12345"
			);
			System.out.println("연결 완료");
       
			
			
			
			
			String sql = "SELECT * FROM lecturelist";
			System.out.println(sql);
			Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				
				String Code = rs.getString("수강코드");
				String Classname = rs.getString("강의명");
				String Div = rs.getString("구분");
				String Pname = rs.getString("교수명");
				String Time = rs.getString("시간");
				String Classroom = rs.getString("강의실");
				String Year = rs.getString("학년");
				String Num = rs.getString("인원");
				String Hack = rs.getString("학점");
				temp = Integer.parseInt(Hack);
				// System.out.println(rs.getRow());
				System.out.print(Year);
				
				list.add(new ClassEnrollVO(Code, Classname, Div, Pname, Time, Classroom, Year, Num, Hack));
			}
			String sql2 = "SELECT * FROM lecturelist WHERE 신청자 LIKE '%"+stu_id+"%' ";
			rs = st.executeQuery(sql2);
			while(rs.next()) {
				String Code = rs.getString("수강코드");
				String Classname = rs.getString("강의명");
				String Div = rs.getString("구분");
				String Pname = rs.getString("교수명");
				String Time = rs.getString("시간");
				String Classroom = rs.getString("강의실");
				String Year = rs.getString("학년");
				String Num = rs.getString("인원");
				String Hack = rs.getString("학점");
				list2.add(new ClassEnrollVO(Code, Classname, Div, Pname, Time, Classroom, Year, Num, Hack));
			}
			
			tableclass.setItems(list);
			tablelist.setItems(list2);
			
			
			// 학생 정보
						sql = "SELECT * FROM student.student";
						System.out.println(sql);
						st = conn.createStatement();
						rs = st.executeQuery(sql);
						while(rs.next()) {
							String Txtname = rs.getString("sName");
							String Txtclass = rs.getString("sDpt");
							int Txtnum = rs.getInt("sID");
							int Txtyear = rs.getInt("sGrade");
							txtclass.setText(Txtclass);
							txtname.setText(Txtname);
							txtyear.setText(Txtyear+"");
							txtnum.setText(String.valueOf(Txtnum));

						}
						
						System.out.println(txtname);
			
			
			
			btnEnroll.setOnMouseClicked(event->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("추가 확인");
				alert.setHeaderText("추가 하시겠습니까?");
				alert.setContentText("확인 = 추가 // 취소 = 취소");
				Optional<ButtonType> result = alert.showAndWait();
				// 확인 버튼 선택
				if(result.get() == ButtonType.OK) {
					System.out.println("OK 버튼 선택");
					ClassEnrollVO vo = tableclass.getSelectionModel().getSelectedItem();
//					list2.add(vo);
					System.out.println("voCode"+vo.getCode());
					sum = sum + temp;
					txthap.setText(sum+"");
					System.out.println(sum);
					String sql3;
					String sql4;
					sql3 = "select 신청자 from lecturelist where 수강코드 = '"+vo.getCode()+"' ";
					System.out.println(sql3);
					try {
						state = conn.createStatement();
						rs = state.executeQuery(sql3);
						String regList = null;
						while(rs.next()) {
							regList = rs.getString("신청자");
						}
						System.out.println(regList);
						sql4 = "update lecturelist set 신청자 = ?"
								+ "WHERE 수강코드 = '"+vo.getCode()+"' ";
						pstmt = conn.prepareStatement(sql4);
						if(regList == null) {
							System.out.println("첫 번째 신청자");
							pstmt.setString(1, stu_id);
							pstmt.executeUpdate();
							list2.add(vo);
							// 신청자 목록이 null이 아니고신청자 목록에 현재 로그인한 사용자의 학번이 존재하지 않을 때
						} else if(regList != null && !regList.contains(stu_id)){
							System.out.println("아직 신청하지 않은 강좌");
							pstmt.setString(1, regList+","+stu_id);
							pstmt.executeUpdate();
							list2.add(vo);
						} else if(regList != null && regList.contains(stu_id)){
							System.out.println("이미 신청하신 강좌");
							showAlert("이미 신청하신 강좌입니다!");
						} else {
							
						}
//						pstmt = conn.prepareStatement(sql3);
//						pstmt.setString(1, stu_id);
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(result.get() == ButtonType.CANCEL){
					// 취소 버튼 + 닫기버튼 + alt+f4
					System.out.println("취소 선택");
				}else {
					System.out.println("모르는 버튼");
				}
			
			
				
				// database table 생성
				// database connection 
				// insert 삽입
				
			});
			
			
			btnCancel.setOnMouseClicked(event->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("취소 확인");
				alert.setHeaderText("취소 하시겠습니까?");
				alert.setContentText("확인 = 취소 // 취소  = 중지");
				Optional<ButtonType> result = alert.showAndWait();
				// 확인 버튼 선택
				if(result.get() == ButtonType.OK) {
					System.out.println("OK 버튼 선택");
					ClassEnrollVO vo = tablelist.getSelectionModel().getSelectedItem();
					String select_sql
					= "select 신청자 from lecturelist where 수강코드 = '"+vo.getCode()+"' ";
					String regList;
					try {
						state = conn.createStatement();
						rs = state.executeQuery(select_sql);
						regList = null;
						while(rs.next()) {
							regList = rs.getString("신청자");
						}
						System.out.println(regList);
						regList = regList.replace(stu_id, "00000000");
						String update_sql = "update lecturelist set 신청자 = ?"
								+ "WHERE 수강코드 = '"+vo.getCode()+"' ";
						pstmt = conn.prepareStatement(update_sql);
						pstmt.setString(1, regList);
						pstmt.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					list2.remove(vo);
					sum = sum - temp;
					 txthap.setText(sum+"");
					 System.out.println(sum);
				}else if(result.get() == ButtonType.CANCEL){
					// 취소 버튼 + 닫기버튼 + alt+f4
					System.out.println("취소 선택");
				}else {
					System.out.println("모르는 버튼");
				}
			});
			
			
			
			

			
			st.close();
			rs.close();

			
		} catch (ClassNotFoundException e1) {
			System.out.println("Driver class가 존재하지 않음");
		} catch (SQLException e1) {
			System.out.println("db 연결 정보가 일치하지 않음");
		}
			
	
	}
	

	private String getString(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void showAlert(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림");
		alert.setHeaderText(text);
		alert.show();
	}
	}
