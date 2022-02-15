package school.find;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import school.main.ClientMain;
import school.vo.StudentVO;

public class FindController implements Initializable , FindInterface{
	
	@FXML private TextField mName, mBirth, mNum, mEmail;
	@FXML private Button btnNumF, btnPassF;
	@FXML ToggleGroup group;
	
	Stage dialog;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientMain.thread.findController = this;
		setFindNumEvent();
		setFindPassEvent();
	}

	@Override
	public void initFindNumUI() {
		Platform.runLater(()->{
			mName.clear();
			mBirth.clear();
			mName.requestFocus();
		});
		
	}

	@Override
	public void setFindNumEvent() {
		mName.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				mBirth.requestFocus();
			}
		});
		
		mBirth.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				btnNumF.fire();
			}
		});
		
		btnNumF.setOnAction(event->{
			System.out.println("click");
			String name = mName.getText().trim();
			int birth = Integer.parseInt(mBirth.getText());
			RadioButton btn = (RadioButton) group.getSelectedToggle();
			//System.out.println(btn.getUserData());
			int gender = Integer.parseInt((String) btn.getUserData());
			
			if(name.equals("")) {
				mName.requestFocus();
			}else if(mBirth.getText().equals("")) {
				mBirth.requestFocus();
			}else {
				StudentVO student = new StudentVO(name,birth,gender);
				student.setOrder(1);
				ClientMain.thread.sendData(student);
			}
		});
		
	}

	@Override
	public void setFindNumCheck(StudentVO vo) {
		if(vo.isSuccess()) {
			System.out.println("학번찾기 성공");
			Platform.runLater(()->{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("알림");
				alert.setHeaderText("알려드립니다.");
				alert.setContentText("입력하신 정보의 학번은 : "+vo.getmNum());
				alert.show();
			});
		}else {
			System.out.println("학번찾기 실패");
			// textField 초기화
			initFindNumUI();
		}
		
	}

	@Override
	public void initFindPassUI() {
		mNum.clear();
		mEmail.clear();
		mNum.requestFocus();
		
	}

	@Override
	public void setFindPassEvent() {
		mNum.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				mEmail.requestFocus();
			}
		});
		
		mEmail.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				btnPassF.fire();
			}
		});
		
		btnPassF.setOnAction(event->{
			System.out.println("click");
			String num = mNum.getText().trim();
			String email = mEmail.getText().trim();
			
			if(num.equals("")) {
				mNum.requestFocus();
			}else if(email.equals("")) {	
				mEmail.requestFocus();
			}else {
				StudentVO student = new StudentVO(2,num,email);
				ClientMain.thread.sendData(student);
			}
		});
		
	}

	@Override
	public void setFindPassCheck(StudentVO vo) {
		if(vo.isSuccess()) {
			System.out.println("비번찾기 성공");
			Platform.runLater(()->{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("알림");
				alert.setHeaderText("알려드립니다.");
				alert.setContentText("입력하신 이메일 주소로 비밀번호를 전송해드렸습니다. \n"+vo.getmEmail());
				alert.show();
			});
		}else {
			System.out.println("학번찾기 실패");
			// textField 초기화
			initFindPassUI();
		}
		
	}

	@Override
	public void receiveData(StudentVO vo) {
		System.out.println(vo);
		int order = vo.getOrder();
		switch(order) {
		case 1 :
			// 학번 찾기
			setFindNumCheck(vo);
			break;
		case 2 : 
			// 비번 찾기
			setFindPassCheck(vo);
			break;
		}
	}

}
