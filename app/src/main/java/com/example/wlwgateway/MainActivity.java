package com.example.wlwgateway;

import com.example.wlwgateway.com.ComManager;
import com.example.wlwgateway.device.Device;
import com.example.wlwgateway.device.DeviceAdapter;
import com.example.wlwgateway.message.ComEvent;
import com.example.wlwgateway.message.LinkEvent;
import com.example.wlwgateway.message.NetEvent;
import com.example.wlwgateway.net.NetManager;
import com.example.wlwgateway.net.ReadNetThread;
import com.example.wlwgateway.tool.Command;
import com.example.wlwgateway.tool.ToHex;

import de.greenrobot.event.EventBus;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.net.Socket;

public class MainActivity extends Activity implements View.OnClickListener {

	private TextView tvMsg;
	private ListView deList;
	private Button btn1;
	private DeviceAdapter adapter;
	private Button btn2;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvMsg = (TextView)findViewById(R.id.textView1);
		tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());

		deList = (ListView) findViewById(R.id.devicelist);
		tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
		btn1=(Button)findViewById(R.id.DeviceState);
		btn1.setOnClickListener(this);
		btn2=(Button)findViewById(R.id.clearLog);
		btn2.setOnClickListener(this);
		adapter =DeviceAdapter.getDefaultAdapter( new DeviceAdapter(this));
		deList.setAdapter(adapter);
		ComManager.getInstance().open();
        NetManager.start();
    }
	
	public void onEventMainThread(ComEvent event) {
		tvMsg.append("ComEvent:"+event.getMessage()+"\n");
		reFlashDevice(event.getMessage());
		adapter.notifyDataSetChanged();
	}
	public void onEventMainThread(NetEvent event) {
		tvMsg.append("NetEvent:"+event.getMessage()+"\n");
	}
	public void onEventMainThread(LinkEvent event) {
		tvMsg.append("LinkEvent"+event.getMessage()+"\n");
	}
	
	public void onResume() {
		super.onResume();
		EventBus.getDefault().register(this);
	}
	
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View view) {
		//
		if(view.getId()==R.id.DeviceState) {
			ComManager.getInstance().sendToXtq(Command.getDeviceStataCommand());
			//SystemClock.sleep(1000);
			adapter.notifyDataSetChanged();
		}

		if(view.getId()==R.id.clearLog){
			tvMsg.setText("");
		}
	}

	public boolean reFlashDevice(String msg){
		String[] ff=msg.split(" ");
		int index=0;
		if(ff[1]=="01")
			index=1;
		if(ff[1]=="02")
			index=2;
		if(ff[1]=="03")
			index=3;

		for(int i=0;i<adapter.getCount();i++){
			Device d=adapter.getItem(i);

			if ((d.getName()=="Router1"&&index==1)||
						(d.getName()=="Router2"&&index==2)||(d.getName()=="Router3"&&index==3)) {


					adapter.getItem(i).setState("online");
					//d.setState("online");
					//adapter.add(d);
					//adapter.remove(i);
					//adapter.add(d);
			}

			if(d.getName()=="mobile"){
				adapter.remove(i);
			}
		}

		String url = "http://img0.imgtn.bdimg.com/it/u=4007200800,283936593&fm=214&gp=0.jpg";
		int i=0;
		for(Socket phone: ReadNetThread.mobiles.keySet()){
			i++;
			adapter.add(new Device("mobile", "N0"+phone.getPort(), "online", url));
		}

		//adapter.notifyDataSetChanged();
		return  true;
	}
}
