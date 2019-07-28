package application.view;

import application.util.CCmdToolChooser;
import application.util.CocosCmd;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrebuildPaneController {
	@FXML
	private Button btCancel;
	@FXML
	private Button btBuildTemplate;
	@FXML
	private CheckBox cbShowConsole;
	@FXML
	private Button btRun;
	@FXML
	private CheckBox cbPlatformWin32;
	@FXML
	private CheckBox cbPlatformAndroid;
	@FXML
	private ComboBox<String> cbMode;
	@FXML
	private TextField tfEnginePath;
	@FXML
	private Button btEnginePath;
	@FXML
	private CheckBox cbClean;
	@FXML
	private CheckBox cbCloseStrip;
	@FXML
	private ProgressIndicator statusPi;
	@FXML
	private Label statusLabel;
	@FXML
	private TextArea outputTextArea;
	@FXML
	private TextField tfVSVersion;
	@FXML
	private TextField tfAPP_ABI;
	
	private CocosCmd progress;
	private Thread thread;
	
	public static BooleanProperty status = new SimpleBooleanProperty(false);
	
	private int mode = 1;
	private String enginePath, vsversion, appabi;
	private boolean clean, closeStrip, showConsole = false, platformWin32, platformAndroid;
	
	@FXML
	private void initialize() {
		ObservableList<String> modeList = FXCollections.observableArrayList("debug", "release");
		cbMode.setItems(modeList);
		cbMode.setValue(modeList.get(1));
		
		btRun.setOnAction(e -> buildLibs());

		btBuildTemplate.setOnAction(e -> this.buildTemplate());

		btCancel.setDisable(true);
		btCancel.setOnAction(e -> {
			if (progress.isRunning()) {
				progress.cancel();
			}
			if (thread.isAlive()) {
				thread.interrupt();
			}
		});
		
		btEnginePath.setOnAction(e -> {
			String path = null;
			if ((path = new CCmdToolChooser().getDirPath()) != null) {
				tfEnginePath.setText(path);
			}
		});
		
		cbShowConsole.setSelected(true);
		
		statusPi.setVisible(false);

		status.addListener(e -> {
			if (status.get()) {
				statusPi.setVisible(true);
				statusLabel.setText("正在运行 - 请耐心等待...");
				btRun.setDisable(true);
				btBuildTemplate.setDisable(true);
				cbShowConsole.setDisable(true);
				btCancel.setDisable(false);
			} else {
				statusPi.setVisible(false);
				statusLabel.setText("就绪");
				btRun.setDisable(false);
				btBuildTemplate.setDisable(false);
				cbShowConsole.setDisable(false);
				btCancel.setDisable(true);
			}
		});
	}
	
	// 预编译
	private void buildLibs() {		
		enginePath = tfEnginePath.getText();
		vsversion = tfVSVersion.getText();
		appabi = tfAPP_ABI.getText();
		mode = cbMode.getItems().indexOf(cbMode.getValue());
		clean = cbClean.isSelected();
		closeStrip = cbCloseStrip.isSelected();
		platformWin32 = cbPlatformWin32.isSelected();
		platformAndroid = cbPlatformAndroid.isSelected();
		showConsole = cbShowConsole.isSelected();
		
		// 取消clean命令
		cbClean.setSelected(false);
		
		// 生成命令行
		String cmdline = this.getCmdline();
		progress = new CocosCmd(cmdline);
		
		status.bind(progress.runningProperty());
		outputTextArea.setText("");
		progress.messageProperty().addListener(e -> {
			outputTextArea.appendText(progress.getMessage());
		});
		// 运行
		thread = new Thread(progress);
		thread.start();
	}
	
	// 生成预编译命令行
	private String getCmdline() {
		String cmd = "cmd /c";
		if (showConsole) {
			cmd += " start";
		}
		cmd += " cocos gen-libs";
		// 编译模式
		cmd += (mode == 0) ? " -m debug" : " -m release";
		// 平台
		if (platformWin32) {
			cmd += " -p win32";
			if (!vsversion.isEmpty()) {
				cmd += " --vs " + vsversion;
			}
		}
		if (platformAndroid) {
			cmd += " -p android";
			if (!appabi.isEmpty()) {
				cmd += " --app-abi " + appabi;
			}
		}
		
		// 引擎路径
		if (enginePath.length() != 0) {
			cmd += " -e \"" + enginePath + "\"";
		}
		// 删除 prebuild 文件夹
		if (clean) {
			cmd += " -c";
		}
		// 关闭Strip功能
		if (closeStrip) {
			cmd += " --dis-strip";
		}
		return cmd;
	}
	
	// 生成预编译模版
	private void buildTemplate() {
		String cmdline = "cmd /c";
		if (cbShowConsole.isSelected()) {
			cmdline += " start";
		}
		cmdline += " cocos gen-templates";
		progress = new CocosCmd(cmdline);
		
		status.bind(progress.runningProperty());
		outputTextArea.setText("");
		progress.messageProperty().addListener(e -> {
			outputTextArea.appendText(progress.getMessage());
		});
		
		thread = new Thread(progress);
		thread.start();
	}
}
