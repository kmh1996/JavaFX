package l1_property_listener;

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
			Parent root = FXMLLoader.load(
					getClass().getResource("Root.fxml")
				);
			primaryStage.setTitle("Listener");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (IOException e) {}
	}

	public static void main(String[] args) {
		launch(args);
	}
}




