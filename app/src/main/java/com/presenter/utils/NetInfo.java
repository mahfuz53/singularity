package com.presenter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetInfo {

	/*
	 * check internet info
	 */

	public static boolean isOnline(final Context ctx) {
		final ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null) {
			return ni.isConnectedOrConnecting();
		} else {
			return false;
		}
	}

}
