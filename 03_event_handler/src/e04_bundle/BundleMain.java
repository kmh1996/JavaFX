package e04_bundle;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/*
 * bundle - 여러가지 데이터의 묶음
 * java - bundle 여러가지 타입의 데이터를 저장하는 Map
 * Map형태로 구현된 데이터의 묶음(bundle)
 */
public class BundleMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Locale 각 나라 지역의 [언어][나라]등의 정보를 담고 있는 Class
		// 사용가능한 국가 정보를 List로 제공
		for(Locale locale : Locale.getAvailableLocales()) {
			System.out.printf("Display Language : %s , ",locale.getDisplayLanguage());
			System.out.printf("language code : %s , " , locale.getLanguage());
			System.out.printf("Display Country : %s , " , locale.getDisplayCountry());
			System.out.printf("Country code : %s , code : %s %n " , locale.getCountry(),locale.toString());
		}
		
		System.out.println("==========================");
		Locale locale = Locale.getDefault();
		System.out.println(locale);
		//                  언어  , 국가
		locale = new Locale("en","UK");//영국
		//locale = new Locale("en","US");//미국
		locale = new Locale("zh","CN");//중국
		locale = new Locale("ja","JP");//일본
		
		// prop.s_ja_JP.properties
		ResourceBundle bundle 
					= ResourceBundle.getBundle(
							"prop.s", locale
						);
		System.out.println(bundle.keySet());
		
		
		try {
			//Parent root = FXMLLoader.load(getClass().getResource("Bundle.fxml"));
			Parent root = FXMLLoader.load(
					getClass().getResource("Bundle.fxml"),
					bundle
				);
			/*
			// Button 도 Parent가 될 수 있음. 단. 내부에 요소를 담을 수 없음.
			Parent btn = new Button("Hello JavaFX!");
			Scene scene = new Scene(btn);
			*/
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}







