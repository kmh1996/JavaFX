package c1_anchor_pane;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		URL url = getClass().getResource("Root.fxml");
		System.out.println(url.getPath());
		url = AppMain.class.getResource("Root.fxml");
		System.out.println(url.getPath());
		// 모든 Container의 최상위 객체 Parent
		try {
			//Parent root = FXMLLoader.load(url);
			AnchorPane root = FXMLLoader.load(url);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("AnchorPane");
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("fxml 파일을 읽을 수 없음");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}




