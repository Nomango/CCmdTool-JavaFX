package application.view;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AboutPaneController {
	@FXML
	private Button btBlog;
	@FXML
	private ImageView iconView;
	
	@FXML
	private void initialize() {
		// 打开博客按钮
		btBlog.setOnAction(a -> {
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + new URL("http://www.werelone.cn"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		iconView.setImage(new Image("cocos2dx.png"));
	}
}
