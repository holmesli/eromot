package com.app.tomore.beans;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.app.tomore.R;
import com.app.tomore.beans.ArticleModel;
import com.app.tomore.beans.ImageLoaderFactory;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<ArticleModel> arrayList;
	private ImageLoader IL;
    private DisplayImageOptions otp;
    private View footerView;

	public ArticleAdapter(Context context,ArrayList<ArticleModel> arrayList) {
        this.mContext = context;
        this.arrayList = arrayList;
        IL = ImageLoaderFactory.getInstance().createImageLoader(mContext);
        otp = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().showImageForEmptyUri(R.drawable.tomorelogo).build();
        //footerView =  getFooterView(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size()+1;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(position == arrayList.size()){
			return footerView;
		}else if(convertView!=null && !"footer".equals(convertView.getTag())){
			viewHolder = (ViewHolder) convertView.getTag();
		}else{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.mag_listview, null);
			viewHolder.title = (TextView) convertView.findViewById(R.id.info);
			//viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
			//viewHolder.icon_news_live_flag = (ImageView) convertView.findViewById(R.id.icon_news_live_flag);
			//viewHolder.icon_news_comment_flag = (ImageView) convertView.findViewById(R.id.icon_news_comment_flag);
			//viewHolder.commentCount = (TextView) convertView.findViewById(R.id.commentCount);
			convertView.setTag(viewHolder);
		}
			
		ArticleModel article = arrayList.get(position);
//			String type = news.getType();
//			if(type!=null && "2".equals(type)){
//				viewHolder.icon_news_live_flag.setVisibility(View.VISIBLE);
//				viewHolder.icon_news_comment_flag.setVisibility(View.GONE);
				//IL.displayImage(article.getImagePosition(), viewHolder.img, otp);
			//}else{
//				viewHolder.icon_news_live_flag.setVisibility(View.GONE);
//				viewHolder.icon_news_comment_flag.setVisibility(View.VISIBLE);
				IL.displayImage(article.getArticleSmallImage(), viewHolder.img, otp);
			//}
			viewHolder.title.setText(article.getArticleTitle());
//			viewHolder.desc.setText(news.getDescription());
//			viewHolder.commentCount.setText(news.getCommentNum());
			return convertView;
	}
	
//	public static View getFooterView(Context context) {// list_padding_top_height
//        View footerView = LayoutInflater.from(context).inflate(R.layout.news_adapter_footer, null);
//        footerView.setTag("footer");
//        return footerView;
//    }
	
	class ViewHolder{
		TextView title;
		TextView desc;
		TextView commentCount;
		ImageView img;
		ImageView icon_news_live_flag;
		ImageView icon_news_comment_flag;
	}

}
