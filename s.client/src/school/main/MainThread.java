package school.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import school.find.FindController;
import school.student.StudentController;
import school.vo.StudentVO;

public class MainThread extends Thread {
	
	public StudentController studentController;
	public FindController findController;

	// Server에서 발신한 내용을 Receive
	@Override
	public void run() {
		ObjectInputStream ois = null;
		
		try {
			while(true) {
				if(isInterrupted()) {break;}
				Object obj = null;
				ois = new ObjectInputStream(
						ClientMain.socket.getInputStream()
					);
				if((obj = ois.readObject()) != null) { 
					System.out.println(obj);
					if(obj instanceof StudentVO) {
						// 회원 관련 요청 처리결과
						int order = ((StudentVO) obj).getOrder();
						switch(order) {
						case 0 : 
							studentController.receiveData((StudentVO)obj);
							break;
						case 1 : case 2 :
							findController.receiveData((StudentVO)obj);
							break;
						}
						
						
					}
				}
			}
		} catch (Exception e) {
			
		}
	}

	public void sendData(Object o) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(ClientMain.socket.getOutputStream());
			oos.writeObject(o);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
