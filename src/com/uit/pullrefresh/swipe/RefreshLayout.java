/**
 *
 *	created by Mr.Simple, Oct 9, 20142:54:51 PM.
 *	Copyright (c) 2014, hehonghui@umeng.com All Rights Reserved.
 *
 *                #####################################################
 *                #                                                   #
 *                #                       _oo0oo_                     #   
 *                #                      o8888888o                    #
 *                #                      88" . "88                    #
 *                #                      (| -_- |)                    #
 *                #                      0\  =  /0                    #   
 *                #                    ___/`---'\___                  #
 *                #                  .' \\|     |# '.                 #
 *                #                 / \\|||  :  |||# \                #
 *                #                / _||||| -:- |||||- \              #
 *                #               |   | \\\  -  #/ |   |              #
 *                #               | \_|  ''\---/''  |_/ |             #
 *                #               \  .-\__  '-'  ___/-. /             #
 *                #             ___'. .'  /--.--\  `. .'___           #
 *                #          ."" '<  `.___\_<|>_/___.' >' "".         #
 *                #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 *                #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 *                #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 *                #                       `=---='                     #
 *                #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 *                #                                                   #
 *                #               娴ｆ稓顨叉穱婵呯秹         濮樺憡妫UG              #
 *                #                                                   #
 *                #####################################################
 */

package com.uit.pullrefresh.swipe;

import com.app.tomore.R;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;



/**
 * 缂佈勫閼风寗wipeRefreshLayout,娴犲氦锟界�鐐靛箛濠婃垵濮╅崚鏉跨俺闁劍妞傛稉濠冨閸旂姾娴囬弴鏉戭檵閻ㄥ嫬濮涢懗锟�濞夈劍鍓�:
 * 閸︺劋绗呴幏澶婂煕閺傛澘鐣幋鎰闂囷拷顪呯拫鍐暏RefreshLayout閻ㄥ墕etRefreshing(false)閺傝纭堕弶銉ヤ粻濮濄垹鍩涢弬鎷岀箖缁嬪绱� * 閸︺劋绗傞幏澶婂鏉炶姤娲挎径姘暚閹存劖妞傞棁锟筋渽鐠嬪啰鏁etLoading(false)閺夈儲鐖ｇ拠鍡楀鏉炶棄鐣幋鎰╋拷
 * 
 * @author mrsimple
 */
