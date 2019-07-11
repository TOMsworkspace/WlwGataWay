package com.example.wlwgateway.net;

import android.util.Log;

import com.example.wlwgateway.message.LinkEvent;
import com.example.wlwgateway.message.NetEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

import de.greenrobot.event.EventBus;

public class NetManager {

	public static void sendToAllMobile(byte[] msg) {
		// TODO Auto-generated method stub
		Collection<ReadNetThread> mm = ReadNetThread.mobiles.values();
		for(ReadNetThread m:mm){
			try {
				m.dos.write(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void start() {
		new Thread() {
			public void run() {
				try {
					ServerSocket server = new ServerSocket(1100);
					while(true) {
						Socket client = server.accept();
						Log.i("LinkEvent ","Socket:"+client.getInetAddress()
								+":port"+client.getPort()+"linked.\n");
						EventBus.getDefault().post(new LinkEvent("Socket:"+client.getInetAddress()
						+":port"+client.getPort()+"linked.\n"));
						new ReadNetThread(client).start();	
					}	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

		
	}


}
