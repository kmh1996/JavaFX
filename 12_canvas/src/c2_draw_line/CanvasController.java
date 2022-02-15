package c2_draw_line;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CanvasController implements Initializable {
	
	@FXML private Canvas canvas;
	@FXML private TextArea txtArea;
	@FXML private ColorPicker pick;
	@FXML private Slider slider;
	@FXML private Button btnClear;
	
	GraphicsContext gc;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);	// 선 색
		gc.setLineWidth(1);			// 선 굵기
		
		slider.setMin(1);			// 슬라이더 최소값 지정
		slider.setMax(100);			// 슬라이더 최대값 지정
		txtArea.setEditable(false); // == read only

		// canvas 위에서 마우스가 움직일 때
		canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double x = event.getSceneX();
				double y = event.getSceneY();
				txtArea.appendText("x : " + x +", y : "+y +"\n" );
			}
		});
		
		// 마우스가 눌러졌을 때
		canvas.setOnMousePressed(event->{
			gc.beginPath();	// 선그리기 시작
			gc.lineTo(event.getX(), event.getY());
		});
		
		// 마우스가 드래그 되는 동안
		canvas.setOnMouseDragged(event->{
			double x = event.getX();
			double y = event.getY();
			txtArea.appendText(x+":"+y+"\n");
			gc.lineTo(x, y);
			gc.stroke();
		});
		
		btnClear.setOnAction(event->{
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			pick.setValue(Color.WHITE);
			slider.setValue(1);
			gc.setLineWidth(1);
			gc.setStroke(Color.BLACK);
		});
		
		// colorPicker의 값이 변경되면 선 색상 변경
		pick.valueProperty()
		.addListener(new ChangeListener<Color>() {
			@Override
			public void changed(
					ObservableValue<? extends Color> observable, 
					Color oldValue, Color newValue) {
				gc.setStroke(newValue);
			}
		});
		// slider의 값이 변경되면 선 굵기 변경
		slider.valueProperty()
		.addListener((ob,oldValue,newValue)->{
			int value = newValue.intValue();
			double result = value/10;
			gc.setLineWidth(result);
		});
	}

}














