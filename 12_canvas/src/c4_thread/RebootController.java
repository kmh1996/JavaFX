package c4_thread;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class RebootController implements Initializable {

	@FXML private Canvas bgCanvas;
	@FXML private VBox carBox;
	@FXML private Button btnStart;
	
	boolean isStart;
	
	int[] x;
	
	int i = 0 , bgX = 0; 
	
	ArrayList<Thread> threadList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//initCar();
		btnStart.setOnAction((event)->{
			isStart = true;
			initCar();
			Platform.runLater(()->{
				for(Thread t : threadList) {
					t.start();
				}
			});
		});
	}

	public void initCar() {
		x = new int[4];
		threadList = new ArrayList<>();
		Set<Node> carList = carBox.lookupAll("Canvas");
		for(Node n : carList) {
			final int j = i;
			Canvas canvas = (Canvas)n;
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			Image image = new Image(
				getClass().getResource("img/car"+(j+1)+".png").toString()
			);
			gc.drawImage(image, 0, 24);
			threadList.add(new Thread(new Runnable() {
				@Override
				public void run() {
					while(isStart) {
						int a = (int)(Math.random()*15)+1;
						int y = (int)(Math.random()*24)+1;
						Platform.runLater(()->{
							gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
							gc.drawImage(image, x[j], y);
						});
						x[j] += a;
						if(x[j]+image.getWidth() >= 600) {
							isStart = false;
							System.out.println(j+1+"번차 승리");
						}
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {}
					}
				}
			}));
			i++;
		}
	}
}







