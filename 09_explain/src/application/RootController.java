package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RootController implements Initializable {

	@FXML private TableView<StudentVO> tableView;
	@FXML private Button btnAdd,btnUpdate,btnDelete,btnBarChart;
	
	private ObservableList<StudentVO> list;
	
	private Stage dialog;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = FXCollections.observableArrayList();
		list.add(new StudentVO("홍길동A",40,60,80));
		list.add(new StudentVO("홍길동B",60,80,40));
		list.add(new StudentVO("홍길동C",80,40,60));
		
		TableColumn<StudentVO,String> tcName
			= new TableColumn<>("이름");
		tcName.setCellValueFactory(
				new PropertyValueFactory<>("name")
		);
		tableView.getColumns().add(0,tcName);
		
		TableColumn<StudentVO , Integer> tcKor 
			= new TableColumn<>("국어");
		tcKor.setCellValueFactory(
				new PropertyValueFactory<>("kor")
			);
		tableView.getColumns().add(1,tcKor);
		
		TableColumn<StudentVO , Integer> tcMath 
		= new TableColumn<>("수학");
		tcMath.setCellValueFactory(
				new PropertyValueFactory<>("math")
			);
		tableView.getColumns().add(2,tcMath);
		
		TableColumn<StudentVO , Integer> tcEng 
			= new TableColumn<>("영어");
		tcEng.setCellValueFactory(
				new PropertyValueFactory<>("eng")
			);
		tableView.getColumns().add(3,tcEng);
		
		for(TableColumn<StudentVO,?> tc : tableView.getColumns()) {
			tc.setPrefWidth(100.0);
			tc.setResizable(false);
			tc.setStyle("-fx-alignment:center;");
		}
		
		tableView.setItems(list);
		
		/*
		// tableView  항목 선택 시 pieChart view 이벤트 추가
		tableView.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<StudentVO> () {
			@Override
			public void changed(
					ObservableValue<? extends StudentVO> observable,
					StudentVO oldValue,
					StudentVO studentVO) {
				System.out.println(studentVO);
				
				dialog = new Stage();
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnAdd.getScene().getWindow());
				dialog.setTitle("파이 그래프");
				Parent root = null;
				try {
					root = FXMLLoader.load(
						getClass().getResource("PieChart.fxml")
					);
				} catch (IOException e) {}
				dialog.setScene(new Scene(root));
				dialog.show();
			}
		});
		*/
		
		tableView.setOnMouseClicked(
		new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// 짧은 시간안에 감지되는 mouse click 횟수
				if(event.getClickCount() == 2) {
					StudentVO studentVO 
					= tableView.getSelectionModel().getSelectedItem();
					System.out.println(studentVO);
					dialog = new Stage();
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initOwner(btnAdd.getScene().getWindow());
					dialog.setTitle("파이 그래프");
					Parent root = null;
					try {
						root = FXMLLoader.load(
							getClass().getResource("PieChart.fxml")
						);
					} catch (IOException e) {}
					Button btnClose = (Button)root.lookup("#btnClose");
					btnClose.setOnAction(e->{
						// PieChart창 닫기
						dialog.close();
					});
					
					PieChart pieChart = (PieChart)root.lookup("#pieChart");
					pieChart.setData(FXCollections.observableArrayList(
							new PieChart.Data("국어", studentVO.getKor()),
							new PieChart.Data("수학", studentVO.getMath()),
							new PieChart.Data("영어", studentVO.getEng())
						));
					dialog.setScene(new Scene(root));
					dialog.show();
				}
			}
		}); // End MouseClicked
		
		// btnAdd 버튼 이벤트 발생 시
		// 이름 점수를 입력받는 Dialog 창을 띄우고
		// 입력한 값을 전달 받아 list에 추가(학생정보 추가)
		btnAdd.setOnAction(e->{
			dialog = new Stage();
			dialog.setTitle("추가");
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnAdd.getScene().getWindow());
			
			try {
				Parent root = FXMLLoader.load(
					getClass().getResource("Form.fxml")
				);
				
				Button btnSave = (Button)root.lookup("#btnSave");
				Button btnCancel = (Button)root.lookup("#btnCancel");
				
				TextField txtName = (TextField)root.lookup("#txtName");
				TextField txtKor = (TextField)root.lookup("#txtKor");
				TextField txtMath = (TextField)root.lookup("#txtMath");
				TextField txtEng = (TextField)root.lookup("#txtEng");
				
				btnCancel.setOnAction(event->dialog.close());
				btnSave.setOnAction(event->{
					String name = txtName.getText();
					String strKor = txtKor.getText();
					String strMath = txtMath.getText();
					String strEng = txtEng.getText();
					int kor = Integer.parseInt(strKor);
					int math = Integer.parseInt(strMath);
					int eng = Integer.parseInt(strEng);
					StudentVO stu = new StudentVO(name,kor,math,eng);
					list.add(stu);
					
					txtName.clear();
					txtKor.clear();
					txtMath.clear();
					txtEng.clear();
					
					dialog.close();
					
				});
				
				
				Scene scene = new Scene(root);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();
			} catch (IOException e1) {}
		}); // END btnAdd event
		
		// barChart event 추가
		btnBarChart.setOnAction(event->{
			dialog = new Stage();
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnBarChart.getScene().getWindow());
			dialog.setTitle("막대 그래프");
			Parent root = null;
			try {
				root = FXMLLoader.load(
					getClass().getResource("BarChart.fxml")
				);
			} catch (IOException e1) {}
			
			BarChart<String,Integer> barChart
				= (BarChart<String,Integer>)root.lookup("#barChart");
			
			XYChart.Series<String, Integer> seriesKor 
								= new XYChart.Series<>();
			seriesKor.setName("국어");
			
			XYChart.Series<String, Integer> seriesMath 
								= new XYChart.Series<>();
			seriesMath.setName("수학");
			
			XYChart.Series<String, Integer> seriesEng 
								= new XYChart.Series<>();
			seriesEng.setName("영어");
			
			ObservableList<XYChart.Data<String,Integer>> listKor
				= FXCollections.observableArrayList();
			ObservableList<XYChart.Data<String,Integer>> listMath
			= FXCollections.observableArrayList();
			ObservableList<XYChart.Data<String,Integer>> listEng
			= FXCollections.observableArrayList();
			
			for(StudentVO stu : list) {
				listKor.add(new XYChart.Data<>(stu.getName(),stu.getKor()));
				listMath.add(new XYChart.Data<>(stu.getName(),stu.getMath()));
				listEng.add(new XYChart.Data<>(stu.getName(),stu.getEng()));
			}
			
			seriesKor.setData(listKor);
			seriesMath.setData(listMath);
			seriesEng.setData(listEng);
			
			barChart.getData().add(seriesKor);
			barChart.getData().add(seriesMath);
			barChart.getData().add(seriesEng);
			
			((Button)root.lookup("#btnClose")).setOnAction(e->{
				dialog.close();
			});
			
			dialog.setScene(new Scene(root));
			dialog.setResizable(false);
			dialog.show();
			
		}); // END BarChart
		
		// btnDelete event 추가 - 학생정보 삭제
		btnDelete.setOnAction(event->{
			StudentVO stu
			= tableView.getSelectionModel().getSelectedItem();
			int index 
			= tableView.getSelectionModel().getSelectedIndex();
			if(stu == null || index < 0) {
				showAlert("삭제할 학생의 정보를 선택해 주세요.");
				return;
			}
			list.remove(stu);
			//list.remove(index);
			
		}); // END btnDelete
		
		// btnUpdate event 처리
		btnUpdate.setOnAction(event->{
			// tableView의 행을 선택하고 수정 버튼 선택 시
			// 선택된 행의 StudentVO 정보를 가지고 와서
			// Form.fxml 파일을 이용하여 
			// 각 textField에 이름과 점수를 추가하고
			// 수정하려는 사용자가 기존의 값을 확인 할 수 있도록 함.
			// 사용자가 수정한 내용을 가지고 와서 list의 값을 변경한다.
			try {
				Parent root = FXMLLoader.load(
					getClass().getResource("Form.fxml")
				);
				
				StudentVO stu = 
					tableView.getSelectionModel().getSelectedItem();
				if(stu == null) {
					showAlert("정보를 수정할 학생을 선택해주세요!");
					return;
				}
				TextField txtName = (TextField)root.lookup("#txtName");
				txtName.setDisable(true); // 이름은 수정 불가
				TextField txtKor = (TextField)root.lookup("#txtKor");
				TextField txtMath = (TextField)root.lookup("#txtMath");
				TextField txtEng = (TextField)root.lookup("#txtEng");
				txtKor.requestFocus(); // 해당 요소에 focus 추가
				txtName.setText(stu.getName());
				txtKor.setText(String.valueOf(stu.getKor()));
				txtMath.setText(stu.getMath()+"");
				txtEng.setText(stu.getEng()+"");
				
				Button btnSave = (Button)root.lookup("#btnSave");
				btnSave.setOnAction(e->{
					Set<Node> textFields = root.lookupAll("TextField");
					for(Node textField : textFields) {
						TextField text = (TextField)textField;
						if(text.getText().trim().equals("")) {
							showAlert("작성된 값이 없습니다.");
							text.requestFocus();
							return;
						}
						
						if(!text.getId().equals("txtName")
							&&	
							!checkInt(text.getText())) {
							showAlert("정수를 입력하세요.");
							text.clear();
							text.requestFocus();
							return;
						}
					}
					
					// 검증 완료
					stu.setKor(Integer.parseInt(txtKor.getText()));
					stu.setMath(Integer.parseInt(txtMath.getText()));
					stu.setEng(Integer.parseInt(txtEng.getText()));
					int index 
						= tableView.getSelectionModel().getSelectedIndex();
					list.set(index, stu);
					dialog.close();
					
					
				});
				
				Button btnCancel = (Button)root.lookup("#btnCancel");
				btnCancel.setOnAction(e->dialog.close());
				
				
				dialog = new Stage();
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnUpdate.getScene().getWindow());
				dialog.setTitle("수정");
				dialog.setResizable(false);
				dialog.setScene(new Scene(root));
				dialog.show();
			} catch (IOException e1) {}
			
			
			
			
		});
	}
	// TextField에 작성된 문자열을 전달받아
	// 숫자로 작성되었는지 확인 (점수 인지 확인)
	public boolean checkInt(String data) {
		// 50
		// [5][0]
		// [53][48]
		// 일50
		// [43256][53][48]
		for(char c : data.toCharArray()) {
			if(c < 48 || c > 57) {
				System.out.println("숫자가 아닌녀석이 포함");
				return false;
			}
		}
		return true;
	}
	
	public void showAlert(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림");
		alert.setHeaderText(text);
		alert.show();
	}
	
	

}















