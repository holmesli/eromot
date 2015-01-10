package com.app.tomore;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogActivity {
	public Context mContext;
	ProgressDialog pd;
	private int mType;
	private boolean isShowing;
	
	public DialogActivity(Context context, int type) {
		this.mContext = context;
		this.mType = type;
	}
	
	public boolean isShowing()
	{
		return isShowing;
	}
		
	public void show() {
		if (pd == null) {
			pd = new ProgressDialog(mContext);
			pd.setMessage("Loading....");
			isShowing = true;
		}
		pd.show();
	}

	public void dismiss() {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
			isShowing = false;
		}
	}

}
