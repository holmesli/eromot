package com.app.tomore;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.app.tomore.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class CameroActivity extends Activity implements SurfaceHolder.Callback{
	private static final String tag="yan";
	private boolean isPreview = false;
	private SurfaceView mPreviewSV = null; //棰勮SurfaceView
	private SurfaceHolder mySurfaceHolder = null;
	private ImageButton mPhotoImgBtn = null;
	private Camera myCamera = null;
	private Bitmap mBitmap = null;
	private AutoFocusCallback myAutoFocusCallback = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window myWindow = this.getWindow();
		myWindow.setFlags(flag, flag);

		setContentView(R.layout.activity_rect_photo);

		//鍒濆鍖朣urfaceView
		mPreviewSV = (SurfaceView)findViewById(R.id.previewSV);
		mySurfaceHolder = mPreviewSV.getHolder();
		mySurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);//translucent鍗婇�鏄�transparent閫忔槑
		mySurfaceHolder.addCallback(this);
		mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		//鑷姩鑱氱劍鍙橀噺鍥炶皟
		myAutoFocusCallback = new AutoFocusCallback() {

			public void onAutoFocus(boolean success, Camera camera) {
				// TODO Auto-generated method stub
				if(success)//success琛ㄧず瀵圭劍鎴愬姛
				{
					Log.i(tag, "myAutoFocusCallback: success...");
					//myCamera.setOneShotPreviewCallback(null);

				}
				else
				{
					//鏈鐒︽垚鍔�					Log.i(tag, "myAutoFocusCallback: 澶辫触浜�..");

				}


			}
		};

		mPhotoImgBtn = (ImageButton)findViewById(R.id.photoImgBtn);
		//鎵嬪姩璁剧疆鎷嶇収ImageButton鐨勫ぇ灏忎负120脳120,鍘熷浘鐗囧ぇ灏忔槸64脳64
		LayoutParams lp = mPhotoImgBtn.getLayoutParams();
		lp.width = 120;
		lp.height = 120;		
		mPhotoImgBtn.setLayoutParams(lp);				
		mPhotoImgBtn.setOnClickListener(new PhotoOnClickListener());
		mPhotoImgBtn.setOnTouchListener(new MyOnTouchListener());


	}


	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) 
	// 褰揝urfaceView/棰勮鐣岄潰鐨勬牸寮忓拰澶у皬鍙戠敓鏀瑰彉鏃讹紝璇ユ柟娉曡璋冪敤
	{
		// TODO Auto-generated method stub		
		Log.i(tag, "SurfaceHolder.Callback:surfaceChanged!");
		initCamera();

	}


	public void surfaceCreated(SurfaceHolder holder) 
	// SurfaceView鍚姩鏃�鍒濇瀹炰緥鍖栵紝棰勮鐣岄潰琚垱寤烘椂锛岃鏂规硶琚皟鐢ㄣ�
	{
		// TODO Auto-generated method stub		
		myCamera = Camera.open();
		try {
			myCamera.setPreviewDisplay(mySurfaceHolder);
			Log.i(tag, "SurfaceHolder.Callback: surfaceCreated!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(null != myCamera){
				myCamera.release();
				myCamera = null;
			}
			e.printStackTrace();
		}



	}


	public void surfaceDestroyed(SurfaceHolder holder) 
	//閿�瘉鏃惰璋冪敤
	{
		// TODO Auto-generated method stub
		Log.i(tag, "SurfaceHolder.Callback锛歋urface Destroyed");
		if(null != myCamera)
		{
			myCamera.setPreviewCallback(null); /*鍦ㄥ惎鍔≒reviewCallback鏃惰繖涓繀椤诲湪鍓嶄笉鐒堕�鍑哄嚭閿欍�
			杩欓噷瀹為檯涓婃敞閲婃帀涔熸病鍏崇郴*/
			
			myCamera.stopPreview(); 
			isPreview = false; 
			myCamera.release();
			myCamera = null;     
		}

	}

		public void initCamera(){
		if(isPreview){
			myCamera.stopPreview();
		}
		if(null != myCamera){			
			Camera.Parameters myParam = myCamera.getParameters();
			//			//鏌ヨ灞忓箷鐨勫鍜岄珮
			//			WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
			//			Display display = wm.getDefaultDisplay();
			//			Log.i(tag, "灞忓箷瀹藉害锛�+display.getWidth()+" 灞忓箷楂樺害:"+display.getHeight());

			myParam.setPictureFormat(PixelFormat.JPEG);//璁剧疆鎷嶇収鍚庡瓨鍌ㄧ殑鍥剧墖鏍煎紡

			//			//鏌ヨcamera鏀寔鐨刾icturesize鍜宲reviewsize
			//			List<Size> pictureSizes = myParam.getSupportedPictureSizes();
			//			List<Size> previewSizes = myParam.getSupportedPreviewSizes();
			//			for(int i=0; i<pictureSizes.size(); i++){
			//				Size size = pictureSizes.get(i);
			//				Log.i(tag, "initCamera:鎽勫儚澶存敮鎸佺殑pictureSizes: width = "+size.width+"height = "+size.height);
			//			}
			//			for(int i=0; i<previewSizes.size(); i++){
			//				Size size = previewSizes.get(i);
			//				Log.i(tag, "initCamera:鎽勫儚澶存敮鎸佺殑previewSizes: width = "+size.width+"height = "+size.height);
			//
			//			}


			//璁剧疆澶у皬鍜屾柟鍚戠瓑鍙傛暟
			myParam.setPictureSize(1280, 960);
			myParam.setPreviewSize(960, 720);			
			//myParam.set("rotation", 90);  			
			myCamera.setDisplayOrientation(90);  
			myParam.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			myCamera.setParameters(myParam);			
			myCamera.startPreview();
			myCamera.autoFocus(myAutoFocusCallback);
			isPreview = true;
		}
	}

	/*涓轰簡瀹炵幇鎷嶇収鐨勫揩闂ㄥ０闊冲強鎷嶇収淇濆瓨鐓х墖闇�涓嬮潰涓変釜鍥炶皟鍙橀噺*/
	ShutterCallback myShutterCallback = new ShutterCallback() 
	//蹇棬鎸変笅鐨勫洖璋冿紝鍦ㄨ繖閲屾垜浠彲浠ヨ缃被浼兼挱鏀锯�鍜斿殦鈥濆０涔嬬被鐨勬搷浣溿�榛樿鐨勫氨鏄挃鍤撱�
	{

		public void onShutter() {
			// TODO Auto-generated method stub
			Log.i(tag, "myShutterCallback:onShutter...");

		}
	};
	PictureCallback myRawCallback = new PictureCallback() 
	// 鎷嶆憚鐨勬湭鍘嬬缉鍘熸暟鎹殑鍥炶皟,鍙互涓簄ull
	{

		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			Log.i(tag, "myRawCallback:onPictureTaken...");

		}
	};
	PictureCallback myJpegCallback = new PictureCallback() 
	//瀵筳peg鍥惧儚鏁版嵁鐨勫洖璋�鏈�噸瑕佺殑涓�釜鍥炶皟
	{

		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			Log.i(tag, "myJpegCallback:onPictureTaken...");
			if(null != data){
				mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);//data鏄瓧鑺傛暟鎹紝灏嗗叾瑙ｆ瀽鎴愪綅鍥�				myCamera.stopPreview();
				isPreview = false;
			}
			//璁剧疆FOCUS_MODE_CONTINUOUS_VIDEO)涔嬪悗锛宮yParam.set("rotation", 90)澶辨晥銆傚浘鐗囩珶鐒朵笉鑳芥棆杞簡锛屾晠杩欓噷瑕佹棆杞笅
			Matrix matrix = new Matrix();
			matrix.postRotate((float)90.0);
			Bitmap rotaBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, false);
			//淇濆瓨鍥剧墖鍒皊dcard
			if(null != rotaBitmap)
			{
				saveJpeg(rotaBitmap);
			}

			//鍐嶆杩涘叆棰勮
			myCamera.startPreview();
			isPreview = true;
		}
	};
	public class PhotoOnClickListener implements OnClickListener{

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(isPreview && myCamera!=null){
				myCamera.takePicture(myShutterCallback, null, myJpegCallback);	
			}

		}
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			
		}
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}

	}
	
	public void saveJpeg(Bitmap bm){
		String savePath = "/mnt/sdcard/rectPhoto/";
		File folder = new File(savePath);
		if(!folder.exists()) //濡傛灉鏂囦欢澶逛笉瀛樺湪鍒欏垱寤�		
			{
			folder.mkdir();
		}
		long dataTake = System.currentTimeMillis();
		String jpegName = savePath + dataTake +".jpg";
		Log.i(tag, "saveJpeg:jpegName--" + jpegName);
		//File jpegFile = new File(jpegName);
		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);

			//			//濡傛灉闇�鏀瑰彉澶у皬(榛樿鐨勬槸瀹�60脳楂�280),濡傛敼鎴愬600脳楂�00
			//			Bitmap newBM = bm.createScaledBitmap(bm, 600, 800, false);

			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			Log.i(tag, "saveJpeg锛氬瓨鍌ㄥ畬姣曪紒");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(tag, "saveJpeg:瀛樺偍澶辫触锛�");
			e.printStackTrace();
		}
	}

	
	public class MyOnTouchListener implements OnTouchListener{

		public final  float[] BT_SELECTED=new float[]
				{ 2, 0, 0, 0, 2,
			0, 2, 0, 0, 2,
			0, 0, 2, 0, 2,
			0, 0, 0, 1, 0 };			    

		public final float[] BT_NOT_SELECTED=new float[]
				{ 1, 0, 0, 0, 0,
			0, 1, 0, 0, 0,
			0, 0, 1, 0, 0,
			0, 0, 0, 1, 0 };
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			}
			else if(event.getAction() == MotionEvent.ACTION_UP){
				v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
				
			}
			return false;
		}

	}
	
	@Override
	public void onBackPressed()
		{
		// TODO Auto-generated method stub
		super.onBackPressed();
		CameroActivity.this.finish();
	}
}
