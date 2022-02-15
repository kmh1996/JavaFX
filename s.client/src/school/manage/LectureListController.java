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

import school.vo.LectureListVO;
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

public class LectureListController implements Initializable {
	int tblnum;

	@FXML
	private TextField lecGrade,lecCode, lecture, divide, lecProfessor, lecCount, lecTime,
	lecClass, lecScore;

	@FXML
	private Button lectureAdd, btnUpdate, btnDelete, btnAdd, btnCancel;

	@FXML
	private TableView<LectureListVO> lectureTable;

	@FXML
	private TableColumn<LectureListVO, ?> tblGrade, tblCode, tblLecture, tblDivide, tblProfessor, tblCont, tblTime, tblClass,
	tblScore;

	@FXML
	private TextField txtGrade, txtCode, txtLecture, txtDivide, txtProfessor, txtCount, txtTime, txtClass,txtScore;

	private ObservableList<LectureListVO> list;

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

			String sql = "SELECT * FROM lecturelist";
			rs = state.executeQuery(sql);
			while (rs.next()) {
				String lecGrade = rs.getString("학년");
				String lecCode = rs.getString("수강코드");
				String lecture = rs.getString("강의명");
				String divide = rs.getString("구분");
				String lecProfessor = rs.getString("교수명");
				String lecCount = rs.getString("인원");
				String lecTime = rs.getString("시간");
				String lecClass = rs.getString("강의실");
				String lecScore = rs.getString("학점");
				list.add(new LectureListVO(lecGrade, lecCode, lecture, divide, lecProfessor, lecCount,
						lecTime, lecClass, lecScore));
			}	
			System.out.println(list);
			tblGrade.setCellValueFactory(new PropertyValueFactory<>("lecGrade"));
			tblCode.setCellValueFactory(new PropertyValueFactory<>("lecCode"));
			tblLecture.setCellValueFactory(new PropertyValueFactory<>("lecture"));
			tblDivide.setCellValueFactory(new PropertyValueFactory<>("divide"));
			tblProfessor.setCellValueFactory(new PropertyValueFactory<>("lecProfessor"));
			tblCont.setCellValueFactory(new PropertyValueFactory<>("lecCount"));
			tblTime.setCellValueFactory(new PropertyValueFactory<>("lecTime"));
			tblClass.setCellValueFactory(new PropertyValueFactory<>("lecClass"));
			tblScore.setCellValueFactory(new PropertyValueFactory<>("lecScore"));

			// 컬럼 center 정렬
			for (TableColumn<LectureListVO, ?> tc : lectureTable.getColumns()) {
				tc.setPrefWidth(100.0);
				tc.setResizable(false);
				tc.setStyle("-fx-alignment:center;");
			}

