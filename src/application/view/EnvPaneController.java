package application.view;

import java.util.Map;

import application.util.CCmdToolChooser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EnvPaneController {
	@FXML
	private TextField tfEnvRoot;
	@FXML
	private TextField tfEnvFramework;
	@FXML
	private TextField tfEnvTemplates;
	@FXML
	private TextField tfEnvConsole;
	@FXML
	private TextField tfEnvNDK;
	@FXML
	private TextField tfEnvSDK;
	@FXML
	private TextField tfEnvAnt;
	@FXML
	private Button btOpenRoot;
	@FXML
	private Button btOpenFramework;
	@FXML
	private Button btOpenTemplates;
	@FXML
	private Button btOpenConsole;
	@FXML
	private Button btOpenNDK;
	@FXML
	private Button btOpenSDK;
	@FXML
	private Button btOpenAnt;
	@FXML
	private TextArea outputTextArea;
	
	private String envRoot = "";
	private String envFramework = "";
	private String envConsole = "";
	private String envTemplates = "";
	private String envNDK = "";
	private String envSDK = "";
	private String envAnt = "";
	
	@FXML
	private void initialize() {
	/*	btGetVer.setOnAction(a -> {
			CocosCmd progress = new CocosCmd("cmd /c cocos -v");

			outputTextArea.setText("");
			progress.messageProperty().addListener(e -> {
				outputTextArea.appendText(progress.getMessage());
			});
			
			Thread thread = new Thread(progress);
			thread.start();
		});
		btGetHelp.setOnAction(a -> {
			CocosCmd progress = new CocosCmd("cmd /c cocos -h");

			outputTextArea.setText("");
			progress.messageProperty().addListener(e -> {
				outputTextArea.appendText(progress.getMessage());
			});
			
			Thread thread = new Thread(progress);
			thread.start();
		});*/
		// 获取环境路径
		getEnv();
		
		tfEnvRoot.setText(envRoot);
		tfEnvFramework.setText(envFramework);
		tfEnvConsole.setText(envConsole);
		tfEnvTemplates.setText(envTemplates);
		tfEnvNDK.setText(envNDK);
		tfEnvSDK.setText(envSDK);
		tfEnvAnt.setText(envAnt);
		
		btOpenRoot.setOnAction(e -> {
			if (!envRoot.isEmpty()) {
				new CCmdToolChooser().openDirectory(envRoot);
			}
		});
		btOpenFramework.setOnAction(e -> {
			if (!envFramework.isEmpty()) {
				new CCmdToolChooser().openDirectory(envFramework);
			}
		});
		btOpenTemplates.setOnAction(e -> {
			if (!envTemplates.isEmpty()) {
				new CCmdToolChooser().openDirectory(envTemplates);
			}
		});
		btOpenConsole.setOnAction(e -> {
			if (!envConsole.isEmpty()) {
				new CCmdToolChooser().openDirectory(envConsole);
			}
		});
		btOpenNDK.setOnAction(e -> {
			if (!envNDK.isEmpty()) {
				new CCmdToolChooser().openDirectory(envNDK);
			}
		});
		btOpenSDK.setOnAction(e -> {
			if (!envSDK.isEmpty()) {
				new CCmdToolChooser().openDirectory(envSDK);
			}
		});
		btOpenAnt.setOnAction(e -> {
			if (!envAnt.isEmpty()) {
				new CCmdToolChooser().openDirectory(envAnt);
			}
		});
	}
	
	private void getEnv() {
		Map<String, String> map = System.getenv();
		
		envRoot = map.get("COCOS_X_ROOT");
		envConsole = map.get("COCOS_CONSOLE_ROOT");
		envFramework = map.get("COCOS_FRAMEWORKS");
		envTemplates = map.get("COCOS_TEMPLATES_ROOT");
		envNDK = map.get("NDK_ROOT");
		envSDK = map.get("ANDROID_SDK_ROOT");
		envAnt = map.get("ANT_ROOT");
	}

}
