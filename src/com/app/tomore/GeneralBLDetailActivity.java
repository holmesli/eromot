package com.app.tomore;

import java.util.ArrayList;
import java.util.List;

import com.app.tomore.beans.GeneralBLModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeneralBLDetailActivity extends Activity {

	private GeneralBLModel BLModel;
	private GoogleMap map;
	private View layout;
	private EditText messagetosent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.general_bl_detail_layout);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		getWindow().setSoftInputMode(  WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		Intent intent = getIntent();
		BLModel = (GeneralBLModel) intent.getSerializableExtra("BLdata");
		layout = findViewById(R.id.general_bl_detail_layout);
		final Button btnBack = (Button) layout.findViewById(R.id.bar_title_general_detail_go_back);

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		BindData();
		ImageView Call = (ImageView) getWindow().getDecorView()
				.findViewById(R.id.CallImage);
		Call.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	showPopup();
		    }
		});
		messagetosent = (EditText) layout.findViewById(R.id.messagetosent);

		TextView sendlabel = (TextView) layout.findViewById(R.id.sendlabel);
		sendlabel.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	SentText(messagetosent.getText().toString());
		    }
		});
	}
	
	private void showPopup(){
		
		String Cancel = getString(R.string.Cancel);
		String Phone1 = BLModel.getPhone1();
		List<CharSequence>  cs = new ArrayList<CharSequence>();
		cs.add(Phone1);
    	if(BLModel.getPhone2() != null){
    		if(BLModel.getPhone2().length() > 7 || !BLModel.getPhone2().equals(""))
    		{
    			cs.add(BLModel.getPhone2());
    		}
    	}
		cs.add(Cancel);
    	CharSequence [] options = cs.toArray(new CharSequence[cs.size()]);
    	final int length = options.length;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.PhoneCall));
		builder.setItems(options, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface Optiondialog, int which) {
				String phone_number = BLModel.getPhone1();
		        if (which == 0){
					Intent call = new Intent(Intent.ACTION_DIAL);
					call.setData(Uri.parse("tel:"+phone_number));
					startActivity(call);
		        }
		        else if (which == 1 && length == 3)
		        {
		        		Intent call = new Intent(Intent.ACTION_DIAL);
		        		call.setData(Uri.parse("tel:"+BLModel.getPhone2()));
		        		startActivity(call);
		        }

		    }
		});
		builder.show();
		
	}
	private void SentText(final String text){
		String Cancel = getString(R.string.Cancel);
		String Phone1 = BLModel.getPhone1();
		List<CharSequence>  cs = new ArrayList<CharSequence>();
		cs.add(Phone1);
    	if(BLModel.getPhone2() != null){
    		if(BLModel.getPhone2().length() > 7 || !BLModel.getPhone2().equals(""))
    		{
    			cs.add(BLModel.getPhone2());
    		}
    	}
		cs.add(Cancel);
    	CharSequence [] options = cs.toArray(new CharSequence[cs.size()]);
    	final int length = options.length;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.PhoneCall));
		builder.setItems(options, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface Optiondialog, int which) {
		        if (which == 0){
		        	Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		        	sendIntent.putExtra("sms_body", text); 
		        	sendIntent.setType("vnd.android-dir/mms-sms");
		        	startActivity(sendIntent);
		        }
		        else if (which == 1 && length == 3)
		        {
		        	Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		        	sendIntent.putExtra("sms_body", text); 
		        	sendIntent.setType("vnd.android-dir/mms-sms");
		        	startActivity(sendIntent);
		        }

		    }
		});
		builder.show();
		
	}
	
	
	
	private void BindData()
	{
		TextView languageView = (TextView) getWindow().getDecorView()
				.findViewById(R.id.serviceLanguage);
		TextView contact = (TextView) getWindow().getDecorView()
				.findViewById(R.id.contact);
		TextView infotext = (TextView) getWindow().getDecorView()
				.findViewById(R.id.infotext);
		TextView detailinfotext = (TextView) getWindow().getDecorView()
				.findViewById(R.id.detailinfotext);
		TextView addressinfotext = (TextView) getWindow().getDecorView()
				.findViewById(R.id.addressinfotext);
		TextView phoneinfo = (TextView) getWindow().getDecorView()
				.findViewById(R.id.phoneinfo);
		TextView phoneinfo2 = (TextView) getWindow().getDecorView()
				.findViewById(R.id.phoneinfo2);
		EditText messagetosent = (EditText) getWindow().getDecorView()
				.findViewById(R.id.messagetosent);
		TextView sendlabel = (TextView) getWindow().getDecorView()
				.findViewById(R.id.sendlabel);
		
		String servicelanuage = getString (R.string.servicelanuage);
		String contacttext = getString (R.string.contact);
		String detailinfo_Chinese = getString (R.string.detailinfo);
		String addressinfo_Chinese = getString (R.string.addressinfo);
		String phone = getString (R.string.phone);
		String sent = getString (R.string.sent);
		
		String language = "";
        if(BLModel.getLanguage_C().equals("1"))
        {
        	language = getString(R.string.cantonese) ;
        }
        
        if(BLModel.getLanguage_E().equals("1")) 
        {
        	if(BLModel.getLanguage_C().equals("1")){
        			language = language + "/" + getString(R.string.english);;
        	}
        	else{
        		language = getString(R.string.english);;
        	}
        }
       
        if(BLModel.getLanguage_M().equals("1")) 
        {
        	if(BLModel.getLanguage_C().equals("1") || BLModel.getLanguage_E().equals("1"))
        	{
        		language = language + "/" + getString(R.string.mandarin);
        	}
        	else{
        		language = getString(R.string.mandarin);
        	}
        }
        
        languageView.setText(servicelanuage +":" + language);
        contact.setText(contacttext + ":   " +BLModel.getContact());
        infotext.setText(detailinfo_Chinese);
        detailinfotext.setText(BLModel.getContent());
        addressinfotext.setText(addressinfo_Chinese);
        phoneinfo.setText(phone + ": " + BLModel.getPhone1());
        if(BLModel.getPhone2().equals("")){
        	phoneinfo2.setVisibility(View.INVISIBLE);
        }
        else{
        	phoneinfo2.setText(phone + ": " + BLModel.getPhone2());
        }
        sendlabel.setText(sent);
        
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        try {
	        final LatLng location = new LatLng(Double.parseDouble(BLModel.getLatitude()), Double.parseDouble(BLModel.getLongitude()));
	        
	        Marker go_to_location = map.addMarker(new MarkerOptions().position(location)
	                .title(BLModel.getTitle()));
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
	
	        // Zoom in, animating the camera.
	        map.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null); 
        } catch (NullPointerException e) {
            System.out.print("Caught the NullPointerException");
        }
    }
}


