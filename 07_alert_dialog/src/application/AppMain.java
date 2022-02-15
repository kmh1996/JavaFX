package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		VBox vBox = new VBox();
		vBox.setPrefWidth(200);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().add(new Button("알림"));
		vBox.getChildren().add(new Button("경고"));
		vBox.getChildren().add(new Button("오류"));
		vBox.getChildren().add(new Button("확인"));
		vBox.getChildren().add(new Button("입력"));
		vBox.getChildren().add(new Button("Custom"));
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10));
		
		for(Node n : vBox.getChildren()){
			Button btn = (Button)n;
			btn.setMaxWidth(Double.MAX_VALUE);
			btn.setOnAction(event->{
				handleAlert(btn.getText());
			});
		}
		
		primaryStage.setScene(new Scene(vBox));
		primaryStage.show();
	}
	
	public void handleAlert(String text) {
		switch(text) {
			case "알림" :
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("알림");
				alert.setHeaderText("알림창입니다.\n헤더정보입니다.");
				alert.setContentText("이곳은 Content 정보입니다.\n내용을 확인하세요.");
				alert.show();
				break;
			case "경고" :
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("경고");
				alert.setHeaderText("Header Text");
				alert.setContentText("Content Text");
				alert.show();
				break;
			case "오류" :
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("오류");
				alert.setHeaderText("Header Text");
				alert.setContentText("Content Text");
				alert.show();
				break;
			case "확인" :
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("결제 확인");
				alert.setHeaderText("결제 정보입니다.");
				alert.setContentText("확인시 5,000,000원 결제됩니다.");
				Optional<ButtonType> result = alert.showAndWait();
				// 확인 버튼 선택
				if(result.get() == ButtonType.OK) {
					System.out.println("OK 버튼 선택");
				}else if(result.get() == ButtonType.CANCEL){
					// 취소 버튼 + 닫기버튼 + alt+f4
					System.out.println("취소 선택");
				}else {
					System.out.println("모르는 버튼");
				}
				break;
			case "입력" :
				TextInputDialog dialog 
					= new TextInputDialog("김재관");
				dialog.setTitle("이름 입력");
				dialog.setHeaderText("Text Input Dialog");
				dialog.setContentText("이름을 입력해주세요.");
				Optional<String> res = dialog.showAndWait();
				if(res.isPresent() && !res.get().trim().equals("")) {
					// 텍스트 작성하고 확인버튼 선택
					/*
					res.ifPresent(new Consumer<String>() {
						@Override
						public void accept(String t) {
							System.out.println(t);
						}
					});
					*/
					res.ifPresent(name->{
						System.out.println("작성하신 이름은 : " + name+"입니다.");
					});
					
				}else {
					System.out.println("작성된 값이 없습니다.");
				}
				break;
			default : 
				alert = new Alert(
						AlertType.INFORMATION,
						"이곳은 Content 정보입니다.\n내용을 확인하세요.",
						ButtonType.OK,
						ButtonType.CANCEL,
						ButtonType.NEXT
						);
				alert.setTitle("알림");
				Optional<ButtonType> result1 = alert.showAndWait();
				if(result1.get() == ButtonType.OK) {
					System.out.println("확인");
				}else if(result1.get() == ButtonType.CANCEL) {
					System.out.println("취소");
				}else {
					System.out.println("NEXT");
				}
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}