			// list에 추가
			lectureTable.setItems(list);
			if (rs != null)
				rs.close();
			if (state != null)
				state.close();
				
		} catch (ClassNotFoundException e) {
			System.out.println("Driver class가 존재하지 않음");
		} catch (SQLException e) {
			System.out.println("DB 연결 정보 오류" + e.getMessage());
		}

		lectureAdd.setOnAction(event -> {
			dialog = new Stage();
			dialog.setTitle("추가");
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(lectureAdd.getScene().getWindow());

			try {
				Parent root = FXMLLoader.load(getClass().getResource("LectureList_Add.fxml"));

				TextField txtGrade = (TextField) root.lookup("#txtGrade");
				TextField txtCode = (TextField) root.lookup("#txtCode");
				TextField txtLecture = (TextField) root.lookup("#txtLecture");
				TextField txtDivide = (TextField) root.lookup("#txtDivide");				
				TextField txtProfessor = (TextField) root.lookup("#txtProfessor");
				TextField txtCount = (TextField) root.lookup("#txtCount");
				TextField txtTime = (TextField) root.lookup("#txtTime");
				TextField txtClass = (TextField) root.lookup("#txtClass");
				TextField txtScore = (TextField) root.lookup("#txtScore");
				Button btnAdd = (Button) root.lookup("#btnAdd");
				Button btnCancel = (Button) root.lookup("#btnCancel");

				btnAdd.setOnAction(e -> {
					String sql1 = "SELECT * FROM lecturelist Where 수강코드 = ?";
					try {
						pstmt = conn.prepareStatement(sql1);
						pstmt.setString(1, txtCode.getText());
						rs = pstmt.executeQuery();
						if (rs.next()) {
							
							showAlert("동일한 수강코드가 존재합니다!");
						} else {
							// 동일 수강코드 존재 하지 않을때
							String sql = "insert into lecturelist"
									+ "(학년,수강코드,강의명,구분,교수명,인원,시간,강의실,학점)"
									+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
							System.out.println(txtCode.getText());
							try {
								pstmt = conn.prepareStatement(sql);
								pstmt.setString(1, txtGrade.getText());
								pstmt.setString(2, txtCode.getText());
								pstmt.setString(3, txtLecture.getText());
								pstmt.setString(4, txtDivide.getText());
								pstmt.setString(5, txtProfessor.getText());
								pstmt.setString(6, txtCount.getText());
								pstmt.setString(7, txtTime.getText());
								pstmt.setString(8, txtClass.getText());
								pstmt.setString(9, txtScore.getText());

								pstmt.executeUpdate();
								LectureListVO vo = new LectureListVO();
								vo.setLecGrade(txtGrade.getText());
								vo.setLecCode(txtCode.getText());
								vo.setLecture(txtLecture.getText());
								vo.setDivide(txtDivide.getText());
								vo.setLecProfessor(txtProfessor.getText());
								vo.setLecCount(txtCount.getText());
								vo.setLecTime(txtTime.getText());
								vo.setLecClass(txtClass.getText());
								vo.setLecScore(txtScore.getText());
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
			LectureListVO Lec = lectureTable.getSelectionModel().getSelectedItem();
			tblnum++;
			System.out.println("tblnum" + tblnum); // 테이블 열 넘버
			if (Lec == null) {
				showAlert("정보를 수정할 학생을 선택해주세요!");
				return;
			}
			// 올바른 학생을 선택함
			try {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("확인");
				alert.setHeaderText("수정");
				alert.setContentText("정말 수정하시겠습니까?");
				ButtonType buttonTypeAccept = new ButtonType("확인");
				ButtonType buttonTypeCancel = new ButtonType("취소");
				alert.getButtonTypes().setAll(buttonTypeAccept, buttonTypeCancel);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeAccept) {
					conn = DriverManager.getConnection(url, user, password);
					PreparedStatement pstmt = null;

							// 수정 sql문
							System.out.println(lecGrade.getText());
							String sql = "UPDATE lecturelist SET"
							+ " 학년 = ?," 
									+ "수강코드 = ?," + "강의명 = ?,"
									+ "구분 = ?," + "교수명 = ?," + "인원 = ?," + "시간 = ?,"
									+ "강의실 = ?," + "학점 = ?" + " WHERE 수강코드 = '" + lecCode.getText() + "' ";
							System.out.println(sql);
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, lecGrade.getText());
							pstmt.setString(2, lecCode.getText());
							pstmt.setString(3, lecture.getText());
							pstmt.setString(4, divide.getText());
							pstmt.setString(5, lecProfessor.getText());
							pstmt.setString(6, lecCount.getText());
							pstmt.setString(7, lecTime.getText());
							pstmt.setString(8, lecClass.getText());
							pstmt.setString(9, lecScore.getText());

							int r = pstmt.executeUpdate();

							System.out.println(r);

							Lec.setLecGrade(lecGrade.getText());
							Lec.setLecCode(lecCode.getText());
							Lec.setLecture(lecture.getText());
							Lec.setDivide(divide.getText());
							Lec.setLecProfessor(lecProfessor.getText());
							Lec.setLecCount(lecCount.getText());
							Lec.setLecTime(lecTime.getText());
							Lec.setLecClass(lecClass.getText());
							Lec.setLecScore(lecScore.getText());

							list.set(tblIdx, Lec);
							if (pstmt != null)
								pstmt.close();
							showAlert("수정되었습니다.");						
					
				} else if (result.get() == buttonTypeCancel) {
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
	}
			
}); // 수정 버튼 끝

		// 테이블 컬럼 선택
		lectureTable.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				LectureListVO Lec = lectureTable.getSelectionModel().getSelectedItem();
				tblIdx = lectureTable.getSelectionModel().getSelectedIndex();
				tblIdKey = lectureTable.getSelectionModel().getSelectedItem().getLecCode();
				System.out.println(tblIdx);
				System.out.println(tblIdKey);
				lecGrade.setText(Lec.getLecGrade());
				lecCode.setText(Lec.getLecCode());
				lecture.setText(Lec.getLecture() );
				divide.setText(Lec.getDivide());
				lecProfessor.setText(Lec.getLecProfessor());
				lecCount.setText(Lec.getLecCount());
				lecTime.setText(Lec.getLecTime()+ "");
				lecClass.setText(Lec.getLecClass());
				lecScore.setText(Lec.getLecScore() );
			}
		});
		// 삭제
		btnDelete.setOnAction(event -> {
			LectureListVO Lec = lectureTable.getSelectionModel().getSelectedItem();
			if (Lec == null) {
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

					String sql = "DELETE FROM lecturelist WHERE 수강코드 = '" + tblIdKey + "' ";
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
