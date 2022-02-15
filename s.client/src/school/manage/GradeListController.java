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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import school.vo.GradesListVO;

public class GradeListController implements Initializable {
	int tblnum;

	@FXML
	private TextField gradeName, gradeId, gradeYear, division, gradeGrade, gradeLecture, gradeProfessor,
			gradecreditsCom, gradeGrades;

	@FXML
	private Button gradeAdd, btnUpdate, btnDelete, btnAdd, btnCancel;

	@FXML
	private TableView<GradesListVO> gradeTable;

	@FXML
	private TableColumn<GradesListVO, ?> tblName, tblId, tblYear, tbldivision, tblgrade, tblLecture, tblProf, tblCred,
			tblgrades;

	@FXML
	private TextField txtName, txtId, txtYear, txtdivision, txtGrade, txtLecture, txtProfessor, txtcreditsCom,
			txtGrades;

	private ObservableList<GradesListVO> list;

	int tblIdx;
	String tblIdKey; // 선택한 학생의 학번

	Connection conn = null;
	Statement state = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private Stage dialog;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = FXCollections.observableArrayList();
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://192.168.1.173:3306/student";
		String user = "_admin";
		String password = "12345";

		try {
			Class.forName(driver);
			System.out.println("드라이버 클래스 로드 완료");
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("DB연결 성공");
			state = conn.createStatement();

			String sql = "SELECT * FROM gradeslist2";
			rs = state.executeQuery(sql);
			while (rs.next()) {
				String gradeName = rs.getString("name");
				String gradeId = rs.getString("id");
				int gradeYear = rs.getInt("lectureYear");
				String division = rs.getString("division");
				int gradeGrade = rs.getInt("grade");
				String gradeLecture = rs.getString("Lecture");
				String gradeProfessor = rs.getString("Professor");
				int gradecreditsCom = rs.getInt("creditsCom");
				String gradeGrades = rs.getString("grades");
				list.add(new GradesListVO(gradeName, gradeId, gradeYear, division, gradeGrade, gradeLecture,
						gradeProfessor, gradecreditsCom, gradeGrades));
			}
			System.out.println(list);

			tblName.setCellValueFactory(new PropertyValueFactory<>("gradeName"));
			tblId.setCellValueFactory(new PropertyValueFactory<>("gradeId"));
			tblYear.setCellValueFactory(new PropertyValueFactory<>("gradeYear"));
			tbldivision.setCellValueFactory(new PropertyValueFactory<>("division"));
			tblgrade.setCellValueFactory(new PropertyValueFactory<>("gradeGrade"));
			tblLecture.setCellValueFactory(new PropertyValueFactory<>("gradeLecture"));
			tblProf.setCellValueFactory(new PropertyValueFactory<>("gradeProfessor"));
			tblCred.setCellValueFactory(new PropertyValueFactory<>("gradecreditsCom"));
			tblgrades.setCellValueFactory(new PropertyValueFactory<>("gradeGrades"));

			// 컬럼 center 정렬
			for (TableColumn<GradesListVO, ?> tc : gradeTable.getColumns()) {
				tc.setPrefWidth(100.0);
				tc.setResizable(false);
				tc.setStyle("-fx-alignment:center;");
			}

			// list에 추가
			gradeTable.setItems(list);
			if (rs != null)
				rs.close();
			if (state != null)
				state.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Driver class가 존재하지 않음");
		} catch (SQLException e) {
			System.out.println("DB 연결 정보 오류" + e.getMessage());
		}

