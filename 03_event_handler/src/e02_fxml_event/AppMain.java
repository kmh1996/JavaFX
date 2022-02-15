package e02_fxml_event;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		System.out.println("Start 호출");
		try {
			HBox root = FXMLLoader.load(getClass().getResource("Root.fxml"));
			ObservableList<Node> list = root.getChildren();
			for(Node n : list) {
				if(n instanceof Button) {
					Button btn = (Button)n;
					if("btn1".equals(btn.getId())) {
						btn.setOnAction(event->{
							System.out.println(btn.getId()+" event");
						});
					}
				}
			}
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {}
	}

	public static void main(String[] args) {
		launch(args);
	}
}






