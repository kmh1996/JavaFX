package school.studentInfo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import school.vo.GradeViewVO;

public class GradeController implements Initializable {
	int lecComplete_sum = 0; // 이수학점
	double grades_sum = 0; // 평점(성적)
	String stu_id = null;
	@FXML private TableView<GradeViewVO> gradeTable;
	@FXML private TableColumn<GradeViewVO,?>
	tbl_sName, tbl_sID, tbl_lecYear, tbl_lecDiv, tbl_sGrade, tbl_lecName, tbl_profName, tbl_lecComplete, tbl_lecGrade;
	private ObservableList<GradeViewVO> list;
	
	@FXML private TextField txt_lecComplete;
	@FXML private TextField txt_lecGrade;
	
	Connection conn = null;
	Statement state = null;
	ResultSet rs = null;
	
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
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://192.168.1.173:3306/student";
		String user = "_admin";
		String password ="12345";
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			state = conn.createStatement();
			String sql = "SELECT * FROM gradeslist2 WHERE Id = '"+stu_id+"' ";
			System.out.println(sql);
			rs = state.executeQuery(sql);
			int count = 0;
			while(rs.next()){
				String name 		= rs.getString("name");
				String sId 		   	= rs.getString("Id");
				String lectureYear 	= rs.getString("lectureYear");
				String division 	= rs.getString("division");
				String grade 		= rs.getString("grade");
				String lecture 		= rs.getString("Lecture");
				String Professor 	= rs.getString("Professor");
				String creditsCom 	= rs.getString("creditsCom");
				String grades 		= rs.getString("grades");
				lecComplete_sum += Integer.parseInt(creditsCom);
				switch(grades) {
					case "A+" : grades_sum += 4.5; break;
					case "A0" : grades_sum += 4.0; break;
					case "B+" : grades_sum += 3.5; break;
					case "B0" : grades_sum += 3.0; break;
					case "C+" : grades_sum += 2.5; break;
					case "C0" : grades_sum += 2.0; break;
					case "D+" : grades_sum += 1.5; break;
					case "D0" : grades_sum += 1.0; break;
					case "F" : grades_sum += 0; break;
					default: break;
				}
				list.add(new GradeViewVO(
					name,sId,lectureYear,division,grade,lecture,Professor,creditsCom,grades));
				count++;
			}
			tbl_sName.setCellValueFactory(new PropertyValueFactory<>("stuName"));
			tbl_sID.setCellValueFactory(new PropertyValueFactory<>("stuID"));
			tbl_lecYear.setCellValueFactory(new PropertyValueFactory<>("lecYear"));
			tbl_lecDiv.setCellValueFactory(new PropertyValueFactory<>("lecDiv"));
			tbl_sGrade.setCellValueFactory(new PropertyValueFactory<>("stuGrade"));
			tbl_lecName.setCellValueFactory(new PropertyValueFactory<>("lecName"));
			tbl_profName.setCellValueFactory(new PropertyValueFactory<>("profName"));
			tbl_lecComplete.setCellValueFactory(new PropertyValueFactory<>("lecComplete"));
			tbl_lecGrade.setCellValueFactory(new PropertyValueFactory<>("lecGrade"));
			// 컬럼 center 정렬
			for(TableColumn<GradeViewVO,?> tc : gradeTable.getColumns()) {
				tc.setPrefWidth(100.0);
				tc.setResizable(false);
				tc.setStyle("-fx-alignment:center;");
			}
			
			// list에 추가
			gradeTable.setItems(list);
			if(rs != null) rs.close();
			if(state != null) state.close();
			txt_lecComplete.setText(lecComplete_sum+"");
			txt_lecGrade.setText((grades_sum / count)+"");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver class가 존재하지 않음");
		} catch (SQLException e) {
			System.out.println("DB 연결 정보 오류"+e.getMessage());
		}
	}

}