		gradeAdd.setOnAction(event -> {
			dialog = new Stage();
			dialog.setTitle("추가");
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(gradeAdd.getScene().getWindow());

			try {
				Parent root = FXMLLoader.load(getClass().getResource("GradeList_Add.fxml"));

				TextField txtName = (TextField) root.lookup("#txtName");
				TextField txtId = (TextField) root.lookup("#txtId");
				TextField txtYear = (TextField) root.lookup("#txtYear");
				TextField txtdivision = (TextField) root.lookup("#txtdivision");
				TextField txtGrade = (TextField) root.lookup("#txtGrade");
				TextField txtLecture = (TextField) root.lookup("#txtLecture");
				TextField txtProfessor = (TextField) root.lookup("#txtProfessor");
				TextField txtcreditsCom = (TextField) root.lookup("#txtcreditsCom");
				TextField txtGrades = (TextField) root.lookup("#txtGrades");
				Button btnAdd = (Button) root.lookup("#btnAdd");
				Button btnCancel = (Button) root.lookup("#btnCancel");

				btnAdd.setOnAction(e -> {
					String sql1 = "SELECT * FROM  GradesList2 Where Id = ?";
					try {
						pstmt = conn.prepareStatement(sql1);
						pstmt.setString(1, txtId.getText());
						rs = pstmt.executeQuery();
						if (rs.next()) {
							// 동일 아이디 존재 삽입 x
							showAlert("동일한 아이디가 존재합니다!");
						} else {
							// 동일 아이디 존재 하지 않을때
							String sql = "insert into GradesList2"
									+ "(name,Id,lectureYear,division,grade,Lecture,Professor,creditsCom,grades)"
									+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
							System.out.println(txtName.getText());
							try {
								pstmt = conn.prepareStatement(sql);
								pstmt.setString(1, txtName.getText());
								pstmt.setString(2, txtId.getText());
								pstmt.setInt(3, Integer.parseInt(txtYear.getText()));
								pstmt.setString(4, txtdivision.getText());
								pstmt.setInt(5, Integer.parseInt(txtGrade.getText()));
								pstmt.setString(6, txtLecture.getText());
								pstmt.setString(7, txtProfessor.getText());
								pstmt.setInt(8, Integer.parseInt(txtcreditsCom.getText()));
								pstmt.setString(9, txtGrades.getText());

								pstmt.executeUpdate();
								GradesListVO vo = new GradesListVO();
								vo.setGradeName(txtName.getText());
								vo.setGradeId(txtId.getText());
								vo.setGradeYear(Integer.parseInt(txtYear.getText()));
								vo.setDivision(txtdivision.getText());
								vo.setGradeGrade(Integer.parseInt(txtGrade.getText()));
								vo.setGradeLecture(txtLecture.getText());
								vo.setGradeProfessor(txtProfessor.getText());
								vo.setGradecreditsCom(Integer.parseInt(txtcreditsCom.getText()));
								vo.setGradeGrades(txtGrades.getText());
								// 데이터 베이스에 추가되는 내용을 GradesListVO에도 추가
								// GradeListVO 객체 생성
								// 생성된 GradeListVO list.add(vo);
								list.add(vo);

								if (pstmt != null)
									pstmt.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							dialog.close();
						}
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				});

				btnCancel.setOnAction(e -> {
					System.out.println("취소버튼");
					dialog.close();
				});

				Scene scene = new Scene(root);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();

			} catch (IOException e1) {
				try {
					if (pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				e1.printStackTrace();
			}
		});

		btnUpdate.setOnAction(e -> {
			GradesListVO grades = gradeTable.getSelectionModel().getSelectedItem();
			tblnum++;
			System.out.println("tblnum" + tblnum); // 테이블 열 넘버
			if (grades == null) {
				showAlert("정보를 수정할 학생을 선택해주세요!");
				return;
			}
			// 올바른 학생을 선택함
			try {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("확인");
				alert.setHeaderText("학생수정");
				alert.setContentText("정말 수정하시겠습니까?");
				ButtonType buttonTypeAccept = new ButtonType("확인");
				ButtonType buttonTypeCancel = new ButtonType("취소");
				alert.getButtonTypes().setAll(buttonTypeAccept, buttonTypeCancel);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeAccept) {
					conn = DriverManager.getConnection(url, user, password);
					PreparedStatement pstmt = null;					

							// 수정 sql문
							System.out.println(gradeName.getText());
							String sql = "UPDATE GradesList2 SET" + " name = ?," + "Id = ?," + "lectureYear = ?,"
									+ "division = ?," + "grade = ?," + "Lecture = ?," + "Professor = ?,"
									+ "creditsCom = ?," + "grades = ?" + " WHERE Id = '" + gradeId.getText() + "' ";
							System.out.println(sql);
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, gradeName.getText());
							pstmt.setInt(2, Integer.parseInt(gradeId.getText()));
							pstmt.setString(3, gradeYear.getText());
							pstmt.setString(4, division.getText());
							pstmt.setInt(5, Integer.parseInt(gradeGrade.getText()));
							pstmt.setString(6, gradeLecture.getText());
							pstmt.setString(7, gradeProfessor.getText());
							pstmt.setString(8, gradecreditsCom.getText());
							pstmt.setString(9, gradeGrades.getText());

							int r = pstmt.executeUpdate();

							System.out.println(r);

							grades.setGradeName(gradeName.getText());
							grades.setGradeId(gradeId.getText());
							grades.setGradeYear(Integer.parseInt(gradeYear.getText()));
							grades.setDivision(division.getText());
							grades.setGradeGrade(Integer.parseInt(gradeGrade.getText()));
							grades.setGradeLecture(gradeLecture.getText());
							grades.setGradeProfessor(gradeProfessor.getText());
							grades.setGradecreditsCom(Integer.parseInt(gradecreditsCom.getText()));
							grades.setGradeGrades(gradeGrades.getText());

							list.set(tblIdx, grades);
							if (pstmt != null)
								pstmt.close();
							showAlert("수정되었습니다.");
						}else if (result.get() == buttonTypeCancel) {	
				 
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
			}		
			
		}); // 수정 버튼 끝

		// 테이블 컬럼 선택
		gradeTable.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				GradesListVO grades = gradeTable.getSelectionModel().getSelectedItem();
				tblIdx = gradeTable.getSelectionModel().getSelectedIndex();
				tblIdKey = gradeTable.getSelectionModel().getSelectedItem().getGradeId();
				System.out.println(tblIdx);
				System.out.println(tblIdKey);
				gradeName.setText(grades.getGradeName());
				gradeId.setText(grades.getGradeId());
				gradeYear.setText(grades.getGradeYear() + "");
				division.setText(grades.getDivision());
				gradeGrade.setText(grades.getGradeGrade() + "");
				gradeLecture.setText(grades.getGradeLecture());
				gradeProfessor.setText(grades.getGradeProfessor());
				gradecreditsCom.setText(grades.getGradecreditsCom() + "");
				gradeGrades.setText(grades.getGradeGrades());
			}
		});
		// 삭제
		btnDelete.setOnAction(event -> {
			GradesListVO grades = gradeTable.getSelectionModel().getSelectedItem();
			if (grades == null) {
				showAlert("정보를 삭제할 학생을 선택해주세요!");
				return;
			}
			try {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("확인");
				ButtonType buttonTypeAccept = new ButtonType("확인");
				ButtonType buttonTypeCancel = new ButtonType("취소");
				alert.getButtonTypes().setAll(buttonTypeAccept, buttonTypeCancel);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeAccept) {
					PreparedStatement pstmt = null;

					String sql = "DELETE FROM GradesList2 WHERE id = " + tblIdKey + "";
					System.out.println(sql);
					pstmt = conn.prepareStatement(sql);
					pstmt.executeUpdate();
					list.remove(tblIdx);
					if (pstmt != null)
						pstmt.close();
				} else if (result.get() == buttonTypeCancel) {

				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	} // initialize 끝

	// alert
	public void showAlert(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림");
		alert.setHeaderText(text);
		alert.show();
	}

}
