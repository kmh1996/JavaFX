package w2_task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class TaskController implements Initializable {

	@FXML private ProgressBar progressBar;
	@FXML private Label lblWork;
	@FXML private Button btnStart, btnStop, btnNext;
	
	// generic으로 task 작업이 끝나고 반환 받는 값의 타입을 지정
	// Void == void 반환하는 값이 없다는 걸 표현하는 클래스
	Task<Void> task;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnStart.setOnAction(event->{
			
			task = new Task<Void>() {
				
				@Override
				protected void cancelled() {
					updateMessage("취소됨");
				}
				
				@Override
				protected Void call() throws Exception {
					for(int i=0; i<=100; i++) {
						if(task.isCancelled()) {
							updateMessage("취소됨");
							System.out.println("isCancelled");
							break;
						}
						System.out.println(i);
						super.updateMessage(String.valueOf(i));
						super.updateProgress(i, 100);
						Thread.sleep(100);
					}
					return null;
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
		btnStop.setOnAction(event->{
			if(task != null) {
				// 상태값만 변경할뿐 task를 종료시키진 않음.
				task.cancel();
			}
			
		});
		btnNext.setOnAction(event->{
			try {
				Parent next = FXMLLoader.load(
					getClass().getResource("Next.fxml")
				);
				Stage stage = new Stage();
				stage.initModality(Modality.WINDOW_MODAL);
				Window w = btnNext.getScene().getWindow();
				stage.initOwner(w);
				stage.setScene(new Scene(next));
				stage.show();
				
			} catch (IOException e) {}
		});
	}

}




















