package second;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.MainController;

public class SecondController implements Initializable {

	@FXML private Button btn1, btn2;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn1.setText("_Main");
		btn1.setMnemonicParsing(true);
		btn1.setOnAction(event->{
			System.out.println("Main 으로 이동");
			FXMLLoader loader 
				= new FXMLLoader(
					getClass().getResource("../main/Root.fxml")	
				);
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {}
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			
			MainController controller = loader.getController();
			controller.setStage(stage);
			stage.show();
			
			// stage 정보를 가지고 옴
			Stage secondStage =
						(Stage)btn1.getScene().getWindow();
			secondStage.close();
			
		});
		
		btn2.setText("_A");
		btn2.setMnemonicParsing(true);
		btn2.setOnAction(event->{
			System.out.println("btn2 click");
		});
		
	}

}




