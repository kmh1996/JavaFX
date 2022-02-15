package school.manage;

import java.io.IOException;
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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import school.vo.stuVO;

public class StuMNGController implements Initializable {
	int tbl_idx;
	String tbl_id_key; // 선택한 학생의 학번
	private Stage dialog;
	Connection conn = null;
	Statement state = null;
	ResultSet rs = null;
	// 이름, 학번, 학과, 학년, 생년월일, 핸드폰, 이메일, 주소 TextField
	@FXML private TextField
	stuName, stuId, stuDpt, stuGrade, stuBirth, stuHp, stuEmail, stuAddr;
	
	// 추가, 수정, 삭제 Button
	@FXML private Button stuAdd, stuUpdate, stuDelete;
	
	// 학생관리 TableView
	@FXML private TableView<stuVO> stuTable;
	@FXML private TableColumn<stuVO,?>
	tbl_sName, tbl_sID, tbl_sDpt, tbl_sGrade, tbl_sBirth, tbl_sHp, tbl_sEmail, tbl_sAddr;
	private ObservableList<stuVO> list;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		list = FXCollections.observableArrayList();
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://192.168.1.173:3306/student";
		String user = "_admin";
		String password ="12345";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			state = conn.createStatement();

			String sql = "SELECT * FROM student";
			rs = state.executeQuery(sql);
			
			while(rs.next()){
				String stuName 	= rs.getString("sName");
				String stuID 	= rs.getString("sID");
				String stuDpt 	= rs.getString("sDpt");
				String stuGrade = rs.getString("sGrade");
				String stuBirth = rs.getString("sBirth");
				String stuHp 	= rs.getString("sHp");
				String stuEmail = rs.getString("sEmail");
				String stuAddr 	= rs.getString("sAddr");
				list.add(new stuVO(
					stuName,stuID,stuDpt,stuGrade,stuBirth,stuHp,stuEmail,stuAddr));
			}

			tbl_sName.setCellValueFactory(new PropertyValueFactory<>("stuName"));
			tbl_sID.setCellValueFactory(new PropertyValueFactory<>("stuID"));
			tbl_sDpt.setCellValueFactory(new PropertyValueFactory<>("stuDpt"));
			tbl_sGrade.setCellValueFactory(new PropertyValueFactory<>("stuGrade"));
			tbl_sBirth.setCellValueFactory(new PropertyValueFactory<>("stuBirth"));
			tbl_sHp.setCellValueFactory(new PropertyValueFactory<>("stuHp"));
			tbl_sEmail.setCellValueFactory(new PropertyValueFactory<>("stuEmail"));
			tbl_sAddr.setCellValueFactory(new PropertyValueFactory<>("stuAddr"));
			
			// 컬럼 center 정렬
			for(TableColumn<stuVO,?> tc : stuTable.getColumns()) {
				tc.setPrefWidth(100.0);
				tc.setResizable(false);
				tc.setStyle("-fx-alignment:center;");
			}
			
