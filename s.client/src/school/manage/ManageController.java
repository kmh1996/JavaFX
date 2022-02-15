package school.manage;

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

public class ManageController implements Initializable {

	@FXML private Button btnStuM, btnCoM, btnSoM;
	
	Stage dialog;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnStuM.setOnAction(event->{
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			Parent parent = null;
			try {
				parent = FXMLLoader.load(
						getClass().getResource("stuMNG.fxml")
				);
			} catch (IOException e) {}
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		});
		
		btnCoM.setOnAction(event->{
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			Parent parent = null;
			try {
				parent = FXMLLoader.load(
						getClass().getResource("LectureList.fxml")
				);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		});
		btnSoM.setOnAction(event->{
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			Parent parent = null;
			try {
				parent = FXMLLoader.load(
						getClass().getResource("GradeList.fxml")
				);
			} catch (IOException e) {}
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		});
		

	}

}
