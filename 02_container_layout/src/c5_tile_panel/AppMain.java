package c5_tile_panel;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root 
			= FXMLLoader.load(getClass().getResource("Root.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			// 무대의 크기 변경 불가
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {}
	}

	public static void main(String[] args) {
		launch(args);
	}
}




