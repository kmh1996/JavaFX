package e04_bundle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BundleController implements Initializable {
	
	@FXML
	private Button btnAccept;
	@FXML
	private Button btnReload;
	@FXML
	private Button btnCancel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("연결된 FXML : "+ location);
		
		for(String key : resources.keySet()) {
			System.out.println(key+" : "+resources.getString(key));
		}
		
		btnAccept.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleAction(event);
			}
		});
		
		btnReload.setOnAction(event->handleAction(event));
	}
	
	public void handleAction(ActionEvent e) {
		// event가 발생 한 요소
		Button btn = (Button)e.getTarget();
		System.out.println(btn.getId());
		System.out.println(btn.getText());
		System.out.println(e.getEventType());
	}

}












