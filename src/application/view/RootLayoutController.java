package application.view;

import java.net.URL;

import application.Main;
import application.util.CCmdToolLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RootLayoutController {
	@FXML
	private Label label;
	@FXML
	private Label label2;
	@FXML
	private MenuItem closeItem;
	@FXML
	private MenuItem aboutItem;
	@FXML
	private MenuItem prebuildItem;
	@FXML
	private MenuItem newItem;
	@FXML
	private MenuItem compileItem;
	@FXML
	private MenuItem runItem;
	@FXML
	private MenuItem deployItem;
	@FXML
	private MenuItem luaItem;
	@FXML
	private MenuItem jsItem;
	@FXML
	private MenuItem envItem;
	@FXML
	private MenuItem helpItem;
	@FXML
	private StackPane defaultPane;
	@FXML
	private BorderPane mainPane;
	@FXML
	private ProgressIndicator statusPi;
	
	private BorderPane newPane, compilePane, envPane, deployPane, prebuildPane, runPane, luaPane, jsPane;
	private AnchorPane aboutPane;
	
	private Stage aboutStage = new Stage();
	
	@FXML
	private void initialize() {
		// 加载界面
		newPane = CCmdToolLoader.getFromURL(Main.getResource("view/NewPane.fxml"));
		compilePane = CCmdToolLoader.getFromURL(Main.getResource("view/CompilePane.fxml"));
		envPane = CCmdToolLoader.getFromURL(Main.getResource("view/EnvPane.fxml"));
		aboutPane = CCmdToolLoader.getFromURL(Main.getResource("view/AboutPane.fxml"));
		deployPane = CCmdToolLoader.getFromURL(Main.getResource("view/DeployPane.fxml"));
		prebuildPane = CCmdToolLoader.getFromURL(Main.getResource("view/PrebuildPane.fxml"));
		runPane = CCmdToolLoader.getFromURL(Main.getResource("view/RunPane.fxml"));
		luaPane = CCmdToolLoader.getFromURL(Main.getResource("view/LuaPane.fxml"));
		jsPane = CCmdToolLoader.getFromURL(Main.getResource("view/JsPane.fxml"));
		
		// 预编译菜单项
		prebuildItem.setOnAction(e -> {
			mainPane.setCenter(prebuildPane);
		});
		// 新建项目菜单项
		newItem.setOnAction(e -> {
			mainPane.setCenter(newPane);
		});
		// 编译菜单项
		compileItem.setOnAction(e -> {
			mainPane.setCenter(compilePane);
		});
		// 运行菜单项
		runItem.setOnAction(e -> {
			mainPane.setCenter(runPane);
		});
		// 部署菜单项
		deployItem.setOnAction(e -> {
			mainPane.setCenter(deployPane);
		});
		// Lua菜单项
		luaItem.setOnAction(e -> {
			mainPane.setCenter(luaPane);
		});
		// js菜单项
		jsItem.setOnAction(e -> {
			mainPane.setCenter(jsPane);
		});
		// Cocos环境菜单项
		envItem.setOnAction(e -> {
			mainPane.setCenter(envPane);
		});
		// 关闭菜单项
		closeItem.setOnAction(e -> {
			mainPane.setCenter(defaultPane);
		});
		
		// 关于界面
		aboutStage.setScene(new Scene(aboutPane));
		aboutStage.setResizable(false);
		aboutStage.getIcons().add(new Image("aboutIcon.png"));
		aboutStage.setTitle("关于 CCmdTool");
		// 关于菜单项
		aboutItem.setOnAction(e -> {
			aboutStage.show();
		});
		
		// 帮助菜单项
		helpItem.setOnAction(a -> {
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + new URL("http://www.werelone.cn/2016/12/23/161223-cocos2d-cmd-tool-1-6-help/"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
