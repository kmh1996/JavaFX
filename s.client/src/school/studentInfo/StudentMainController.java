package school.studentInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StudentMainController implements Initializable {
	
	@FXML private Button btnInfo, btnEnroll, btnScoreInfo;
	
	Stage dialog;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnInfo.setOnAction(event->{
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			Parent parent = null;
			try {
				parent = FXMLLoader.load(
						getClass().getResource("MyInfo.fxml")
				);
			} catch (IOException e) {}
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		});
		
		btnEnroll.setOnAction(event->{
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			Parent parent = null;
			try {
				parent = FXMLLoader.load(
						getClass().getResource("ClassEnroll.fxml")
				);
			} catch (IOException e) {}
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		});
		
		btnScoreInfo.setOnAction(event->{
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			Parent parent = null;
			try {
				parent = FXMLLoader.load(
						getClass().getResource("GradeView.fxml")
				);
			} catch (IOException e) {}
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		});

	}

}
