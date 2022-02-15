package c3_omok;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppMain extends Application {
	
	Canvas canvas;
	GraphicsContext gc;
	// 돌이 놓이는 위치를 저장할 이차원 배열
	int[][] map = null;
	int doll_state = 1;	//(흑 1, 백 2)
	
	Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(
				getClass().getResource("Omok.fxml")
			);
			
			canvas = (Canvas)root.lookup("#canvas");
			gc = canvas.getGraphicsContext2D();
			this.primaryStage = primaryStage;
			
			initCanvas();
			
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {}
	}
	
	// 게임 초기화
	private void initCanvas() {
		doll_state = 1;
		map = new int[19][19];
		
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for(int i=30; i<=canvas.getWidth()-30; i+=30) {
			gc.strokeLine(30, i, canvas.getWidth()-30, i);
		}
		
		for(int i = 30; i<=canvas.getHeight()-30; i+=30) {
			gc.strokeLine(i, 30, i, canvas.getHeight()-30);	
		}
		
		gc.setStroke(new Color(0,0,0,1));
		
		canvas.setOnMouseClicked(event->{
			int x = (int)(event.getX()+15)/30;
			int y = (int)(event.getY()+15)/30;
			System.out.println(x+":"+y);
			draw(x,y);
		});
	}

	private void draw(int x,int y) {
		// y는 행의 좌표
		// x는 열의 좌표
		
		int offsetX = (x*30)-15;
		int offsetY = (y*30)-15;
		
		if(map[--y][--x] != 0) {
			System.out.println("이미 돌이 있는 자리입니다.");
			return;
		}
		
		if(doll_state == 1) {
			System.out.println("검은 돌");
			gc.setFill(Color.BLACK);
			map[y][x] = 1;
		}else {
			System.out.println("흰 돌");
			gc.setFill(Color.WHITE);
			gc.setStroke(Color.BLACK);
			map[y][x] = 2;
		}
		
		gc.strokeOval(offsetX,offsetY,28,28);
		gc.fillOval(offsetX,offsetY,28,28);
		
		victory(x,y);
		
		doll_state = (doll_state == 1) ? 2 : 1;
		
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				System.out.printf("[%d]",map[i][j]);
			}
			System.out.println();
		}
	}
	// 승리 확인
	private void victory(int x, int y) {
		int cnt = 1, cnt2 = 1, cnt3 = 1, cnt4 = 1;
		int i;
		// 배열에서 열(가로 뒤) 동일한 색의 돌 개수 찾기
		for(i=1; i<5; i++) {
			if(x+i < 19 && map[y][x+i] == doll_state) {
				cnt++;
			}else {
				break;
			}
		}
		
		// 배열에서 열(가로 앞) 개수 세기
		for(i=1; i<5; i++) {
			if(x-i >=0 && map[y][x-i] == doll_state) {
				cnt++;
			}else {
				break;
			}
		}
		
		// 배열에서 행(세로 뒤) 개수 세기
		for(i=1; i<5; i++) {
			if(y+i < 19 && map[y+i][x] == doll_state) {
				cnt2++;
			}else {
				break;
			}
		}
		// 배열에서 행(세로 위) 개수 세기
		for(i=1; i<5; i++) {
			if(y-i >= 0 && map[y-i][x] == doll_state) {
				cnt2++;
			}else {
				break;
			}
		}
		
		// 왼쪽 대각선 위
		for(i = 1; i < 5; i++) {
			if(x-i>=0 && y-i >=0 && map[y-i][x-i] == doll_state) {
				cnt3++;
			}else {
				break;
			}
		}
		
		// 오른쪽 대각선 아래
		for(i = 1; i<5; i++) {
			if(x+i < 19 && y+i < 19 && map[y+i][x+i] == doll_state) {
				cnt3++;
			}else {
				break;
			}
		}
		
		//왼쪽 대각선 아래
		for(i=1; i<5; i++) {
			if(y+i < 19 && x-i >=0 && map[y+i][x-i] == doll_state) {
				cnt4++;
			}else {
				break;
			}
		}
		
		// 오른쪽 대각선 위
		for(i=1; i<5; i++) {
			if(x+i <19 && y-i >=0 && map[y-i][x+i] == doll_state) {
				cnt4++;
			}else {
				break;
			}
		}
		
		System.out.println(cnt);
		System.out.println(cnt2);
		System.out.println(cnt3);
		System.out.println(cnt4);
		if(cnt >= 5 || cnt2 >= 5 || cnt3 >= 5 || cnt4 >= 5) {
			String text = doll_state == 1 ? "흑" : "백";
			System.out.println(text+" 승리");
			showDailog(text);
		}
	}

	private void showDailog(String text) {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(primaryStage);
		AnchorPane a = new AnchorPane();
		a.setPrefSize(200, 100);
		Label label = new Label(text+"승리");
		label.setStyle("-fx-font-size:35;");
		label.setLayoutX(10);
		label.setLayoutY(25);
		Button btn = new Button("확인");
		btn.setLayoutX(140);
		btn.setLayoutY(40);
		
		btn.setOnAction(event->{
			stage.close();
			initCanvas();
		});
		
		a.getChildren().addAll(label,btn);
		Scene scene = new Scene(a);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}






