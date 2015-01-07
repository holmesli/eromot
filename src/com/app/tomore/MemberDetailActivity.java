package com.app.tomore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;

import com.app.tomore.beans.CardModel;
import com.app.tomore.net.CardsRequest;
import com.app.tomore.net.ToMoreParse;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MemberDetailActivity extends Activity {

	private DialogActivity dialog;
	private CardModel cardItem;
	private ImageView frontImageView;
	private ImageView backImageView;
	private ImageView barcodeImageView;
	private Button btnFrontEdit;
	private Button btnBackEdit;
	private Button btnGenerateBarcode;
	private Button btnSubmit;
	private Button btnEdit;
	private EditText editTitle;
	private EditText editDes;
	private EditText editBarcode;
	private TextView barcodeValueLable;
	private TextView barcodeLable;
	private TextView titleLable;
	private TextView desLable;
	private Bitmap bitmap = null;
	public static final int REQUEST_CAMERA_FOR_FRONT = 1;
    public static final int SELECT_FILE_FOR_FRONT = 2;
	public static final int REQUEST_CAMERA_FOR_BACK = 3;
    public static final int SELECT_FILE_FOR_BACK = 4;
    Uri imageUri;
    long totalSize = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		cardItem = (CardModel) intent.getSerializableExtra("cardList");
		setContentView(R.layout.member_detail);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);

		frontImageView = (ImageView) getWindow().getDecorView().findViewById(
				R.id.frontViewImg);
		backImageView = (ImageView) getWindow().getDecorView().findViewById(
				R.id.backViewImg);
		barcodeImageView = (ImageView) getWindow().getDecorView().findViewById(
				R.id.barcodeImg);

		btnFrontEdit = (Button) getWindow().getDecorView().findViewById(
				R.id.btnFrontEdit);
		btnBackEdit = (Button) getWindow().getDecorView().findViewById(
				R.id.btnBackEdit);
		btnGenerateBarcode = (Button) getWindow().getDecorView().findViewById(
				R.id.btnGenerateBarcode);
		btnSubmit = (Button) getWindow().getDecorView().findViewById(
				R.id.btnSubmit);
		editTitle = (EditText) getWindow().getDecorView().findViewById(
				R.id.editTitle);
		editDes = (EditText) getWindow().getDecorView().findViewById(
				R.id.editDes);
		editBarcode = (EditText) getWindow().getDecorView().findViewById(
				R.id.editBarcode);
		barcodeValueLable = (TextView) getWindow().getDecorView().findViewById(
				R.id.barcodeValueLable);
		barcodeLable = (TextView) getWindow().getDecorView().findViewById(
				R.id.barcodeLable);
		titleLable = (TextView) getWindow().getDecorView().findViewById(
				R.id.titleLable);
		desLable = (TextView) getWindow().getDecorView().findViewById(
				R.id.desLable);
		
		RelativeLayout rl = (RelativeLayout) getWindow().getDecorView()
				.findViewById(R.id.bar_title_member_add);
		
		btnEdit = (Button) rl.findViewById(R.id.bar_title_bt_edit);
		
		try {
			bitmap = encodeAsBitmap(cardItem.getCardBarcode(),
					BarcodeFormat.CODE_128, 600, 250);
			barcodeImageView.setImageBitmap(bitmap);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		BindData();
		new GetData(MemberDetailActivity.this, 1).execute("");		

		btnEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (cardItem == null) {
					return;
				}

				if (btnEdit.getText().equals("取消")) {

					btnFrontEdit.setVisibility(View.INVISIBLE);
					btnBackEdit.setVisibility(View.INVISIBLE);
					btnGenerateBarcode.setVisibility(View.INVISIBLE);
					btnSubmit.setVisibility(View.INVISIBLE);

					if (cardItem.getCardType().equals("1")) {
						editTitle.setVisibility(View.INVISIBLE);
						editDes.setVisibility(View.INVISIBLE);
						editBarcode.setVisibility(View.INVISIBLE);
						titleLable.setVisibility(View.INVISIBLE);
						desLable.setVisibility(View.INVISIBLE);
						barcodeLable.setVisibility(View.INVISIBLE);
					}

					editTitle.setEnabled(false);
					editDes.setEnabled(false);
					editBarcode.setEnabled(false);

					btnEdit.setText("编辑");
					return;
				} else {

					btnFrontEdit.setVisibility(View.VISIBLE);
					btnBackEdit.setVisibility(View.VISIBLE);
					btnGenerateBarcode.setVisibility(View.VISIBLE);
					btnSubmit.setVisibility(View.VISIBLE);

					
					editTitle.setVisibility(View.VISIBLE);
					editDes.setVisibility(View.VISIBLE);
					editBarcode.setVisibility(View.VISIBLE);
					titleLable.setVisibility(View.VISIBLE);
					desLable.setVisibility(View.VISIBLE);
					barcodeLable.setVisibility(View.VISIBLE);

					editTitle.setEnabled(true);
					editDes.setEnabled(true);
					editBarcode.setEnabled(true);

					btnEdit.setText("取消");
				}

			}
		});

		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (cardItem == null) {
					return;
				}
				Log.d("doInBackground", "start request");
				new UploadFileToServer().execute();				
				Log.d("doInBackground", "returned");
			}
		});

		final Button btnBack = (Button) rl
				.findViewById(R.id.bar_title_bt_member);

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		frontImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// loadPhoto(frontImageView,200,200);
				Intent intent = new Intent(MemberDetailActivity.this,
						SpaceImageDetailActivity.class);
				// intent.putExtra("images", (ArrayList<String>) datas);
				// intent.putExtra("position", position);
				int[] location = new int[2];
				frontImageView.getLocationOnScreen(location);
				intent.putExtra("locationX", location[0]);
				intent.putExtra("locationY", location[1]);

				intent.putExtra("Url", cardItem.getFrontViewImage());

				intent.putExtra("width", frontImageView.getWidth());
				intent.putExtra("height", frontImageView.getHeight());
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});

		backImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// loadPhoto(frontImageView,200,200);
				Intent intent = new Intent(MemberDetailActivity.this,
						SpaceImageDetailActivity.class);
				// intent.putExtra("images", (ArrayList<String>) datas);
				// intent.putExtra("position", position);
				int[] location = new int[2];
				frontImageView.getLocationOnScreen(location);
				intent.putExtra("locationX", location[0]);
				intent.putExtra("locationY", location[1]);

				intent.putExtra("Url", cardItem.getBackViewImage());

				intent.putExtra("width", backImageView.getWidth());
				intent.putExtra("height", backImageView.getHeight());
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});

		btnGenerateBarcode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String barcode_data = editBarcode.getText().toString();
				try {
					bitmap = encodeAsBitmap(barcode_data,
							BarcodeFormat.CODE_128, 600, 250);
					barcodeImageView.setImageBitmap(bitmap);
					barcodeValueLable.setText(barcode_data);
				} catch (WriterException e) {
					e.printStackTrace();
				}
			}
		});
		
		btnFrontEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				selectImageForFront();
			}
		});
		
		btnBackEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				selectImageForBack();
			}
		});


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
			String result = null;
			// try {
			Log.d("doInBackground", "start request");

			Log.d("doInBackground", "returned");
			// }
			// catch (IOException e) {
			// e.printStackTrace();
			// } catch (TimeoutException e) {
			// e.printStackTrace();
			// }

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
				// cardList = new ArrayList<CardModel>();
				try {
					// cardList = new CardsParse().parseCardResponse(result);

				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (cardItem != null) {
					// Intent intent = new Intent(MemberDetailActivity.this,
					// MyCameraActivity.class); // fake redirect..
					// intent.putExtra("cardList", (Serializable) cardList);
					// startActivity(intent);
				} else {
					// show empty alert
				}
			}
		}
	}

	private void BindData() {

		// Picasso.with(MemberDetailActivity.this)
		// .load(cardItem.getFrontViewImage()).resize(550, 280)
		// .into(frontImageView);
		// Picasso.with(MemberDetailActivity.this)
		// .load(cardItem.getBackViewImage()).resize(550, 280)
		// .into(backImageView);

		final String frontImageUrl = cardItem.getFrontViewImage();
		frontImageView.setTag(frontImageUrl);
		ImageLoader.getInstance().loadImage(frontImageUrl,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						ImageView imageViewByTag = (ImageView) getWindow()
								.getDecorView().findViewWithTag(imageUri);
						if (imageViewByTag != null) {
							int bwidth = loadedImage.getWidth();
							int bheight = loadedImage.getHeight();
							int swidth = imageViewByTag.getWidth();
							//int sheight = imageViewByTag.getHeight();
							int new_width = swidth;
							int new_height = (int) Math.floor((double) bheight
									* ((double) new_width / (double) bwidth));
							Bitmap newbitMap = Bitmap.createScaledBitmap(
									loadedImage, new_width, new_height, true);
							imageViewByTag.setImageBitmap(newbitMap);
						}
					}
				});

		final String backImageUrl = cardItem.getBackViewImage();
		backImageView.setTag(backImageUrl);
		ImageLoader.getInstance().loadImage(backImageUrl,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						ImageView imageViewByTag = (ImageView) getWindow()
								.getDecorView().findViewWithTag(imageUri);
						if (imageViewByTag != null) {
							int bwidth = loadedImage.getWidth();
							int bheight = loadedImage.getHeight();
							int swidth = imageViewByTag.getWidth();
							//int sheight = imageViewByTag.getHeight();
							int new_width = swidth;
							int new_height = (int) Math.floor((double) bheight
									* ((double) new_width / (double) bwidth));
							Bitmap newbitMap = Bitmap.createScaledBitmap(
									loadedImage, new_width, new_height, true);
							imageViewByTag.setImageBitmap(newbitMap);
						}
					}
				});

		btnFrontEdit.setVisibility(View.INVISIBLE);
		btnBackEdit.setVisibility(View.INVISIBLE);
		btnGenerateBarcode.setVisibility(View.INVISIBLE);
		btnSubmit.setVisibility(View.INVISIBLE);

		editTitle.setText(cardItem.getCardTitle());
		editTitle.setEnabled(false);
		editDes.setText(cardItem.getCardDes());
		editDes.setEnabled(false);
		editBarcode.setText(cardItem.getCardBarcode());
		editBarcode.setEnabled(false);
		barcodeValueLable.setText(cardItem.getCardBarcode());

		if (cardItem.getCardType().equals("1")) {
			editTitle.setVisibility(View.INVISIBLE);
			editDes.setVisibility(View.INVISIBLE);
			editBarcode.setVisibility(View.INVISIBLE);
			titleLable.setVisibility(View.INVISIBLE);
			desLable.setVisibility(View.INVISIBLE);
			barcodeLable.setVisibility(View.INVISIBLE);
		}
		else if(cardItem.getCardType().equals("0"))
		{
			btnEdit.setVisibility(View.INVISIBLE);
		}

	}

	/**************************************************************
	 * getting from com.google.zxing.client.android.encode.QRCodeEncoder
	 * 
	 * See the sites below http://code.google.com/p/zxing/
	 * http://code.google.com
	 * /p/zxing/source/browse/trunk/android/src/com/google/
	 * zxing/client/android/encode/EncodeActivity.java
	 * http://code.google.com/p/zxing
	 * /source/browse/trunk/android/src/com/google/
	 * zxing/client/android/encode/QRCodeEncoder.java
	 */

	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width,
			int img_height) throws WriterException {
		String contentsToEncode = contents;
		if (contentsToEncode == null) {
			return null;
		}
		Map<EncodeHintType, Object> hints = null;
		String encoding = guessAppropriateEncoding(contentsToEncode);
		if (encoding != null) {
			hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, encoding);
		}
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix result;
		try {
			result = writer.encode(contentsToEncode, format, img_width,
					img_height, hints);
		} catch (IllegalArgumentException iae) {
			// Unsupported format
			return null;
		}
		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	private static String guessAppropriateEncoding(CharSequence contents) {
		// Very crude at the moment
		for (int i = 0; i < contents.length(); i++) {
			if (contents.charAt(i) > 0xFF) {
				return "UTF-8";
			}
		}
		return null;
	}
	
	private void selectImageForFront() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(MemberDetailActivity.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
					startActivityForResult(intent, REQUEST_CAMERA_FOR_FRONT);
					
//					  //define the file-name to save photo taken by Camera activity
//	                String fileName = "temp.jpg";
//	                //create parameters for Intent with filename
//	                ContentValues values = new ContentValues();
//	                values.put(MediaStore.Images.Media.TITLE, fileName);
//	                values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
//	                //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
//	                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//	                //create new Intent
//	                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//	                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//	                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//	                startActivityForResult(intent, REQUEST_CAMERA);
	               
				} else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE_FOR_FRONT);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}
	
	private void selectImageForBack() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(MemberDetailActivity.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
					startActivityForResult(intent, REQUEST_CAMERA_FOR_BACK);
					
//					  //define the file-name to save photo taken by Camera activity
//	                String fileName = "temp.jpg";
//	                //create parameters for Intent with filename
//	                ContentValues values = new ContentValues();
//	                values.put(MediaStore.Images.Media.TITLE, fileName);
//	                values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
//	                //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
//	                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//	                //create new Intent
//	                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//	                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//	                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//	                startActivityForResult(intent, REQUEST_CAMERA);
	               
				} else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE_FOR_BACK);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CAMERA_FOR_FRONT) {
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					Bitmap bm;
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

					bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
							btmapOptions);

					// bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
					frontImageView.setImageBitmap(bm);

					String path = android.os.Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "Phoenix" + File.separator + "default";
					f.delete();
					OutputStream fOut = null;
					File file = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");
					try {
						fOut = new FileOutputStream(file);
						bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
						fOut.flush();
						fOut.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == SELECT_FILE_FOR_FRONT) {
				Uri selectedImageUri = data.getData();

				String tempPath = getPath(selectedImageUri, MemberDetailActivity.this);
				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				frontImageView.setImageBitmap(bm);
			}
			else if(requestCode == REQUEST_CAMERA_FOR_BACK) {
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					Bitmap bm;
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
					btmapOptions.inSampleSize = 8;
					bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
							btmapOptions);

					// bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
					
		            
					backImageView.setImageBitmap(bm);

					String path = android.os.Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "Phoenix" + File.separator + "default";
					f.delete();
					OutputStream fOut = null;
					File file = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");
					try {
						fOut = new FileOutputStream(file);
						bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
						fOut.flush();
						fOut.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (requestCode == SELECT_FILE_FOR_BACK) {
				Uri selectedImageUri = data.getData();

				String tempPath = getPath(selectedImageUri, MemberDetailActivity.this);
				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				backImageView.setImageBitmap(bm);
			}
			
			
		}
	}
	@SuppressWarnings("deprecation")
	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	 /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
 
        @Override
        protected void onProgressUpdate(Integer... progress) {
        	
        }
 
        @Override
        protected String doInBackground(Void... params) {
        	String result;
        	String cardID, cardTitle, cardBarcode, cardDes;

			cardID = cardItem.getCardID();
			cardTitle = editTitle.getText().toString();
			cardDes = editDes.getText().toString();
			cardBarcode = editBarcode.getText().toString();
			
			BitmapDrawable drawable = (BitmapDrawable) frontImageView.getDrawable();	
			Bitmap frontImage = drawable.getBitmap();
			
			BitmapDrawable drawable2 = (BitmapDrawable) backImageView.getDrawable();
			Bitmap backImage = drawable2.getBitmap();
			
			CardsRequest request = new CardsRequest(
					MemberDetailActivity.this);
			
			result = request.updateCardInfo(frontImage, backImage, cardID, cardTitle, cardDes, cardBarcode);
			ToMoreParse toMoreParse = new ToMoreParse();
			result = toMoreParse.CommonPares(result).getResult();
			
            return result;
        }
 
        @Override
        protected void onPostExecute(String result) {
        	String message=null;
        	if(result.equals("succ"))
        	{
        		message = "更新成功";
        	}
        	else if(result.equals("upload image fail"))
        	{
        		message = "上传图片失败";
        	}
        	else
        	{
        		message = result;
        	}
            showAlert(message);
            super.onPostExecute(result);
        }
 
    }
    
    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
   
}
