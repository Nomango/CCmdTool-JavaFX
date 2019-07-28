package application.view;

import application.util.CCmdToolChooser;
import application.util.CocosCmd;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JsPaneController {
	@FXML
	private Button btRun;
	@FXML
	private Button btProjectDir;
	@FXML
	private Button btOutputDir;
	@FXML
	private Button btJson;
	@FXML
	private CheckBox cbShowConsole;
	@FXML
	private TextField tfProjectDir;
	@FXML
	private TextField tfOutputDir;
	@FXML
	private CheckBox cbVerbose;
	@FXML
	private CheckBox cbClosure;
	@FXML
	private TextField tfJsName;
	@FXML
	private TextField tfJson;
	@FXML
	private TextField tfExParams;
	@FXML
	private ProgressIndicator statusPi;
	@FXML
	private Label statusLabel;
	@FXML
	private TextArea outputTextArea;
	
	@FXML
	private Button btCancel;
	private CocosCmd progress;
	private Thread thread;
	
	public static BooleanProperty status = new SimpleBooleanProperty(false);
	
	private boolean showConsole = true, verbose, closure;
	private String projectDir, outputDir, json, exParams, jsName;
	
	@FXML
	private void initialize() {
		btRun.setOnAction(e -> pressStart());
		
		btCancel.setDisable(true);
		btCancel.setOnAction(e -> {
			if (progress.isRunning()) {
				progress.cancel();
			}
			if (thread.isAlive()) {
				thread.interrupt();
			}
		});
		
		btProjectDir.setOnAction(e -> {
			String path = new CCmdToolChooser().getFilePath("选择JS文件", "JS文件", "*.js");
			if (path != null) {
				tfProjectDir.appendText(path + ";");
			}
		});
		
		btOutputDir.setOnAction(e -> {
			String path = new CCmdToolChooser().getDirPath();
			if (path != null) {
				tfOutputDir.setText(path);
			}
		});
		
		btJson.setOnAction(e -> {
			String path = new CCmdToolChooser().getFilePath("选择配置文件", "配置文件", "*.json");
			if (path != null) {
				tfProjectDir.setText(path);
			}
		});
		
		cbShowConsole.setSelected(false);
		
		statusPi.setVisible(false);

		status.addListener(e -> {
			if (status.get()) {
				statusPi.setVisible(true);
				statusLabel.setText("正在运行 - 请耐心等待...");
				btRun.setDisable(true);
				cbShowConsole.setDisable(true);
				btCancel.setDisable(false);
			} else {
				statusPi.setVisible(false);
				statusLabel.setText("就绪");
				btRun.setDisable(false);
				cbShowConsole.setDisable(false);
				btCancel.setDisable(true);
			}
		});
	}
	
	private void pressStart() {
		projectDir = tfProjectDir.getText();
		outputDir = tfOutputDir.getText();
		jsName = tfJsName.getText();
		json = tfJson.getText();
		exParams = tfExParams.getText();
		
		closure = cbClosure.isSelected();
		verbose = cbVerbose.isSelected();
		showConsole = cbShowConsole.isSelected();
		
		String cmdline = this.getCmdline();
		progress = new CocosCmd(cmdline);

		status.bind(progress.runningProperty());
		outputTextArea.setText("");
		progress.messageProperty().addListener(e -> {
			outputTextArea.appendText(progress.getMessage());
		});

		thread = new Thread(progress);
		thread.start();
	}
	
	private String getCmdline() {
		String cmd = "cmd /c";
		if (showConsole) {
			cmd += " start";
		}
		cmd += " cocos run";
		
		if (!projectDir.isEmpty()) {
			cmd += " -s \"" + projectDir + "\"";
		}
		if (!outputDir.isEmpty()) {
			cmd += " -d \"" + outputDir + "\"";
		}
		
		if (verbose) {
			cmd += " -v";
		}
		if (closure) {
			cmd += " -c";
			if (!jsName.isEmpty()) {
				cmd += " -o " + jsName;
			}
			if (!json.isEmpty()) {
				cmd += " -j " + json;
			}
			if (!exParams.isEmpty()) {
				cmd += " -m " + exParams;
			}
		}
		return cmd;
	}
}
