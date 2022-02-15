package exam03_margin_padding;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		// 패딩 설정
		HBox hBox = new HBox();
		/*
		// 상, 우, 하, 좌
		Insets insets = new Insets(50,10,10,50);
		hBox.setPadding(insets);
		*/
		Button button = new Button();
		// 폭과 높이를 한번에 지정
		button.setPrefSize(100, 100);
		
		// button에 마진 설정
		HBox.setMargin(button, new Insets(30));
		hBox.getChildren().add(button);
		
		Scene scene = new Scene(hBox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}




