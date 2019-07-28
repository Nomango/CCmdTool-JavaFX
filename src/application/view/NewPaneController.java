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

public class NewPaneController {
	@FXML
	private Button btRun;
	@FXML
	private Button btCancel;
	@FXML
	private CheckBox cbShowConsole;
	@FXML
	private TextField tfProjectName;
	@FXML
	private TextField tfOutputDir;
	@FXML
	private Button btOutputDir;
	@FXML
	private TextField tfTemplateName;
	@FXML
	private Button btGetTemplatesName;
	@FXML
	private TextField tfPackageName;
	@FXML
	private TextField tfEnginePath;
	@FXML
	private Button btEnginePath;
	@FXML
	private ComboBox<String> cbLanguage;
	@FXML
	private ComboBox<String> cbEngineType;
	@FXML
	private ComboBox<String> cbScreen;
	@FXML
	private ProgressIndicator statusPi;
	@FXML
	private Label statusLabel;
	@FXML
	private TextArea outputTextArea;
	
	private CocosCmd progress;
	private Thread thread;
	
	private BooleanProperty status = new SimpleBooleanProperty(false);
	
	private String projectName, outputDir, packageName, enginePath, templateName;
	private int language = 0, screenOrientation = 0, engineType = 0;
	private boolean showConsole = false;
	
	@FXML
	private void initialize() {
		ObservableList<String> languageList = FXCollections.observableArrayList("cpp", "lua", "js");
		cbLanguage.setItems(languageList);
		cbLanguage.setValue(languageList.get(0));
		cbLanguage.setOnAction(e -> language = languageList.indexOf(cbLanguage.getValue()));
		
		ObservableList<String> engineTypeList = FXCollections.observableArrayList("预编译", "源代码");
		cbEngineType.setItems(engineTypeList);
		cbEngineType.setValue(engineTypeList.get(0));
		cbEngineType.setOnAction(e -> engineType = engineTypeList.indexOf(cbEngineType.getValue()));
		
		ObservableList<String> screenList = FXCollections.observableArrayList("横屏", "竖屏");
		cbScreen.setItems(screenList);
		cbScreen.setValue(screenList.get(0));
		cbScreen.setOnAction(e -> screenOrientation = screenList.indexOf(cbScreen.getValue()));
		
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

		btOutputDir.setOnAction(e -> {
			String path = null;
			if ((path = new CCmdToolChooser().getDirPath()) != null) {
				tfOutputDir.setText(path);
			}
		});

		btEnginePath.setOnAction(e -> {
			String path = null;
			if ((path = new CCmdToolChooser().getDirPath()) != null) {
				tfEnginePath.setText(path);
			}
		});

		btGetTemplatesName.setOnAction(e -> this.getTemplatesName());

		cbShowConsole.setSelected(false);
		
		statusPi.setVisible(false);

		status.addListener(e -> {
			if (status.get()) {
				statusPi.setVisible(true);
				statusLabel.setText("正在运行 - 请耐心等待...");
				btRun.setDisable(true);
				cbShowConsole.setDisable(true);
				btGetTemplatesName.setDisable(true);
				btCancel.setDisable(false);
			} else {
				statusPi.setVisible(false);
				statusLabel.setText("就绪");
				btRun.setDisable(false);
				cbShowConsole.setDisable(false);
				btGetTemplatesName.setDisable(false);
				btCancel.setDisable(true);
			}
		});
	}
	
	private void pressStart() {
		projectName = tfProjectName.getText();
		outputDir = tfOutputDir.getText();
		packageName = tfPackageName.getText();
		enginePath = tfEnginePath.getText();
		templateName = tfTemplateName.getText();
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
		cmd += " cocos new \"" + projectName + "\"" + " -p " + packageName;
		
		if (outputDir.length() != 0) {
			cmd += " -d \"" + outputDir + "\"";
		}

		if (screenOrientation == 1) {
			cmd += " --portrait";
		}

		switch (language) {
		case 0:
			cmd += " -l cpp";
			break;
		case 1:
			cmd += " -l lua";
			break;
		case 2:
			cmd += " -l js";
			break;
		default:
			break;
		}

		if (templateName.length() != 0) {
			cmd += " -t " + templateName;
		} else {
			if (engineType == 0) {
				cmd += " -t binary";
			} else if (engineType == 1) {
				cmd += " -t default";
			}
		}

		if (enginePath.length() != 0) {
			cmd += " -e \"" + enginePath + "\"";
		}
		return cmd;
	}
	
	private void getTemplatesName() {
		String cmdline = "cmd /c";
		if (showConsole) {
			cmdline += " start";
		}
		cmdline += " cocos new --list-templates";
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
