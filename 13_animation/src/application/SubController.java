package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SubController implements Initializable {

	@FXML private BorderPane subWrap;
	@FXML private Button btnPrev;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnPrev.setOnAction(event->{
			StackPane root 
				= (StackPane)btnPrev.getScene().getRoot();
			
			Timeline timeLine = new Timeline();
			// 0 ~ 1 투명도를 표현
			KeyValue keyValue = new KeyValue(
				subWrap.opacityProperty(),
				0
			);
			KeyFrame keyFrame = new KeyFrame(
				Duration.millis(1000),
				//timeLine keyFrame 효과가 완료 후 호출 되는 함수
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						root.getChildren().remove(subWrap);
					}
				},
				keyValue
			);
			timeLine.getKeyFrames().add(keyFrame);
			
			keyValue = new KeyValue(
				subWrap.rotateProperty(),
				720
			);
			keyFrame = new KeyFrame(
				Duration.millis(1000),
				keyValue
			);
			timeLine.getKeyFrames().add(keyFrame);
			
			keyValue = new KeyValue(
				subWrap.scaleXProperty(),
				0
			);
			keyFrame = new KeyFrame(
				Duration.millis(1000),
				keyValue
			);
			timeLine.getKeyFrames().add(keyFrame);
			
			keyValue = new KeyValue(
					subWrap.scaleYProperty(),
					0
				);
				keyFrame = new KeyFrame(
					Duration.millis(1000),
					keyValue
				);
			timeLine.getKeyFrames().add(keyFrame);
			
			timeLine.play();
			
		});
	}

}









