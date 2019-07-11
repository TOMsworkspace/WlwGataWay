package com.example.wlwgateway.com;

import java.io.IOException;
import java.io.OutputStream;

import android.serialport.SerialPort;
import android.util.Log;

import com.example.wlwgateway.message.ComEvent;
import com.example.wlwgateway.message.LinkEvent;

import de.greenrobot.event.EventBus;

public class ComManager {
	
	private static ComManager instance=null;

	private SerialPort port=null;

	private Thread readThread;

	private OutputStream dos;

	public void open() {
		try {
			port = new SerialPort("/dev/ttySAC3", 115200);

			if(port!=null)
				Log.i("LinkEvent ","Com 1 linked.");
			//System.out.println("Com 1 linked.");

			EventBus.getDefault().post(new LinkEvent("Com 1 linked."));
			if(readThread!=null) {
				readThread.interrupt();
				readThread = null;
			}
			dos = port.getOutputStream();
			readThread = new ReadComThread(port.getInputStream());
			readThread.start();
			
		
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ComManager getInstance() {
		// TODO Auto-generated method stub
		if(instance==null) {
			instance = new ComManager();
		}
		return instance;
	}

	public void sendToXtq(byte[] s) {
		// TODO Auto-generated method stub
		try {
			dos.write(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
