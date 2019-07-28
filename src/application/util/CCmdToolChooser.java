package application.util;

import java.io.File;
import java.io.IOException;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CCmdToolChooser {
	
	public String getDirPath() {
		// 文件夹选择器
		final DirectoryChooser dc = new DirectoryChooser();
		final File directory = dc.showDialog(new Stage());
		
		if (directory != null) {
			String path = directory.getAbsolutePath();
			return path.replace(":\\", ":\\\\");
		}
		return null;
	}
	
	public String getFilePath(String chooserTitle, String type, String... args) {
		// 文件选择器
		final FileChooser fc = new FileChooser();
		fc.setTitle(chooserTitle);
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(type, args));
		File file = fc.showOpenDialog(new Stage());
		if (file != null) {
			return file.getAbsolutePath();
		}
		return null;
	}
	
	public void openDirectory(String path) {
		try {
			java.awt.Desktop.getDesktop().open(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
