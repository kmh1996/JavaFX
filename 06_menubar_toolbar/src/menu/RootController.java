package menu;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class RootController implements Initializable {
	
	// 소유자 창 - 주 윈도우
	private Stage primaryStage;
	
	@FXML private TextArea textArea;
	@FXML private ComboBox<String> comboBox;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboBox.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observable, 
					Number oldValue, 
					Number newValue) {
				int index = newValue.intValue();
				System.out.println("index : " + index);
				switch(index) {
					case 0 :
						// directory chooser
						handleDirectoryChooser();
						break;
					case 1 :
						// popup window
						handlePopup();
						break;
					case 2 :
						// custom window
						handleCustom();
						break;
					case 3 :
						// custom dialog
						handleCustomDialog();
						break;
				}
			}
		});
	}
	
	protected void handleDirectoryChooser() {
		// 폴더 선택 상자
		DirectoryChooser chooser = new DirectoryChooser();
		File selectedDir = chooser.showDialog(primaryStage);
		if(selectedDir != null) {
			// directory 안에 존재하는 파일 or dir(폴더)정보
			for(File f : selectedDir.listFiles()) {
				textArea.appendText(f.getName()+"\n");
			}
		}
	}

	protected void handlePopup() {
		System.out.println("popup");
		Popup popup = new Popup();
		Parent parent = null;
		try {
			parent = FXMLLoader.load(
					getClass().getResource("Popup.fxml")
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageView imageView 
			= (ImageView)parent.lookup("#imgMessage");
		Image image = new Image(
			getClass().getResource("../images/dialog-info.png")
			.toString()
		);
		imageView.setImage(image);
		
		Label lblMessage 
			= (Label)parent.lookup(".lblMessage");
		lblMessage.setText("메시지 알림!");
		
		// MouseEvent EventHandler
		imageView.setOnMouseClicked(event->{
			// popup 종료
			popup.hide();
		});
		
		popup.getContent().add(parent);
		// focus가 popup창을 벗어나면 자동으로 종료
		popup.setAutoHide(true);
		popup.show(primaryStage);
	}

	protected void handleCustom() {
		/*
		 * Stage 생성 시 기본 설정을 추가 할 수 있음
		 * StageStyle
		 * DECORATED 일반적 윈도우 스타일, 흰배경, 
		 * 제목줄에 장식(아이콘,타이틀,축소,복원,닫기)이 있다.
		 * UNDECORATED 흰배경, 제목줄 X
		 * TRANSPARENT 투명 배경 , 제목줄 X
		 * UNIFIED DECORATED와 같지만 내용물의 경계선 X
		 * UTILITY 흰배경, 제목줄에 타이틀, 종료버튼만 존재
		 */
		//Stage stage = new Stage(StageStyle.UNDECORATED);
		//Stage stage = new Stage(StageStyle.DECORATED);
		Stage stage = new Stage();
		// 소유자가 지정되어 있으면 다른창으로 이동 불가
		// 제어권을 넘기지 않음.
		//stage.initModality(Modality.WINDOW_MODAL);
		//stage.initOwner(primaryStage);
		// 소유자가 없어도 다른 창으로 제어권을 넘기지 않음.
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("내용입력");
		
		Parent parent = null;
		try {
			parent = FXMLLoader.load(
				getClass().getResource("Custom.fxml")
			);
		} catch (IOException e) {}
		
		TextField textTitle
			= (TextField)parent.lookup("#textTitle");
		TextArea textContent
			= (TextArea)parent.lookup("#textContent");
		Button btnOk 
			= (Button)parent.lookup("#btnOk");
		Button btnCancel 
			= (Button)parent.lookup("#btnCancel");
		
		btnCancel.setOnAction(event->{
			// 무대 종료
			stage.close();
		});
		
		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String title = textTitle.getText();
				String content = textContent.getText();
				stage.close();
				textArea.appendText(title+"\n");
				textArea.appendText(content+"\n");
				// 콤보박스의 선택내용 초기화
				comboBox.getSelectionModel().clearSelection();
			}
		});
		
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	protected void handleCustomDialog() {
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("확인");
		
		Parent parent = null;
		try {
			parent = FXMLLoader.load(
				getClass().getResource("Custom_dialog.fxml")
			);
		} catch (IOException e) {}
		Label txtTitle = (Label)parent.lookup("#txtTitle");
		txtTitle.setText("졸면 안되!");
		Button btnOk = (Button)parent.lookup("#btnOk");
		btnOk.setOnAction(e->{
			comboBox.getSelectionModel().clearSelection();
			dialog.close();
		});
		Scene scene = new Scene(parent);
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();
	}

	public void handleNew(ActionEvent event) {
		System.out.println("NEW");
	}
	
	public void handleOpen(ActionEvent event) throws Exception {
		// textarea로 파일 에 작성된 정보를 출력
		System.out.println("OPEN");
		// Dialog - 소유자 창을 통해 창을 띄움
		textArea.appendText("Open \n");
		// 파일 선택 dialog
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text files","*.txt")
			);
		
		File selectedFile 
			= fileChooser.showOpenDialog(primaryStage);
		if(selectedFile != null) {
			// 파일 선택
			String path = selectedFile.getPath();
			textArea.appendText(path+" \n");
			InputStream fis = new FileInputStream(selectedFile);
			Reader reader = new InputStreamReader(fis);
			BufferedReader bReader = new BufferedReader(reader);
			String data = null;
			textArea.clear();	// textArea 초기화
			// 파일의 정보를 한줄씩 읽어 들임 - 읽어들일게 없을때 까지(null)
			while((data = bReader.readLine()) != null) {
				textArea.appendText(data+"\n");
			}
			bReader.close();
			reader.close();
			fis.close();
		}else {
			// 선택된 파일 없음
			textArea.appendText("파일 미선택\n");
		}
	}
	
	public void handleSave(ActionEvent event) throws Exception{
		System.out.println("SAVE");
		// textArea에 입력된 내용을 
		// FileChooser를 이용하여 선택된 파일에 출력
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text Files","*.txt"),
				new ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg","*.gif"),
				new ExtensionFilter("Media Files","*.wav","*.mpg","*.mp4","*.avi"),
				new ExtensionFilter("All Files","*.*")
			);
		
		File selectedFile 
				= fileChooser.showSaveDialog(primaryStage);
		if(selectedFile != null) {
			String path = selectedFile.getPath();
			textArea.appendText(path+"\n");
			Writer w = new FileWriter(selectedFile);
			BufferedWriter writer = new BufferedWriter(w);
			String data = textArea.getText();
			writer.write(data);
			writer.flush();
			writer.close();
			w.close();
		}else {
			textArea.appendText("파일 미선택\n");
		}
	}
	
	public void handleClose(ActionEvent event) {
		System.out.println("프로그램 종료");
		Platform.exit();
	}
}











