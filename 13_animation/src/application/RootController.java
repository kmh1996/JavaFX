package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class RootController implements Initializable {

	@FXML private BorderPane mainWrap;
	@FXML private Button btnNext;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnNext.setOnAction(event->{
			// stackPane에 Next 화면 추가
			try {
				StackPane root = (StackPane)btnNext.getScene().getRoot();
				Parent sub = FXMLLoader.load(
					getClass().getResource("Next.fxml")
				);
				root.getChildren().add(sub);
				// x좌표로 300px 만큼 이동
				sub.setTranslateX(300);
				
				Timeline timeLine = new Timeline();
				// 어떤 속성을 변경할지 정보를 저장하는 객체
				KeyValue keyValue = new KeyValue(
					sub.translateXProperty(),
					0
				);
				KeyFrame keyFrame = new KeyFrame(
					Duration.millis(1000),
					keyValue
				);
				timeLine.getKeyFrames().add(keyFrame);
				timeLine.play();
				
			} catch (IOException e) {}
		});
	}

}










