package school.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

import school.vo.StudentVO;


public class Client {

	Socket client;
	// 로그인 완료된 회원 정보
	StudentVO student;

	public Client(Socket client) {
		this.client = client;
		serverClientReceive();
	}
	
	// client에서 출력되는 정보를 읽어들임
	public void serverClientReceive() {
		MainController.threadPool.submit(new Runnable() {
			@Override
			public void run() {
				ObjectInputStream ois = null;
				try {
					Object obj;
					while(true) {
						ois = new ObjectInputStream(
							client.getInputStream()
						);
						if((obj = ois.readObject()) != null) {
							System.out.println("요청");
							if(obj instanceof StudentVO) {
								System.out.println("회원관련 요청");
								desposeMember((StudentVO)obj);
							}
						}
					}
				} catch (Exception e) {
					removeClient();
				}
			}
			
		});
	}
	// 회원관련 요청 처리
	public void desposeMember(StudentVO obj) {
		System.out.println("receive StudentVO "+obj);
		int order = obj.getOrder();
		System.out.println(order);
		switch(order) {
		case 0 : 
			StudentVO vo = MainController.studentDAO.loginStudent(obj);
			if(vo.isSuccess()) {
				// 회원 정보 등록
				student = obj = vo;
				System.out.println("LOGIN - "+ student);
			}
			break;
		case 1 :
			vo = MainController.studentDAO.findStudentNum(obj);
			if(vo.isSuccess()) {
				System.out.println("학번찾기");
			}
			
			break;
		case 2 :
			vo = MainController.studentDAO.findStudentPass(obj);
			if(vo.isSuccess()) {
				System.out.println("비번찾기");
			}
			break;

		}
		System.out.println("send MemberVO " +obj);
		sendData(obj);
		}

	// 연결되어 있는 client에 정보를 출력
	public synchronized void sendData(Object data) {
		ObjectOutputStream oos = null;
		
		try {
			OutputStream os = client.getOutputStream();
			oos = new ObjectOutputStream(os);
			oos.writeObject(data);
			oos.flush();
		} catch (IOException e) {
			removeClient();
		}
	}
	
	// client 연결 종료
	public void removeClient() {
		String ip = client.getInetAddress().getHostAddress();
		MainController.mc.appendText(new Date()+ip+"연결종료");
		System.out.println("Client 연결 종료: "+ ip);
		synchronized(MainController.clients) {
			MainController.clients.remove(this);
		}
		
		if(client != null && !client.isClosed()) {
			try {
				client.close();
			} catch (IOException e) {}
		}
	}
}

