package application.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.concurrent.Task;

public class CocosCmd extends Task<Void>{
	private String cmdline = null;	// ������
	private int exitValue;
	
	public CocosCmd(String cmdline) {
		this.cmdline = cmdline;
	}

	@Override
	protected Void call() throws Exception {
		// ��ʾ�ȴ���Ϣ
		updateMessage("����ִ�� - " + cmdline + "\n");
		
		// ����������
		Process process = Runtime.getRuntime().exec(cmdline);
		
		// ��ȡ����̨�����Ϣ
		String line = null;
		try {
			InputStream ins = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
			while (!isCancelled() && (line = reader.readLine()) != null) {
				// ���������Ϣ
				updateMessage(line + "\n");
			}
		} catch (Exception e) {
			updateMessage(e.getMessage() + "\n");
		}
		
		if (this.isCancelled()) {
			process.destroy();
		}
		
		// �ȴ��߳̽���
		exitValue = process.waitFor();
		return null;
	}
	
	@Override
	protected void succeeded() {
		super.succeeded();
		// ��ʾ������Ϣ
		updateMessage("�ѽ��� - ����ֵ��" + exitValue + "\n");
	}

	@Override
	protected void cancelled() {
		super.cancelled();
		updateMessage("��ȡ�� - ����ֵ��" + exitValue + "\n");
	}

	@Override
	protected void failed() {
		super.failed();
		updateMessage("���� - ����ֵ��" + exitValue + "\n");
	}

}
