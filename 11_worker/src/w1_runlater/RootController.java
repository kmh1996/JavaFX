package w1_runlater;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RootController implements Initializable {
	
	@FXML private Label lblTime;
	@FXML private Button btnStart, btnStop;
	
	private SimpleDateFormat sdf;
	
	private boolean isRun = true;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sdf = new SimpleDateFormat("HH:mm:ss");
		
		btnStart.setOnAction(event->{
			
			isRun = true;
			
			Thread t = new Thread(()->{
				while(isRun){
					Platform.runLater(()->setTime(new Date()));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
			});
			t.start();
			
			/*
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while(isRun) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								setTime(new Date());
							}
						});
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
					}
				}
			});
			t.start();
			*/
		});
		
		btnStop.setOnAction(event->{
			isRun = false;
		});
	}
	
	public void setTime(Date date) {
		String time = sdf.format(date);
		lblTime.setText(time);
	}
	

}








