package com.example.wlwgateway;


import android.app.Application;

import org.xutils.x;

public class MyApp extends Application {
	   public void onCreate() {
		    super.onCreate();
		    x.Ext.init(this);
		    x.Ext.setDebug(true);
		    }
}
