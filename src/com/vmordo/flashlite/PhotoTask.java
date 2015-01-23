package com.vmordo.flashlite;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

@SuppressLint("DefaultLocale")
public class PhotoTask extends AsyncTask<String, Integer, String> {
	private static String LOG_TAG = "PhotoTask";
	public static int taskStarted = 0;

	@Override
	protected String doInBackground(String... params) {
		String res = "OK";
		taskStarted = taskStarted + 1;
		try {
			Log.e(LOG_TAG, "doInBackground " + params.length);
			publishProgress(1);
		} catch (Throwable e) {
			e.printStackTrace();
			Log.e(LOG_TAG, e.toString());
			res = "ER";
		} finally {
			taskStarted = taskStarted - 1;
		}
		return res;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		Log.e(LOG_TAG, "onProgressUpdate " + values[0]);
		TakePhoto.getOne(null);
	}

	@Override
	protected void onPostExecute(String res) {
		Log.e(LOG_TAG, "onPostExecute " + res);
		super.onPostExecute(res);
	}

}
