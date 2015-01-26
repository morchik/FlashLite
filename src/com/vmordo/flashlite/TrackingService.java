package com.vmordo.flashlite;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings.System;
import android.widget.RemoteViews;

public class TrackingService extends Service {

	final String LOG_TAG = "myLogsServ";

	public void onCreate() {
		Cnt.set(getApplicationContext());
		super.onCreate();
		Log.e(LOG_TAG, "onCreate TrackingService");
		startService(new Intent(this, TrackingService.class));
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(LOG_TAG, "onStartCommand TrackingService");
		int NOTIFICATION_ID = 1;
		{

			int icon = R.drawable.ic_launcher;

			Intent notificationIntent = new Intent(this,
					FlashLiteActivity.class);
			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
					notificationIntent, 0);

			RemoteViews views = new RemoteViews(getPackageName(), R.layout.notify_bar);
			//views.addView(viewId, nestedView)
			views.setImageViewResource(R.id.imagenotileft,R.drawable.ic_launcher);
			// Locate and set the Text into  TextViews
			views.setTextViewText(R.id.title,"Custom notification");
			views.setTextViewText(R.id.text,"This is a custom layout");
			
			//TakePhoto.setSV(sv);
			Notification notification = new Notification.Builder(this)
					.setContent(views)
					.setContentIntent(contentIntent)
					.setWhen(java.lang.System.currentTimeMillis())
					.setSmallIcon(icon)
					.setTicker("test")
					.build();

			startForeground(NOTIFICATION_ID, notification);
			someTask();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG, "onDestroy");
	}

	public IBinder onBind(Intent intent) {
		Log.d(LOG_TAG, "onBind");
		return null;
	}

	public void someTask() {
		Timer myTimer = new Timer(); // Создаем таймер
		Log.e(LOG_TAG, " someTask ");
		myTimer.schedule(new TimerTask() { // Определяем задачу
					@Override
					public void run() {
						Log.e(LOG_TAG, " start to take foto ");
						Intent intent = new Intent(Cnt.get(), CamActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
					}
				}, 10000L, 10L * 1000); // интервал

	}
}