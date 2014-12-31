package com.app.tomore;

import com.app.tomore.beans.ArticleModel;
import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;
public class MagVideoActivity extends Activity implements MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener{
 public static final String TAG = "VideoPlayer";
 private VideoView mVideoView;
 private Uri mUri;
 private int mPositionWhenPaused = -1;
 private DialogActivity dialog;

 private MediaController mMediaController;
 private ArticleModel articleItem;
 //VideoView detailView = (VideoView)findViewById(R.id.videoview);
	//String video = articleItem.getArticleVideo();
 /* (non-Javadoc)
  * @see android.app.Activity#onCreate(android.os.Bundle)
  */
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  super.onCreate(savedInstanceState);
  setContentView(R.layout.video_detail);
  
  //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
  
//文件路径
  new GetData(MagVideoActivity.this, 1).execute("");
  BindData();
	
 }

 

//监听MediaPlayer上报的错误信息

 @Override
 public boolean onError(MediaPlayer mp, int what, int extra) {
  // TODO Auto-generated method stub
  return false;
 }

 

//Video播完的时候得到通知

 @Override
 public void onCompletion(MediaPlayer mp) {
  this.finish();
 }
 

  public void onStart() {
      // Play Video
      mVideoView.setVideoURI(mUri);
      mVideoView.start();

      super.onStart();
     }


     public void onPause() {
      // Stop video when the activity is pause.
      mPositionWhenPaused = mVideoView.getCurrentPosition();
      mVideoView.stopPlayback();
      Log.d(TAG, "OnStop: mPositionWhenPaused = " + mPositionWhenPaused);
      Log.d(TAG, "OnStop: getDuration  = " + mVideoView.getDuration());

      super.onPause();
     }

 

     public void onResume() {
      // Resume video player
      if(mPositionWhenPaused >= 0) {
       mVideoView.seekTo(mPositionWhenPaused);
       mPositionWhenPaused = -1;
      }

      super.onResume();
     }

     private class GetData extends AsyncTask<String, String, String> {
 		// private Context mContext;
 		private int mType;

 		private GetData(Context context, int type) {
 			// this.mContext = context;
 			this.mType = type;
 			dialog = new DialogActivity(context, type);
 		}

 		@Override
 		protected void onPreExecute() {
 			if (mType == 1) {
 				if (null != dialog && !dialog.isShowing()) {
 					dialog.show();
 				}
 			}
 			super.onPreExecute();
 		}

 		@Override
 		protected String doInBackground(String... params) {
 			String result= null;
 			//try {
 				Log.d("doInBackground", "start request");	
 				Log.d("doInBackground", "returned");
 			//} 
// 			catch (IOException e) {
// 				e.printStackTrace();
// 			} catch (TimeoutException e) {
// 				e.printStackTrace();
// 			}

 			return result;
 		}

 		@Override
 		protected void onPostExecute(String result) {
 			if (null != dialog) {
 				dialog.dismiss();
 			}
 			Log.d("onPostExecute", "postExec state");
 			if (result == null || result.equals("")) {
 				// show empty alert
 			} else {
 				//cardList = new ArrayList<CardModel>();
 				try {
 					//cardList = new CardsParse().parseCardResponse(result);
 					//BindDataToListView();
 					BindData();
 				} catch (JsonSyntaxException e) {
 					e.printStackTrace();
 				}
 				if (articleItem != null) {
 					//Intent intent = new Intent(MemberDetailActivity.this,
 						//	MyCameraActivity.class); // fake redirect..
 					//intent.putExtra("cardList", (Serializable) cardList);
 					//startActivity(intent);
 				} else {
 					// show empty alert
 				}
 			}
 		}
 	}
     private void BindData()
 	{
    	 mVideoView = (VideoView)findViewById(R.id.videoview);
    	 mUri = Uri.parse(articleItem.getArticleVideo());
         mMediaController = new MediaController(this);
         mVideoView.setMediaController(mMediaController);
 	}


}