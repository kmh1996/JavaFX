package client_chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ClientController implements Initializable {

	@FXML private TextArea txtDisplay;
	@FXML private TextField txtIp, txtNick,txtInput;
	@FXML private ListView<String> listView;
	@FXML private Button btnConn, btnSend;
	
	// 연결된 서버 소켓 정보
	Socket server;
	// 연결 요청을 보낼 server ip 주소
	InetAddress ip;
	// 사용자 닉네임
	String nickName;
	
	// 서버로 데이터 출력
	PrintWriter pw;
	// 서버에서 데이터를 읽음
	BufferedReader br;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(()->{
			txtIp.requestFocus();
		});
		
		btnConn.setOnAction(event->{
			// client 시작
			
			try {
				String textIP = txtIp.getText().trim();
				if(textIP.equals("")) {
					displayText("아이피 주소를 확인해 주세요.");
					txtIp.requestFocus();
					return;
				}
				// 문자열 ipv4주소 값을 받아서 address로 반환
				// 정상적인 ip주소나 Domain이 아니면 Exception 발생
				ip = InetAddress.getByName(textIP);
				
				String nick = txtNick.getText().trim();
				if(nick.equals("")) {
					displayText("닉네임을 작성해주세요.");
					txtNick.requestFocus();
					return;
				}
				
				nickName = nick;
				startClient();
			} catch (UnknownHostException e) {
				displayText("사용할 수 없는 주소입니다. 주소를 확인해주세요.");
				txtIp.requestFocus();
			}
		}); // 종료 btnConn event
		
		// send event
		btnSend.setOnAction(event->{
			String text = txtInput.getText().trim();
			if(text.equals("")) {
				displayText("메세지를 작성해 주세요");
				return;
			}
			send(1,text);
		}); // END send event
		
		// txtIp textField 에서 enter키 눌리면 
		// 연결 btnConn 버튼 이벤트 수행
		txtIp.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.ENTER)) {
					// btnConn 버튼 ActionEvent 발생
					btnConn.fire();
				}
			}
		});
		
		txtNick.setOnKeyPressed(e->{
			if(e.getCode().equals(KeyCode.ENTER)) {
				btnConn.fire();
			}
		});
		
		txtInput.setOnKeyPressed(e->{
			if(e.getCode().equals(KeyCode.ENTER)) {
				btnSend.fire();
			}
		});
		
		listView.setOnMouseClicked(event->{
			if(event.getClickCount() == 2) {
				String nickName 
				= listView.getSelectionModel().getSelectedItem();
				System.out.println(nickName);
				if(nickName == null) {
					displayText("먼저 닉네임을 선택해주세요.");
					return;
				}
				
				if(nickName.equals(this.nickName)) {
					displayText("자신은 선택이 안됩니다.");
					return;
				}
				
				txtInput.clear();
				txtInput.setText("/w "+nickName+" ");
				txtInput.requestFocus();
			}
		});
		
	}
	
	// client 시작
	public void startClient() {
		try {
			server = new Socket(ip,5001);
			displayText("[연결완료 : "+server.getRemoteSocketAddress()+"]");
			
			pw = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									server.getOutputStream()
									)
							),true);
			
			br = new BufferedReader(
					new InputStreamReader(
						server.getInputStream()
					)
				);
			
			Platform.runLater(()->{
				btnSend.setDisable(false);
			});
			send(0,nickName);
		} catch (IOException e) {
			displayText("[서버 연결 안됨 IP를 확인해보세요.-"+ip+"]");
			stopClient();
			return;
		}
		receive();
	}
	// client 종료
	public void stopClient() {
		try {
			displayText("[Server와 연결 종료]");
			if(server != null && !server.isClosed()) {
				server.close();
			}
		} catch (IOException e) {}
	}
	
	// 서버에 정보를 전달
	// num == 0 : 닉네임 전달
	// num == 1 : 메세지 전달
	public void send(int num,String text) {
		// 0|닉네임
		// 1|메세지
		pw.println(num+"|"+text);
		displayText("[보내기 완료]"+text);
		txtInput.clear();
		txtInput.requestFocus();
	}
	
	// 서버에서 메세지를 전달 받음
	public void receive() {
		new Thread(()->{
			while(true) {
				try {
					String receiveData = br.readLine();
					// 0|사용자목록
					// 1|nickName+message
					String[] data = receiveData.split("\\|");
					// 1|message
					// 0|nickname,nickname,nickname,nickname,
					String code = data[0];
					String text = data[1];
					if(code.equals("0")) {
						// listView 사용자 목록 갱신
						String[] list = text.split("\\,");
						Platform.runLater(()->{
							listView.setItems(
								FXCollections.observableArrayList(
									// 매개변수로 전달 받은 배열을
									// List type으로 변환
									Arrays.asList(list)
								)
							);
						});
					}else if(code.equals("1")) {
						// message 출력
						displayText(text);
					}
				} catch (IOException e) {
					stopClient();
					break;
				}
			}
		}).start();
	}
	
	// textArea에 text 추가
	public void displayText(String text) {
		Platform.runLater(()->{
			txtDisplay.appendText(text+"\n");
		});
	}
}









