package application.view;

import javax.swing.JOptionPane;

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

public class CompilePaneController {
	@FXML
	private Button btRun;
	@FXML
	private Button btCancel;
	@FXML
	private Button btProjectDir;
	@FXML
	private Button btOutputDir;
	@FXML
	private Button btProjDir;
	@FXML
	private CheckBox cbShowConsole;
	@FXML
	private TextField tfProjectDir;
	@FXML
	private TextField tfOutputDir;
	@FXML
	private TextField tfCPUNumber;
	@FXML
	private TextField tfProjDir;
	@FXML
	private TextField tfAndroidPlatform;
	@FXML
	private ComboBox<String> cbNdkMode;
	@FXML
	private TextField tfAPP_ABI;
	@FXML
	private TextField tfNdkToolChain;
	@FXML
	private TextField tfAppCppFlags;
	@FXML
	private ComboBox<String> cbPlatform;
	@FXML
	private ComboBox<String> cbMode;
	@FXML
	private ComboBox<String> cbVSversion;
	@FXML
	private CheckBox cbQuiet;
	@FXML
	private CheckBox cbUsingAS;
	@FXML
	private CheckBox cbNoApk;
	@FXML
	private CheckBox cbSource_map;
	@FXML
	private CheckBox cbAdvance;
	@FXML
	private CheckBox cbNores;
	@FXML
	private CheckBox cbCompileScript;
	@FXML
	private CheckBox cbLuaEncrypt;
	@FXML
	private TextField tfLuaKey;
	@FXML
	private TextField tfLuaSign;
	@FXML
	private ProgressIndicator statusPi;
	@FXML
	private Label statusLabel;
	@FXML
	private TextArea outputTextArea;
	
	private CocosCmd progress;
	private Thread thread;
	
	public static BooleanProperty status = new SimpleBooleanProperty(false);
	
	private boolean usingAS, noApk, showConsole = true, isQuiet, source_map, advance,
			nores, compileScript, luaEncrypt;
	private int platform = 0, mode = 0, jobs = 1, vsVersion, ndkmode = 2;
	private String projectDir, outputDir, CPUNumber, proj_dir, luakey, luasign, androidPlatform, appabi, toolchain, cppflags;
	
	@FXML
	private void initialize() {
		ObservableList<String> platformList = FXCollections.observableArrayList("android", "win32", "web");
		cbPlatform.setItems(platformList);
		cbPlatform.setValue(platformList.get(0));
		
		ObservableList<String> modeList = FXCollections.observableArrayList("debug", "release");
		cbMode.setItems(modeList);
		cbMode.setValue(modeList.get(0));
		
		ObservableList<String> vsList = FXCollections.observableArrayList("2010", "2012", "2013", "2015", "默认");
		cbVSversion.setItems(vsList);
		cbVSversion.setValue(vsList.get(4));
		
		ObservableList<String> ndkmodeList = FXCollections.observableArrayList("debug", "release", "none");
		cbNdkMode.setItems(ndkmodeList);
		cbNdkMode.setValue(ndkmodeList.get(0));
		
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
		btOutputDir.setOnAction(e -> {
			String path = null;
			if ((path = new CCmdToolChooser().getDirPath()) != null) {
				tfOutputDir.setText(path);
			}
		});
		btProjDir.setOnAction(e -> {
			String path = null;
			if ((path = new CCmdToolChooser().getDirPath()) != null) {
				tfProjDir.setText(path);
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
		outputDir = tfOutputDir.getText();
		CPUNumber = tfCPUNumber.getText();
		proj_dir = tfProjDir.getText();
		luasign = tfLuaSign.getText();
		luakey = tfLuaKey.getText();
		androidPlatform = tfAndroidPlatform.getText();
		appabi = tfAPP_ABI.getText();
		toolchain = tfNdkToolChain.getText();
		cppflags = tfAppCppFlags.getText();
		showConsole = cbShowConsole.isSelected();
		
		platform = cbPlatform.getItems().indexOf(cbPlatform.getValue());
		vsVersion = cbVSversion.getItems().indexOf(cbVSversion.getValue());
		mode = cbMode.getItems().indexOf(cbMode.getValue());
		ndkmode = cbNdkMode.getItems().indexOf(cbNdkMode.getValue());
		
		nores = cbNores.isSelected();
		compileScript = cbCompileScript.isSelected();
		luaEncrypt = cbLuaEncrypt.isSelected();
		usingAS = cbUsingAS.isSelected();
		noApk = cbNoApk.isSelected();
		isQuiet = cbQuiet.isSelected();
		
		if (this.checkJobsValid()) {
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
	}
	
	private String getCmdline() {
		String cmd = "cmd /c";
		if (showConsole) {
			cmd += " start";
		}
		cmd += " cocos compile -s \"" + projectDir + "\"" + " -j " + jobs;
		
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
			if (noApk) cmd += " --no-apk";
			if (usingAS) cmd += " --android-studio";
			if (!androidPlatform.isEmpty()) cmd += " --ap " + androidPlatform;
			if (ndkmode != 2) cmd += " --ndk-mode " + cbNdkMode.getValue();
			if (!appabi.isEmpty()) cmd += " --app-abi " + androidPlatform;
			if (!toolchain.isEmpty()) cmd += " --ndk-toolchain " + toolchain;
			if (!cppflags.isEmpty()) cmd += " --ndk-cppflags " + cppflags;
		} else if (platform == 1) {
			cmd += " -p win32";
			if (vsVersion == 0) cmd += " --vs 2010";
			else if (vsVersion == 1) cmd += " --vs 2012";
			else if (vsVersion == 2) cmd += " --vs 2013";
			else if (vsVersion == 3) cmd += " --vs 2015";
		} else if (platform == 2) {
			cmd += " -p web";
			if (source_map) cmd += " --source-map";
			if (advance) cmd += " --advanced";
		}
		if (nores) {
			cmd += " --no-res";
		}
		if (compileScript) {
			cmd += " --compile-script 1";
		}
		if (luaEncrypt) {
			cmd += " --lua-encrypt --lua-encrypt-key " + luakey + " --lua-encrypt-sign " + luasign;
		}
		if (outputDir.length() != 0) {
			cmd += " -o \"" + outputDir + "\"";
		}
		if (proj_dir.length() != 0) {
			cmd += " --proj-dir \"" + proj_dir + "\"";
		}
		return cmd;
	}

	private boolean checkJobsValid() {
		for (char c: CPUNumber.toCharArray()) {
			if (!Character.isDigit(c)) {
				JOptionPane.showMessageDialog(null, "?指定的cpu数量有误！", "Error", JOptionPane.OK_OPTION);
				return false;
			}
		}
		
		jobs = Integer.valueOf(CPUNumber);
		
		if (jobs > 0) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "?指定的cpu数量有误！", "Error", JOptionPane.OK_OPTION);
			return false;
		}
	}

}
