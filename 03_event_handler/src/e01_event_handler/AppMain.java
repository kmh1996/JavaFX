package e01_event_handler;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage stage) {
		HBox hbox = new HBox();
		//hbox.setPrefSize(200,50);
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		hbox.setSpacing(10.0);
		hbox.setPadding(new Insets(15));
		
		Button btn1 = new Button("버튼1");
		// 특정 요소에 접근하기 위한 구별 값
		btn1.setId("btn1");
		btn1.setPrefSize(200, 100);
		btn1.setAlignment(Pos.TOP_LEFT);
		// ActionEvent 발생시 실행할 Handler 등록
		ButtonActionEventHandler handler
			= new ButtonActionEventHandler();
		// 구현 객체 이용
		btn1.setOnAction(handler);
		
		Button btn2 = new Button("버튼2");
		// 익명 객체 이용
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("버튼2 click!@");
			}
		});
		
		Button btn3 = new Button();
		btn3.setText("버튼3");
		
		// ramda 표현식 이용
		btn3.setOnAction(event->{
			System.out.println("버튼 3 클릭!");
		});
		
		/*
		hbox.getChildren().add(btn1);
		hbox.getChildren().add(btn2);
		hbox.getChildren().add(btn3);
		*/
		// 자식 노드의 목록을 저장하는 List
		ObservableList<Node> list = hbox.getChildren();
		//list.add(btn1);
		// list.addAll(btn1,btn2,btn3);
		
		Button[] buttons = new Button[] {btn1,btn2,btn3};
		list.addAll(buttons);
		
		Scene scene = new Scene(hbox);
		stage.setScene(scene);
		stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}






