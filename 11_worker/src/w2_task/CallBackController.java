package w2_task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class CallBackController implements Initializable {
	
	@FXML private ProgressBar progressBar;
	@FXML private Label lblPath , lblWork;
	@FXML private Button btnChooser, btnStart, btnStop;

	Task<Integer> task;
	// 복사 진행할 파일 정보를 저장
	File selectedFile;
	// 복사해서 내보내기할 파일 정보
	File outputFile;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 파일 선택
		btnChooser.setOnAction(event->{
			FileChooser chooser = new FileChooser();
			Window w = btnChooser.getScene().getWindow();
			selectedFile = chooser.showOpenDialog(w);
			if(selectedFile != null) {
				lblPath.setText(selectedFile.getPath());
			}else {
				lblPath.setText("Path");
			}
		});
		// 복사 시작
		btnStart.setOnAction(event->{
			task = new Task<Integer>() {
				
				@Override
				protected Integer call() throws Exception {
					int result = 0;
					if(selectedFile == null) {
						updateMessage("파일없음");
						return result;
					}
					
					BufferedInputStream bis 
					= new BufferedInputStream(
							new FileInputStream(selectedFile)
						);
					String fileName = selectedFile.getName();
					System.out.println("fileName : " + fileName);
					outputFile = new File(fileName);
					System.out.println(outputFile.getAbsolutePath());
					BufferedOutputStream bos 
					= new BufferedOutputStream(
							new FileOutputStream(outputFile)
						);
					byte[] bytes = new byte[100];
					int readBytes = 0;
					while((readBytes = bis.read(bytes)) != -1) {
						result += readBytes;
						bos.write(bytes, 0 , readBytes);
						updateMessage(result+"/"+selectedFile.length()+"bytes");
						updateProgress(result,selectedFile.length());
						if(task.isCancelled()) {
							break;
						}
					}
					bos.flush();
					bos.close();
					bis.close();
					
					return result;
				}
				
				@Override
				protected void succeeded() {
					// 작업 정상 완료 시 호출
					int result = getValue();
					lblPath.setText(result+"");
				}
				@Override
				protected void cancelled() {
					// 작업 취소시 호출
					lblPath.setText("취소됨");
					if(outputFile != null) {
						outputFile.delete();
					}
				}
				@Override
				protected void failed() {
					// 작업 실패 시 호출
					lblPath.setText("실패");
				}
				
			};
			
			progressBar.progressProperty().bind(
				task.progressProperty()
			);
			lblWork.textProperty().bind(task.messageProperty());
			
			Thread t = new Thread(task);
			t.setDaemon(true);
			t.start();
			
		});
		
		// 복사 중지
		btnStop.setOnAction(event->{
			task.cancel();
		});
		
	}
}