public abstract class RefreshLayout<T extends AbsListView> extends SwipeRefreshLayout implements
        OnScrollListener {

    /**
     * 濠婃垵濮╅崚鐗堟付娑撳娼伴弮鍓佹畱娑撳﹥濯洪幙宥勭稊
     */

    private int mTouchSlop;
    /**
     * listview鐎圭偘绶�     */
    // private ListView mListView;

    /**
     * 
     */
    protected T mAbsListView;

    /**
     * ListView濠婃艾濮╅惄鎴濇儔閸ｏ拷閻劋绨径鏍劥
     */
    private OnScrollListener mListViewOnScrollListener;

    /**
     * 娑撳﹥濯洪惄鎴濇儔閸ｏ拷 閸掗绨￠張锟界俺闁劎娈戞稉濠冨閸旂姾娴囬幙宥勭稊
     */
    private OnLoadListener mOnLoadListener;

    /**
     * ListView閻ㄥ嫬濮炴潪鎴掕厬footer
     */
    protected View mListViewFooter;

    /**
     * 閹稿绗呴弮鍓佹畱y閸ф劖鐖�     */
    protected int mYDown;
    /**
     * 閹额剝鎹ｉ弮鍓佹畱y閸ф劖鐖� 娑撳窇YDown娑擄拷鎹ｉ悽銊ょ艾濠婃垵濮╅崚鏉跨俺闁劍妞傞崚銈嗘焽閺勵垯绗傞幏澶庣箷閺勵垯绗呴幏锟�    */
    protected int mLastY;
    /**
     * 閺勵垰鎯侀崷銊ュ鏉炴垝鑵�( 娑撳﹥濯洪崝鐘烘祰閺囨潙顧�)
     */
    protected boolean isLoading = false;

    /**
     * @param context
     */
    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_footer,
                null,
                false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 閸掓繂顬婇崠鏈檌stView鐎电钖�        // if (mListView == null) {
        // getRefreshView();
        // }

        if (mAbsListView == null) {
            getRefreshView();

            // 鐠佸墽鐤嗘０婊嗗
            this.setColorScheme(R.color.umeng_comm_lv_header_color1,
                    R.color.umeng_comm_lv_header_color2, R.color.umeng_comm_lv_header_color3,
                    R.color.umeng_comm_lv_header_color4);
        }
    }

    /**
     * 閼惧嘲褰嘗istView鐎电钖�     */
    @SuppressWarnings("unchecked")
    private void getRefreshView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            // if (childView instanceof ListView) {
            // mListView = (ListView) childView;
            // // 鐠佸墽鐤嗗姘З閻╂垵鎯夐崳銊х舶ListView, 娴ｅ灝绶卞姘З閻ㄥ嫭鍎忛崘鍏哥瑓娑旂喎褰叉禒銉ㄥ殰閸斻劌濮炴潪锟�           // mListView.setOnScrollListener(this);
            // Log.d(VIEW_LOG_TAG, "### 閹垫儳鍩宭istview");
            // }

            if (childView instanceof AbsListView) {
                mAbsListView = (T) childView;
                // 鐠佸墽鐤嗗姘З閻╂垵鎯夐崳銊х舶ListView, 娴ｅ灝绶卞姘З閻ㄥ嫭鍎忛崘鍏哥瑓娑旂喎褰叉禒銉ㄥ殰閸斻劌濮炴潪锟�               
                mAbsListView.setOnScrollListener(this);
                Log.d(VIEW_LOG_TAG, "### 閹垫儳鍩宭istview = " + mAbsListView);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 閹稿绗�                
            	mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 缁夎濮�                
            	mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 閹额剝鎹�              
            	if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 閺勵垰鎯侀崣顖欎簰閸旂姾娴囬弴鏉戭檵, 閺夆�娆㈤弰顖氬煂娴滃棙娓舵惔鏇㈠劥, listview娑撳秴婀崝鐘烘祰娑擄拷 娑撴柧璐熸稉濠冨閹垮秳缍�
     * 
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 閸掋倖鏌囬弰顖氭儊閸掗绨￠張锟界俺闁拷     */
    private boolean isBottom() {

        // if (mListView != null && mListView.getAdapter() != null) {
        // return mListView.getLastVisiblePosition() ==
        // (mListView.getAdapter().getCount() - 1);
        // }
        if (mAbsListView != null && mAbsListView.getAdapter() != null) {
            return mAbsListView.getLastVisiblePosition() == (mAbsListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * 閺勵垰鎯侀弰顖欑瑐閹峰鎼锋担锟�    * 
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 婵″倹鐏夐崚棰佺啊閺堬拷绨抽柈锟介懓灞肩瑬閺勵垯绗傞幏澶嬫惙娴ｏ拷闁絼绠為幍褑顢憃nLoad閺傝纭�     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 璁剧疆鐘舵�
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }

    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        // if (isLoading) {
        // mListView.addFooterView(mListViewFooter);
        // } else {
        // if (mListView.getAdapter() instanceof HeaderViewListAdapter) {
        // mListView.removeFooterView(mListViewFooter);
        // } else {
        // mListViewFooter.setVisibility(View.GONE);
        // }
        // mYDown = 0;
        // mLastY = 0;
        // }

    }

    /**
     * 娴ｅ灝顧囬柈銊ュ讲娴犮儳娲冮崥顒�煂listview閻ㄥ嫭绮撮崝锟�    * 
     * @param listener
     */
    public void addOnScrollListener(OnScrollListener listener) {
        mListViewOnScrollListener = listener;
    }

    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.d(VIEW_LOG_TAG, "@@@@ state = " + scrollState);

        // 閸ョ偠鐨熺紒娆忣檱闁劎娈戦惄鎴濇儔閸ｏ拷       
        if (mListViewOnScrollListener != null) {
            mListViewOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {

        // 閸ョ偠鐨熺紒娆忣檱闁劎娈戦惄鎴濇儔閸ｏ拷        
    	if (mListViewOnScrollListener != null) {
            mListViewOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }

        // 濠婃艾濮╅弮璺哄煂娴滃棙娓舵惔鏇㈠劥娑旂喎褰叉禒銉ュ鏉炶姤娲挎径锟�      
    if (canLoad()) {
            loadData();
        }
    }

    /**
     * 閸旂姾娴囬弴鏉戭檵閻ㄥ嫮娲冮崥顒�珤
     * 
     * @author mrsimple
     */
    public static interface OnLoadListener {
        public void onLoad();
    }
}
