package life_cycle;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;

public class LifeCycleTest extends Application {

	public LifeCycleTest() {
		System.out.println(Thread.currentThread()+" 생성자() 호출");
	}
	
	@Override
	public void init() throws Exception {
		// main method 실행 시 넘겨받는 argument(인자값)
		// 을 application 에서 가져오는 법
		// [a, b, c, --PORT=5001]
		// List 또는 --PORT=5001 형식의 값은 Map으로 확인 가능
		
		Parameters params = getParameters();
		List<String> list = params.getRaw();
		System.out.println(list);
		Map<String,String> map = params.getNamed();
		System.out.println(map);
		System.out.println(Thread.currentThread()+" init() 호출");
	}

	@Override
	public void start(Stage primaryStage) {
		System.out.println(Thread.currentThread()+" start() 호출");
		primaryStage.show();
	}
	
	@Override
	public void stop() throws Exception {
		System.out.println(Thread.currentThread()+" stop() 호출");
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(args));
		System.out.println(Thread.currentThread()+" Main 시작");
		launch(args);
		System.out.println(Thread.currentThread()+" Main 종료");
	}
}










