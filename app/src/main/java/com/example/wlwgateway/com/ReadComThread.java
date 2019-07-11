package com.example.wlwgateway.com;
import android.os.SystemClock;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import com.example.wlwgateway.message.ComEvent;
import com.example.wlwgateway.net.NetManager;
import com.example.wlwgateway.tool.Command;
import com.example.wlwgateway.tool.ToHex;
import de.greenrobot.event.EventBus;
public class ReadComThread extends Thread {

	private BufferedInputStream reader;

	public ReadComThread(InputStream is) {
		reader = new BufferedInputStream(is);
	}
	
	public void run() {
		byte [] comdata=new byte[8];
		byte [] com=new byte[1];
		while(true) {
			try {

				int msgl = reader.read(comdata);
				if(msgl!=0&&new Command(comdata).isValid()) {

					Log.i("ComEvent:", ToHex.byte2HexStr(comdata) + "\n");
					EventBus.getDefault().post(new ComEvent(ToHex.byte2HexStr(comdata)));
					NetManager.sendToAllMobile(comdata);
				}

				SystemClock.sleep(1000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
