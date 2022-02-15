package exam01_java_layout;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		// 새로 - 수직으로 내부에 control을 포함하는 Container
		VBox root = new VBox();
		root.prefWidth(350);	// VBox 폭 설정
		root.prefHeight(150);	// VBox 높이 설정
		root.setAlignment(Pos.CENTER);// 컨트롤을 중앙으로 정렬
		root.setSpacing(20);	// 컨트롤의 수직 간격
		
		Label label = new Label();		// Label control 생성
		label.setText("Hello JavaFX");  // label text 설정
		label.setFont(new Font(50));	// 글자크기 설정
		
		// VBox의 자식요소들을 저장하고있는 리스트에
		// label을 추가
		root.getChildren().add(label);
		
		Button btn = new Button(); // Button 컨트롤 생성
		btn.setText("확인");		   // 버튼의 text 설정	
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// 무대 종료 == 프로그램 종료
				Platform.exit();
			}
		});
		
		// VBox의 자식 요소에 btn 추가
		root.getChildren().add(btn);
		
		// VBox를 루트 컨테이너로 해서 장면을 생성
		Scene scene = new Scene(root);
		// 무대 - Window에 장면 추가
		primaryStage.setScene(scene);
		// 무대 - Window에 제목 추가
		primaryStage.setTitle("AppMain");
		// 창 띄우기
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}





