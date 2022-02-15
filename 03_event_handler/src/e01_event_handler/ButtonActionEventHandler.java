package e01_event_handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonActionEventHandler 
implements EventHandler<ActionEvent>{
	@Override
	public void handle(ActionEvent event) {
		System.out.println(event);
		Button button = (Button)event.getTarget();
		System.out.println("button id : " + button.getId());
		System.out.println("button Text : "+button.getText());
	}
}











