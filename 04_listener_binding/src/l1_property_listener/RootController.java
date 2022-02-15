package l1_property_listener;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootController 
				implements Initializable{
	
	@FXML private Slider slider;
	@FXML private ImageView img;
	@FXML private ToggleButton toggle;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(slider.valueProperty());
		double value = slider.valueProperty().doubleValue();
		System.out.println(value);
		setImageWidth(value);
		
		slider.valueProperty()
		.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println(observable);
				System.out.println("oldValue : " + oldValue);
				System.out.println("newValue : " + newValue);
			}
		});
		
		slider.valueProperty()
		.addListener((target,oldValue,newValue)->{
			setImageWidth(newValue.doubleValue());
		});
		
		toggle.selectedProperty()
		.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				System.out.println(newValue);
				if(!newValue) {
					String path = getClass().getResource("./animals/dog.jpg").toString();
					Image image = new Image(path);
					img.setImage(image);
				}else {
					img.setImage(new Image(getClass().getResource("./animals/cat3.jpg").toString()));
				}
			}
		});
	}
	
	// 전체 크기 : 350
	public void setImageWidth(double value) {
		double width = (350/100.0)*value;
		width = 3.5 * value;
		img.setFitWidth(width == 0 ? 1 : width);
	}
}






















