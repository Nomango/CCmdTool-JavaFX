package application.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.concurrent.Task;

public class CocosCmd extends Task<Void>{
	private String cmdline = null;	// 命令行
	private int exitValue;
	
	public CocosCmd(String cmdline) {
		this.cmdline = cmdline;
	}

	@Override
	protected Void call() throws Exception {
		// 显示等待信息
		updateMessage("正在执行 - " + cmdline + "\n");
		
		// 运行命令行
		Process process = Runtime.getRuntime().exec(cmdline);
		
		// 获取控制台输出信息
		String line = null;
		try {
			InputStream ins = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
			while (!isCancelled() && (line = reader.readLine()) != null) {
				// 更新输出信息
				updateMessage(line + "\n");
			}
		} catch (Exception e) {
			updateMessage(e.getMessage() + "\n");
		}
		
		if (this.isCancelled()) {
			process.destroy();
		}
		
		// 等待线程结束
		exitValue = process.waitFor();
		return null;
	}
	
	@Override
	protected void succeeded() {
		super.succeeded();
		// 显示结束信息
		updateMessage("已结束 - 返回值：" + exitValue + "\n");
	}

	@Override
	protected void cancelled() {
		super.cancelled();
		updateMessage("已取消 - 返回值：" + exitValue + "\n");
	}

	@Override
	protected void failed() {
		super.failed();
		updateMessage("出错！ - 返回值：" + exitValue + "\n");
	}

}
