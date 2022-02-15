package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;

public class MainController implements Initializable {

	@FXML private Button btn1, btn2, btn3;
	
	private Stage stage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn1.setText("_A 버튼");
		btn1.setMnemonicParsing(false);
		btn1.setOnAction(event->{
			System.out.println("btn1 선택");
		});
		
		btn2.setText("_S 버튼");
		btn2.setOnAction(event->{
			System.out.println("btn2 선택");
		});
		
		btn3.setOnAction(event->{
			System.out.println("btn3 선택");
			Stage stage = new Stage();
			Parent root = null;
			try {
				root = FXMLLoader.load(
					getClass().getResource("../second/second.fxml")
				);
			} catch (IOException e) {}
			stage.setScene(new Scene(root));
			stage.show();
			this.stage.close();
		});
	}
	
	// Main 에서 생성된 무대 정보를 전달 받음
	public void setStage(Stage primaryStage) {
		this.stage = primaryStage;
		System.out.println(stage);
		KeyCombination kc 
		= new KeyCharacterCombination(
				"P",
				KeyCombination.ALT_DOWN,
				KeyCombination.SHIFT_DOWN);
		Mnemonic mn = new Mnemonic(btn1,kc);
		
		this.stage.getScene().addMnemonic(mn);
		
	}

}







