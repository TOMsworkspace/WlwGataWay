package com.example.wlwgateway.net;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.wlwgateway.com.ComManager;
import com.example.wlwgateway.message.LinkEvent;
import com.example.wlwgateway.message.NetEvent;
import com.example.wlwgateway.tool.Command;
import com.example.wlwgateway.tool.ToHex;

import de.greenrobot.event.EventBus;

public class ReadNetThread extends Thread {
	public static HashMap<Socket, ReadNetThread> mobiles = new HashMap<Socket, ReadNetThread>();
	
	public InputStream dis;
	public OutputStream dos;
	
	public ReadNetThread(Socket s) {
		try {
			mobiles.put(s, this);
			dis = s.getInputStream();
			dos = s.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		byte[] netdata = new byte[8];
		while (true) {
			try {

				int msgl = dis.read(netdata);
				if(msgl!=0&&new Command(netdata).isValid()&&netdata[1]!=(byte)0xff) {
					Log.i("NetEvent:  ", ToHex.byte2HexStr(netdata) + "\n");
					EventBus.getDefault().post(new NetEvent(ToHex.byte2HexStr(netdata)));
					//if(netdata[1]!=(byte)0xff)
						ComManager.getInstance().sendToXtq(netdata);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Set<Socket> cc = mobiles.keySet();
				Socket mobile = null;
				for (Socket name : cc) {
					if (mobiles.get(name) == this || !name.isConnected()) {
						mobile = name;
						break;
					}
				}
				Log.i("LinkEvent ","mobile "+mobile.toString()+" offline.");
				EventBus.getDefault().post(new LinkEvent("mobile "+mobile.toString()+" offline."));
				mobiles.remove(mobile);
				break;
			}
		}
	}
}
