package c4_thread;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RootController implements Initializable {
	
	@FXML private Canvas bgCanvas,canvas1,canvas2,canvas3,canvas4;
	@FXML private Button btnStart;
	
	GraphicsContext backgroundGC,gc1,gc2,gc3,gc4;
	boolean isStart = true;
	
	int x,x1,x2,x3,x4;
	
	ArrayList<Thread> threadList = new ArrayList<>();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initBackGround();
		initCar();
		btnStart.setOnAction(event->{
			isStart = true;
			for(Thread t : threadList) {
				t.start();
			}
			btnStart.setDisable(true);
		});
	}
	
	public void initBackGround() {
		
		threadList.clear();
		
		backgroundGC = bgCanvas.getGraphicsContext2D();
		Image image = new Image(
			getClass().getResource("img/sky.jpg").toString()
		);
		backgroundGC.drawImage(image, 0, 0, 600, 400);
		backgroundGC.drawImage(image, 600, 0, 600, 400);
		
		Thread thread = new Thread(()->{
			x = 0;
			while(isStart) {
				backgroundGC.clearRect(0, 0, bgCanvas.getWidth(), bgCanvas.getHeight());
				backgroundGC.drawImage(image,x,0,600,400);
				backgroundGC.drawImage(image,x+600,0,600,400);
				x = x - 10;
				if(x == -600) {
					x = 0;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		});
		threadList.add(thread);
		
	}
	
	public void initCar(){
		gc1 = canvas1.getGraphicsContext2D();
		Image image1 = new Image(
			getClass().getResource("img/car1.png").toString()
		);
		gc1.clearRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
		gc1.drawImage(image1, 0, 40);
		gc2 = canvas2.getGraphicsContext2D();
		Image image2 = new Image(
			getClass().getResource("img/car5.png").toString()
		);
		gc2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
		gc2.drawImage(image2, 0, 40);
		gc3 = canvas3.getGraphicsContext2D();
		Image image3 = new Image(
			getClass().getResource("img/car7.png").toString()
		);
		gc3.clearRect(0, 0, canvas3.getWidth(), canvas3.getHeight());
		gc3.drawImage(image3, 0, 40);
		gc4 = canvas4.getGraphicsContext2D();
		Image image4 = new Image(
			getClass().getResource("img/car2.png").toString()
		);
		gc4.clearRect(0, 0, canvas4.getWidth(), canvas4.getHeight());
		gc4.drawImage(image4, 0, 40);
		
		Thread t1 = new Thread(()->{
			x1 = 0;
			while(isStart) {
				int a = (int)(Math.random()*15)+5;
				Platform.runLater(()->{
					gc1.clearRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
					gc1.drawImage(image1, x1, 40);
				});
				x1 += a;
				if(x1+image1.getWidth() >= 600) {
					isStart = false;
					Platform.runLater(()->{
						showDialog(1);
					});
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
			}
		});
		threadList.add(t1);
		
		Thread t2 = new Thread(()->{
			x2 = 0;
			while(isStart) {
				int a = (int)(Math.random()*15)+5;
				Platform.runLater(()->{
					gc2.clearRect(0, 0, canvas2.getWidth(), canvas1.getHeight());
					gc2.drawImage(image2, x2, 40);
				});
				x2 += a;
				if(x2+image2.getWidth() >= 600) {
					isStart = false;
					Platform.runLater(()->{
						showDialog(2);
					});
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
			}
		});
		threadList.add(t2);
		
		Thread t3 = new Thread(()->{
			x3 = 0;
			while(isStart) {
				int a = (int)(Math.random()*15)+5;
				Platform.runLater(()->{
					gc3.clearRect(0, 0, canvas3.getWidth(), canvas1.getHeight());
					gc3.drawImage(image3, x3, 40);
				});
				x3 += a;
				if(x3+image3.getWidth() >= 600) {
					isStart = false;
					Platform.runLater(()->{
						showDialog(3);
					});
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
			}
		});
		threadList.add(t3);
		
		Thread t4 = new Thread(()->{
			x4 = 0;
			while(isStart) {
				int a = (int)(Math.random()*15)+5;
				Platform.runLater(()->{
					gc4.clearRect(0, 0, canvas4.getWidth(), canvas1.getHeight());
					gc4.drawImage(image4, x4, 40);
				});
				x4 += a;
				if(x4+image4.getWidth() >= 600) {
					isStart = false;
					Platform.runLater(()->{
						showDialog(4);
					});
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
			}
		});
		threadList.add(t4);
		
	}

	public void showDialog(int num) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("승리");
		alert.setHeaderText(num+"번 경주차 승리");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			initBackGround();
			initCar();
			btnStart.setDisable(false);
		}else {
			Stage stage = 
			(Stage)btnStart.getScene().getWindow();
			stage.close();
		}
	}
	
}

















