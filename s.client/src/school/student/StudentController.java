package school.student;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import school.main.ClientMain;
import school.vo.StudentVO;

public class StudentController implements Initializable , StudentInterface{
	
	@FXML private Button btnLogin, btnFind;
	@FXML private TextField mNum;
	@FXML private PasswordField mPass;
	
	// 로그인 완료 시 추가 될 사용자 정보
	public static StudentVO user;
	
	Stage dialog;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientMain.thread.studentController = this;
		
		setLoginEvent();
		
		btnFind.setOnAction(event->{
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			Parent parent = null;
			try {
				parent = FXMLLoader.load(
						getClass().getResource("FindInfo.fxml")
				);
			} catch (IOException e) {}
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		});
		
	}

	@Override
	public void initLoginUI() {
		Platform.runLater(()->{
			mNum.clear();
			mPass.clear();
			mNum.requestFocus();
		});
	}
	
	@Override
	public void setLoginEvent() {
		
		mNum.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				mPass.requestFocus();
			}
		});
		
		mPass.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				btnLogin.fire();
			}
		});
		
		btnLogin.setOnAction(event->{
			String num = mNum.getText().trim();
			String pass = mPass.getText().trim();
			
			if(num.equals("")) {
				mNum.requestFocus();
			}else if(pass.equals("")) {
				mPass.requestFocus();
			}else {
				StudentVO student = new StudentVO(num,pass);
				student.setOrder(0);
				ClientMain.thread.sendData(student);
			}
		});
			
	}

	@Override
	public void setLoginCheck(StudentVO vo) {
		if(vo.isSuccess()) {
			System.out.println("로그인 성공");
			Platform.runLater(()->{
				// 로그인 완료된 사용자 정보 저장
				 user = vo;
				 System.out.println(user.getmNum());
				 //////////////학번 파일 만들기 시작//////////////////
				 String dir = "C:/Temp";
					String fileName = "studentId.txt";
					String path = dir+"/"+fileName;
					
					File file = new File(dir);
					// 해당 위치에 폴더나 파일이 존재하면 true
					// 존재하지 않으면 false를 반환
					if(!file.exists()) {
						// 경로에 있는 모든 디렉토리를 생성
						file.mkdirs();
						// 마지막에 정의된 디렉토리를 생성
						//file.mkdir();
						System.out.println("디렉토리 생성완료");
					}
					
					file = new File(dir,fileName);
					try {
						file.createNewFile();
						System.out.println("파일 생성 완료");
					} catch (IOException e) {
						e.printStackTrace();
					}

					try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
					    writer.write(user.getmNum());
					} catch (IOException e) {
					    e.printStackTrace();
					}
				 ////////////// 학번 파일 만들기 끝////////////////
				// 무대이동
				showLogin(vo);
			});
		}else {
			System.out.println("로그인 실패");
			// textField 초기화
			initLoginUI();
		}
		
	}

	@Override
	public void receiveData(StudentVO vo) {
		// 로그인
		switch(vo.getOrder()) {
		case 0 :
			System.out.println("로그인 요청 처리 결과");
			setLoginCheck(vo);
		break;
		}
	} 

	private void showLogin(StudentVO vo) {
		
		if(vo != null && !vo.getmNum().equals("root")) {
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			Parent parent = null;
			try {
				parent = FXMLLoader.load(
						getClass().getResource("/school/studentInfo/StudentMain.fxml")
				);
			} catch (IOException e) {}
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
			// 관리자 
		}else if(vo.getmNum().equals("root") && vo.getmPw().equals("root")){
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			Parent parent = null;
			try {
				parent = FXMLLoader.load(
						getClass().getResource("Manage.fxml")
				);
			} catch (IOException e) {}
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("경고");
			alert.setHeaderText("잘못된 정보입니다.");
			alert.setContentText("다시 입력해주세요.");
			alert.show();
		}
	}

}
