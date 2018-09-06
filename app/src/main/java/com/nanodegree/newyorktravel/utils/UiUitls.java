package com.nanodegree.newyorktravel.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.nanodegree.newyorktravel.R;

public class UiUitls {

	public void showErrorAlert(Context context, RuntimeException exception){
		if(context != null){
			String errorMsg = context.getString(R.string.unknown_error_msg);
			if(exception != null && exception.getMessage() != null && exception.getMessage().length() > 0){
				errorMsg = exception.getMessage();
			}

			AlertDialog dialog = new AlertDialog.Builder(context)
					.setTitle(context.getString(R.string.unkown_error_title))
					.setMessage(errorMsg)
					.setPositiveButton(context.getString(android.R.string.ok), null)
					.create();

			dialog.show();
		}
	}
}