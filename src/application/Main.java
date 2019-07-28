package application;

import java.net.URL;

import application.util.CCmdToolLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private BorderPane rootLayout;
	
	public void initRootLayout() {
		rootLayout = CCmdToolLoader.getFromURL(getResource("view/RootLayout.fxml"));
	}
	
	public static URL getResource(String name) {
		return Main.class.getResource(name);
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.initRootLayout();
		primaryStage.setTitle("CCmdTool v1.7");
		primaryStage.setScene(new Scene(rootLayout));
		primaryStage.getIcons().add(new Image("cocos2dx.png"));
		primaryStage.setMinWidth(400);
		primaryStage.setMinHeight(480);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
