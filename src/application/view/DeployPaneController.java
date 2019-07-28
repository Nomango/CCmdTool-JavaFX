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

public class DeployPaneController {
	@FXML
	private Button btRun;
	@FXML
	private Button btProjectDir;
	@FXML
	private Button btPlatformPath;
	@FXML
	private Button btOutputDir;
	@FXML
	private CheckBox cbShowConsole;
	@FXML
	private TextField tfProjectDir;
	@FXML
	private TextField tfPlatformPath;
	@FXML
	private TextField tfOutputDir;
	@FXML
	private ComboBox<String> cbPlatform;
	@FXML
	private ComboBox<String> cbMode;
	@FXML
	private CheckBox cbQuiet;
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
	
	private boolean showConsole = true, isQuiet = false;
	private int platform = 0, mode = 0;
	private String projectDir, proj_dir, outputDir;
	
	@FXML
	private void initialize() {
		ObservableList<String> platformList = FXCollections.observableArrayList("android", "win32", "web");
		cbPlatform.setItems(platformList);
		cbPlatform.setValue(platformList.get(0));
		cbPlatform.setOnAction(e -> platform = platformList.indexOf(cbPlatform.getValue()));
		
		ObservableList<String> modeList = FXCollections.observableArrayList("debug", "release");
		cbMode.setItems(modeList);
		cbMode.setValue(modeList.get(0));
		cbMode.setOnAction(e -> mode = modeList.indexOf(cbMode.getValue()));
		
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
			String path = null;
			if ((path = new CCmdToolChooser().getDirPath()) != null) {
				tfProjectDir.setText(path);
			}
		});

		btPlatformPath.setOnAction(e -> {
			String path = null;
			if ((path = new CCmdToolChooser().getDirPath()) != null) {
				tfPlatformPath.setText(path);
			}
		});

		btOutputDir.setOnAction(e -> {
			String path = null;
			if ((path = new CCmdToolChooser().getDirPath()) != null) {
				tfOutputDir.setText(path);
			}
		});

		cbShowConsole.setSelected(true);
		
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
		proj_dir = tfPlatformPath.getText();
		outputDir = tfOutputDir.getText();
		isQuiet = cbQuiet.isSelected();
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
		cmd += " cocos deploy -s \"" + projectDir + "\"";
		
		if (isQuiet) {
			cmd += " -q";
		}

		if (mode == 0) {
			cmd += " -m debug";
		} else {
			cmd += " -m release";
		}

		if (platform == 0) {
			cmd += " -p android";
		} else if (platform == 1) {
			cmd += " -p win32";
		} else if (platform == 2) {
			cmd += " -p web";
		}

		if (proj_dir.length() != 0) {
			cmd += " --proj-dir \"" + proj_dir + "\"";
		}

		if (outputDir.length() != 0) {
			cmd += " -o \"" + outputDir + "\"";
		}
		return cmd;
	}

}
