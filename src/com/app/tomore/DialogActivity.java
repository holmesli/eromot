package com.app.tomore;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class DialogActivity extends Dialog {
	public Context mContext;
	public Dialog dialog;
	private TextView tvMessage;
	private Button btnSure;
	//private Button btnCancel;
	public int mType;
	

	public DialogActivity(Context context, int type) {
		super(context, R.style.dialog_meijika);
		this.mContext = context;
		this.mType = type;
		if (mType == 1) {
			setContentView(R.layout.dialog_load);
			//btnCancel = (Button) findViewById(R.id.btn_cancel);
			//disDialog();
		} else {
			setContentView(R.layout.dialog_prompt);
			tvMessage = (TextView) findViewById(R.id.tv_message);
			btnSure = (Button) findViewById(R.id.btn_sure);
		}
		setProperty();
	}

	private void setProperty() {
		Window window = getWindow();
		WindowManager.LayoutParams p = window.getAttributes();
		Display d = getWindow().getWindowManager().getDefaultDisplay();
		Point outSize = new Point();
		d.getSize(outSize);

		p.height = outSize.y;//   (int) (d.getHeight() * 1);
		p.width = outSize.x;//(int) (d.getWidth() * 1);
		window.setAttributes(p);
	}

	public void setShowMessage(String showMessage) {
		tvMessage.setText(showMessage);
	}

	public Button getBtnSure() {
		return btnSure;
	}
	/*
	public Button getBtnCancel() {
		return btnCancel;
	}

	public void disDialog() {
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ApplicationData.isCancel = false;
				dis();
			}
		});
	}
	
	private void dis() {
		this.dismiss();
	}
	*/
}
