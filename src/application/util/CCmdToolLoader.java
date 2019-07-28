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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return object;
	}

}
