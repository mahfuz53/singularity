package com.presenter.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

public class dialog {
	
	public static Dialog showBottomDialog(Context contex,int layoutId, int width, int height,int x, int y) {
			  Dialog dialog = new Dialog(contex);
			  dialog.setCanceledOnTouchOutside(false);
			  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			  dialog.setContentView(layoutId);

			  WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

			  lp.copyFrom(dialog.getWindow().getAttributes());
			  if (width != -1) {
			   lp.width = width;
			   if (height != -1)
			    lp.height = height;
			  }
			  if (x != -1) {
			   lp.x = x;
			   if (y != -1)
			    lp.y = y;
			  }
			  dialog.getWindow().setAttributes(lp);

			  dialog.show();
			  return dialog;
			 }


}
