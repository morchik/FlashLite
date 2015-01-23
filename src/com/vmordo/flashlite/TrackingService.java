package com.vmordo.flashlite;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

public class TrackingService extends Service {

	final String LOG_TAG = "myLogsServ";

	public void onCreate() {
		Cnt.set(getApplicationContext());
		super.onCreate();
		Log.e(LOG_TAG, "onCreate TrackingService");
		startService(new Intent(this, TrackingService.class));
	}

	@SuppressWarnings("deprecation")
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(LOG_TAG, "onStartCommand TrackingService");
		int NOTIFICATION_ID = 1;
		 {

			int icon =  R.drawable.ic_launcher;
			long when = System.currentTimeMillis();
			Context context = getBaseContext();

			// Notification notification = new Notification.Builder(context)
			// .setContentTitle("started foreground " )
			// .setContentText("EarFlap")
			// .setSmallIcon(R.drawable.new_mail)
			// .setLargeIcon(aBitmap)
			// .build();
			Notification notification = new android.app.Notification(icon, "test",
					when);
			// �������� ��������� � ��������� ������ ����� Activity, �������
			// ������
			// ������� ��� ������� �� ����������.
			Intent notificationIntent = new Intent(this, FlashLiteActivity.class);
			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			String txt = "cam test";
			notification.setLatestEventInfo(context, "what", txt, contentIntent);
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
		Timer myTimer = new Timer(); // ������� ������
		Log.e(LOG_TAG, " someTask ");
		myTimer.schedule(new TimerTask() { // ���������� ������
					@Override
					public void run() {
						Log.e(LOG_TAG, " start to take foto ");
						if (PhotoTask.taskStarted < 1) {
							PhotoTask taskP = new PhotoTask();
							taskP.executeOnExecutor(
									AsyncTask.THREAD_POOL_EXECUTOR,
									new String[] {});
						}
					}
				}, 2000L, 10L * 1000); // ��������

	}
}