/**
 *
 *	created by Mr.Simple, Oct 2, 201410:21:12 AM.
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

package com.uit.pullrefresh.scroller.impl;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

public class RefreshSlideDeleteListView extends RefreshListView {

    /**
     * 
     */
    private int mXDown;
    /**
     * 
     */
    private int mYDown;
    /**
     * 
     */
    private View mItemView;
    /**
     * 閸掔娀娅庨崠鍝勭厵閻ㄥ嫬顔旀惔锟�鏉╂瑩鍣烽崑鍥啎鐎硅棄瀹虫稉锟�0px閸氾拷     */
    private int mDeleteViewWidth = 200;

    /**
     * 閺勵垰鎯侀崷銊︾拨閸斻劋鑵�     */
    private boolean isSliding = false;

    /**
     * 閺勵垰鎯佸鑼病閺勫墽銇�     */
    private boolean isDeleteViewShowing = false;
    /**
     * 
     */
    int mItemPosition = 0;

    /**
     * @param context
     */
    public RefreshSlideDeleteListView(Context context) {
        this(context, null, 0);
    }

    public RefreshSlideDeleteListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshSlideDeleteListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*
     * 閸︺劑锟借ぐ鎾舵畱閺冭泛锟介幏锔藉焻鐟欙附鎳滄禍瀣╂閿涘矁绻栭柌灞惧瘹閻ㄥ嫰锟借ぐ鎾舵畱閺冭泛锟介弰顖氱秼mContentView濠婃垵濮╅崚浼淬�闁煉绱濋獮鏈电瑬閺勵垯绗呴幏澶嬫閹凤附鍩呯憴锔芥嚋娴滃娆㈤敍灞芥儊閸掓瑤绗夐幏锔藉焻閿涘奔姘︾紒娆忓従child
     * view 閺夈儱顦甸悶鍡愶拷
     * @see
     * android.view.ViewGroup#onInterceptTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        /*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onTouchEvent will be called and we do the actual
         * scrolling there.
         */
        final int action = MotionEventCompat.getActionMasked(ev);
        // Always handle the case of the touch gesture being complete.
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Do not intercept touch event, let the child handle it
            return false;
        }

        Log.d(VIEW_LOG_TAG, "### is delete showing = " + isDeleteViewShowing);

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getRawY();
                mXDown = (int) ev.getRawX();
                mYDown = mLastY;
                // 閸掋倖鏌囬弰顖氭儊閺堝鍨归梽銈嗗瘻闁筋喗妯夌粈锟芥俊鍌涚亯閺堝鍨梾鎰閸掔娀娅庨幐澶愭尦
                if (isDeleteViewShowing && mItemPosition != AbsListView.INVALID_POSITION
                        && mItemPosition != getItemPosition(mXDown, mYDown)) {
                    slideItemView(0);
                    clearSlideState();
                    return false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                // int yDistance = (int) ev.getRawY() - mYDown;
                mYOffset = (int) ev.getRawY() - mLastY;
                mDistanceX = (int) ev.getRawX() - mXDown;
                // 婵″倹鐏夐幏澶婂煂娴滃棝銆婇柈锟�楠炴湹绗栭弰顖欑瑓閹凤拷閸掓瑦瀚ら幋顏囆曢幗闀愮皑娴狅拷娴犲氦锟芥潪顒�煂onTouchEvent閺夈儱顦甸悶鍡曠瑓閹峰鍩涢弬棰佺皑娴狅拷               
                if (isTop() && mYOffset > 0) {
                    return true;
                }
                // 濠婃垵濮╅崚鐘绘珟閹垮秳缍�               
                if (Math.abs(mDistanceX) > 10) {
                    return true;
                }
                break;

        }

        // Do not intercept touch event, let the child handle it
        return false;
    }

    /*
     * 閸︺劏绻栭柌灞筋樀閻炲棜袝閹介晲绨ㄦ禒鏈典簰鏉堟儳鍩屾稉瀣閸掗攱鏌婇幋鏍拷娑撳﹥濯洪懛顏勫З閸旂姾娴囬惃鍕６妫帮拷     * @see android.view.View#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                int currentY = (int) event.getRawY();
                int currentX = (int) event.getRawX();
                mDistanceX = currentX - mXDown;
                int distanceY = (int) event.getRawY() - mYDown;
                Log.d(VIEW_LOG_TAG, "### distanceX = " + mDistanceX + ", dis y = " + distanceY
                        + ", is delete showing = " + isDeleteViewShowing);
                if (Math.abs(mDistanceX) > Math.abs(distanceY) * 2) {
                    if (mItemView == null) {
                        mItemView = getCurrentItemView(currentX, currentY);
                    }
                    slideItemView(mDistanceX);
                } else if (!isDeleteViewShowing && isTop() && mYOffset > 0 && !isSliding) {
                    //
                    mYOffset = currentY - mLastY;
                    if (mCurrentStatus != STATUS_LOADING) {
                        // 娣囶喗鏁糞crollY鏉堟儳鍩岄幏澶夊嚑header閻ㄥ嫭鏅ラ弸锟�                       changeScrollY(mYOffset);
                    }

                    rotateHeaderArrow();
                    changeTips();
                    mLastY = currentY;
                }
                break;

            case MotionEvent.ACTION_UP:
                isSlideValid();
                // 娑撳濯洪崚閿嬫煀閻ㄥ嫬鍙挎担鎾存惙娴ｏ拷                doRefresh();
                break;
            default:
                break;

        }

        return true;
    }

    int mDistanceX = 0;

    /**
     * 
     */
    private void slideItemView(int distanceX) {
        if (mCurrentStatus == STATUS_PULL_TO_REFRESH
                || mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            return;
        }

        // 閸︺劍鐥呴張濉猠lete view閺勫墽銇氶敍灞肩瑬閺勵垳鏁ら幋宄板礁濠婃垵鍨箛鐣屾殣
        if (distanceX > 0 && !isDeleteViewShowing) {
            return;
        }

        Log.d(VIEW_LOG_TAG, "### slide item view , origin distance x = " + distanceX);
        if (distanceX <= 0) {
            // 鐏忓繋绨�閿涘奔鍞悰銊ф暏閹村嘲涔忓鎴礉鏉╂瑩鍣风亸鍡楀従鏉烆兛璐熷锝嗘殶閿涘奔濞囧妤佹殻娑撶導tem view閻ㄥ嫯顬呴崶鎯ф倻閸欒櫕绮﹂崝顭掔礉閸楀啿婀獂鏉炵繝绗傜憰浣告倻閸欏磭些閸斻劊锟�            distanceX = Math.abs(distanceX);
            distanceX = Math.min(distanceX, mDeleteViewWidth);
        } else {
            distanceX = Math.max(0, mDeleteViewWidth - distanceX);
        }

        Log.d(VIEW_LOG_TAG, "### slide item view , distance x = " + distanceX);
        Log.d(VIEW_LOG_TAG, "item view = " + mItemView);
        if (mItemView != null) {
            mItemView.scrollTo(distanceX, 0);
            mItemView.invalidate();
            isSliding = true;
        }

    }

    /**
     * 
     */
    private void clearSlideState() {
        mItemView = null;
        isDeleteViewShowing = false;
        isSliding = false;
        mDistanceX = 0;
        mItemPosition = AbsListView.INVALID_POSITION;
    }

    /**
     * 
     */
    private void isSlideValid() {
        if (mItemView != null) {
            if (mItemView.getScrollX() > mDeleteViewWidth / 2) {
                slideItemView(-mDeleteViewWidth);
                isDeleteViewShowing = true;
            } else {
                slideItemView(0);
                clearSlideState();
            }
        } else {
            clearSlideState();
        }

    }

    /**
     * @param position
     */
    public void onRemoveItem(int position) {
        slideItemView(0);
        clearSlideState();
    }

    /**
     * @param x
     * @param y
     * @return
     */
    private int getItemPosition(int x, int y) {
        int position = mContentView.pointToPosition(x, y);
        if (position != AbsListView.INVALID_POSITION) {

            Log.d(VIEW_LOG_TAG,
                    "### first = " + mContentView.getFirstVisiblePosition() + ", touch postion = "
                            + position + ", header count = " + mContentView.getHeaderViewsCount());

            mItemPosition = position - mContentView.getFirstVisiblePosition() - 1;
            mItemPosition = Math.max(0, mItemPosition);
        }

        return mItemPosition;
    }

    /**
     * @param event
     * @return
     */
    private View getCurrentItemView(int x, int y) {
        //
        getItemPosition(x, y);

        Log.d(VIEW_LOG_TAG, "### child count = " + mContentView.getChildCount());
        //
        if (mItemPosition != AbsListView.INVALID_POSITION) {
            // get child view
            return mContentView.getChildAt(mItemPosition);
        }

        return null;
    }

}
