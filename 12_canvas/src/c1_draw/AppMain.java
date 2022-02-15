package c1_draw;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppMain extends Application {
	
	// 도화지
	private Canvas canvas;
	
	// 그리기 도구
	private GraphicsContext gc;
	
	
	

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(
				getClass().getResource("Draw.fxml")
			);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			canvas = (Canvas)root.lookup("#canvas");
			// canvas에 그려줄 그리기 도구
			gc = canvas.getGraphicsContext2D();
			draw();
		} catch (IOException e) {}
	}

	private void draw() {
		// 채우기 색상
		gc.setFill(Color.RED);
		// 가로위치 , 세로위치, 너비(폭) , 높이
		gc.fillRect(100, 100, 100, 100);
		// 외곽선만 있는 사각형
		gc.setStroke(Color.BLUE);
		gc.strokeRect(150, 150, 100, 100);
		
		// 호를 그리는 도구
		gc.strokeOval(100, 100, 50, 50);
		gc.strokeOval(150, 150, 50, 50);
		
		// 선그리기 도구
		//(시작X,시작Y,끝X,끝Y)
		gc.strokeLine(200, 200, 200, 250);
		
		// 여러 선을 이어서 그려주는 녀석
		gc.beginPath();	// 선그리기 시작
		gc.setStroke(new Color(0.5,0.5,0.5,1));
		gc.lineTo(10, 10);
		gc.lineTo(20, 30);
		gc.lineTo(50, 40);
		gc.lineTo(10, 10);
		gc.stroke();
		
		// 이미지를 그리기 도구
		Image image = new Image(
			getClass().getResource("cat3.jpg").toString()
		);
		// gc.drawImage(image, 300, 300);
		gc.drawImage(image, 300, 300, 100, 100);
		
		gc.clearRect(0, 0, 100, 100);
		gc.clearRect(0, 0, 
						canvas.getWidth(), 
						canvas.getHeight()
					); 
		
		Thread t = new Thread(new Runnable(){
			public void run() {
			for(int i=0; i<canvas.getWidth();i++) {
				final int j = i;
				try {
					Platform.runLater(()->{
						gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						gc.drawImage(image, j, j,100,100);
					});
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
			}
		});
		t.start();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}









