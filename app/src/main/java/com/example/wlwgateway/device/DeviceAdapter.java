package com.example.wlwgateway.device;

import java.net.Socket;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wlwgateway.R;
import com.example.wlwgateway.net.ReadNetThread;

import org.xutils.image.ImageOptions;
import org.xutils.x;

class ViewHolder {
    public TextView tv1;
    public TextView tv2;
    public TextView tv3;
    public ImageView iv;
}

public class DeviceAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Device> data = new ArrayList<Device>();

    public DeviceAdapter(Context ctx) {
        context = ctx;
    }

    public void add(Device dev) {
        data.add(dev);
    }

    public void remove(int arg0) {
        data.remove(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Device getItem(int arg0) {
        // TODO Auto-generated method stub
        return data.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View view, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder vh;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.device_item, null);
            TextView tv1 = (TextView)view.findViewById(R.id.textView1);
            TextView tv2 = (TextView)view.findViewById(R.id.textView2);
            TextView tv3 = (TextView)view.findViewById(R.id.textView3);
            ImageView iv = (ImageView)view.findViewById(R.id.imageView1);
            vh = new ViewHolder();
            vh.tv1 = tv1;
            vh.tv2 = tv2;
            vh.tv3 = tv3;
            vh.iv = iv;
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }

        Device dev = data.get(arg0);
        vh.tv1.setText(dev.getName());
        vh.tv2.setText(dev.getNo());
        vh.tv3.setText(dev.getState());

        ImageOptions options = new ImageOptions.Builder().
                setCircular(true).
                setCrop(true).setSize(30,30).
                setFadeIn(true).build(); //淡入效果
        x.image().bind(vh.iv, dev.getPic(), options);

        return view;
    }

    public static DeviceAdapter getDefaultAdapter(DeviceAdapter adapter){
        if(adapter.getCount()==0) {
            String url1 = "https://graph.baidu.com/thumb/1096476340,2139364727.jpg";
            String url2 = "http://img0.imgtn.bdimg.com/it/u=4007200800,283936593&fm=214&gp=0.jpg";
            adapter.add(new Device("Router1", "01", "online", url1));
            adapter.add(new Device("Router2", "02", "online", url1));
            adapter.add(new Device("Router3", "03", "online", url1));
            adapter.add(new Device("Coordinator", "01", "online", url1));
            int i=0;
            for(Socket phone: ReadNetThread.mobiles.keySet()){
                i++;
                adapter.add(new Device("mobile", "N0"+phone.getPort(), "online", url2));
            }
        }
        return adapter;
    }

}
