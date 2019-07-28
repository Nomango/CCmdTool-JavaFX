package application.util;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public class CCmdToolLoader extends FXMLLoader {
	private static FXMLLoader loader;
	
	public static <T> T getFromURL(URL location) {
		T object = null;
		try {
			loader = new FXMLLoader();
			loader.setLocation(location);
			object = loader.load();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return object;
	}

}