			// list에 추가
			stuTable.setItems(list);
			if(rs != null) rs.close();
			if(state != null) state.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Driver class가 존재하지 않음");
		} catch (SQLException e) {
			System.out.println("DB 연결 정보 오류"+e.getMessage());
		}
		
		// 학생 추가 다이얼로그 열기 버튼
		stuAdd.setOnAction(e -> {
			dialog = new Stage();
			dialog.setTitle("추가");
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(stuAdd.getScene().getWindow());
			
			try {
				Parent root = FXMLLoader.load(getClass().getResource("stuMNG_add.fxml"));
				
				TextField txtName = (TextField)root.lookup("#txtName");
				TextField txtId = (TextField)root.lookup("#txtId");
				TextField txtDpt = (TextField)root.lookup("#txtDpt");
				TextField txtGrade = (TextField)root.lookup("#txtGrade");
				TextField txtBirth = (TextField)root.lookup("#txtBirth");
				TextField txtHp = (TextField)root.lookup("#txtHp");
				TextField txtEmail = (TextField)root.lookup("#txtEmail");
				TextField txtAddr = (TextField)root.lookup("#txtAddr");
				
				Button btnAdd = (Button)root.lookup("#btnAdd");
				Button btnCancel = (Button)root.lookup("#btnCancel");
				
				// 학생 추가 버튼
				btnAdd.setOnAction(event -> {
					try {
						PreparedStatement pstmt = null;
						// insert sql문 시작
						String sql = "insert into student"
								+ " (sName,sID,sDpt,sGrade,sBirth,sHp,sEmail,sAddr)"
								+ " values(?, ?, ?, ?, ?, ?, ?, ?)";
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("학생추가");
						alert.setHeaderText("정말 추가하시겠습니까?");
						ButtonType buttonTypeAccept = new ButtonType("확인");
						ButtonType buttonTypeCancel = new ButtonType("취소");
						alert.getButtonTypes().setAll(buttonTypeAccept, buttonTypeCancel);
						Optional<ButtonType> result = alert.showAndWait();
						// 확인 버튼 클릭
						if (result.get() == buttonTypeAccept){
							int tbl_size = stuTable.getItems().size();
							for(int i=0; i<tbl_size; i++) {
								if(stuTable.getItems().get(i).getStuID().equals
									(txtId.getText())) {
									showAlert("학생추가","동일한 학번의 학생이 존재합니다.");
									return;
								}
							}
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, txtName.getText());
							pstmt.setString(2, txtId.getText());
							pstmt.setString(3, txtDpt.getText());
							pstmt.setString(4, txtGrade.getText());
							pstmt.setString(5, txtBirth.getText());
							pstmt.setString(6, txtHp.getText());
							pstmt.setString(7, txtEmail.getText());
							pstmt.setString(8, txtAddr.getText());
							
							int r = pstmt.executeUpdate(); // insert sql 실행
							System.out.println(r);
							stuVO stu = new stuVO(
								txtName.getText(),
								txtId.getText(),
								txtDpt.getText(),
								txtGrade.getText(),
								txtBirth.getText(),
								txtHp.getText(),
								txtEmail.getText(),
								txtAddr.getText()
							);
							System.out.println(stu);
							list.add(stu);
							if(pstmt != null) pstmt.close();
							showAlert("학생추가","추가되었습니다.");
						} // 추가 확인
					} catch (SQLException e1) {
						System.out.println("학생 추가 오류 발생");
						e1.printStackTrace();
					}
					dialog.close();
				});
				
				btnCancel.setOnAction(event -> {
					System.out.println("취소버튼");
					dialog.close();
				});
				
				Scene scene = new Scene(root);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}); // 추가 버튼 끝
		
		// 수정 버튼
		stuUpdate.setOnAction(event -> {
			stuVO stu = stuTable.getSelectionModel().getSelectedItem();
			if(stu == null) {
				showAlert("학생수정","정보를 수정할 학생을 선택해주세요!");
				return;
			}
			
			// 올바른 학생을 선택함
			try {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("학생수정");
				alert.setHeaderText("정말 수정하시겠습니까?");
				ButtonType buttonTypeAccept = new ButtonType("확인");
				ButtonType buttonTypeCancel = new ButtonType("취소");
				alert.getButtonTypes().setAll(buttonTypeAccept, buttonTypeCancel);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeAccept){
//					int tbl_size = stuTable.getItems().size();
//					for(int i=0; i<tbl_size; i++) {
//						if(stuTable.getItems().get(i).getStuID().equals
//							(stuId.getText())) {
//							showAlert("학생수정","동일한 학번의 학생이 존재합니다.");
//							return;
//						}
//					}
					conn = DriverManager.getConnection(url, user, password);
					PreparedStatement pstmt = null;
					// 수정 sql문
					System.out.println(stuName.getText());
					String sql = "UPDATE student SET"
							+ " sName = ?,"
							+ "sID = ?,"
							+ "sDpt = ?,"
							+ "sGrade = ?,"
							+ "sBirth = ?,"
							+ "sHp = ?,"
							+ "sEmail = ?,"
							+ "sAddr = ?"
							+ " WHERE sID = "+tbl_id_key+"";
					System.out.println(sql);
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, stuName.getText());
					pstmt.setInt(2, Integer.parseInt(stuId.getText()));
					pstmt.setString(3, stuDpt.getText());
					pstmt.setInt(4, Integer.parseInt(stuGrade.getText()));
					pstmt.setInt(5, Integer.parseInt(stuBirth.getText()));
					pstmt.setInt(6, Integer.parseInt(stuHp.getText()));
					pstmt.setString(7, stuEmail.getText());
					pstmt.setString(8, stuAddr.getText());
					pstmt.executeUpdate();
					stu.setStuName(stuName.getText());
					stu.setStuID(stuId.getText());
					stu.setStuDpt(stuDpt.getText());
					stu.setStuGrade(stuGrade.getText());
					stu.setStuBirth(stuBirth.getText());
					stu.setStuHp(stuHp.getText());
					stu.setStuEmail(stuEmail.getText());
					stu.setStuAddr(stuAddr.getText());
					list.set(tbl_idx, stu);
					if(pstmt != null) pstmt.close();
					showAlert("학생수정","수정되었습니다.");
				} else if(result.get() == buttonTypeCancel){}
			} catch (SQLException e1) {
				System.out.println("학생 수정 오류 발생");
				e1.printStackTrace();
			}
		}); // 수정 버튼 끝
		
		// 테이블 컬럼 선택
		stuTable.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				stuVO stu = stuTable.getSelectionModel().getSelectedItem();
				// 선택한 컬럼의 tbl 번호
				tbl_idx = stuTable.getSelectionModel().getSelectedIndex();
				tbl_id_key = stuTable.getSelectionModel().getSelectedItem().getStuID();
				System.out.println(tbl_idx);
				System.out.println(tbl_id_key);
				stuName.setText(stu.getStuName());
				stuId.setText(stu.getStuID());
				stuDpt.setText(stu.getStuDpt());
				stuGrade.setText(stu.getStuGrade());
				stuBirth.setText(stu.getStuBirth());
				stuHp.setText(stu.getStuHp());
				stuEmail.setText(stu.getStuEmail());
				stuAddr.setText(stu.getStuAddr());
			}
		});
		
		// 학생 삭제
		stuDelete.setOnAction(event -> {
			stuVO stu = stuTable.getSelectionModel().getSelectedItem();
			if(stu == null) {
				showAlert("학생삭제","정보를 삭제할 학생을 선택해주세요!");
				return;
			}
			try {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("학생삭제");
				alert.setHeaderText("정말 삭제하시겠습니까?");
				ButtonType buttonTypeAccept = new ButtonType("확인");
				ButtonType buttonTypeCancel = new ButtonType("취소");
				alert.getButtonTypes().setAll(buttonTypeAccept, buttonTypeCancel);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeAccept){
					PreparedStatement pstmt = null;
					// 삭제 sql문
					String sql = "DELETE FROM student WHERE sID = "+tbl_id_key+"";
					System.out.println(sql);
					pstmt = conn.prepareStatement(sql);
					pstmt.executeUpdate();
					list.remove(tbl_idx);
					if(pstmt != null) pstmt.close();
				} else {}
			} catch (SQLException e1) {
				System.out.println("학생 삭제 오류 발생");
				e1.printStackTrace();
			}
		});
	} // initialize 끝
	
	// alert
	public void showAlert(String title, String header) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.show();
	}
	
}
