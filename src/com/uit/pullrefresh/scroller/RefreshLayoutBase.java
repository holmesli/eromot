/**
 *
 *	created by Mr.Simple, Sep 30, 20142:48:17 PM.
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

package com.uit.pullrefresh.scroller;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.app.tomore.R;
import com.uit.pullrefresh.listener.OnLoadListener;
import com.uit.pullrefresh.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mrsimple
 */
public abstract class RefreshLayoutBase<T extends View> extends ViewGroup implements
        OnScrollListener {

    /**
     * 
     */
    protected Scroller mScroller;

    /**
     * 娑撳濯洪崚閿嬫煀閺冭埖妯夌粈铏规畱header view
     */
    protected View mHeaderView;

    /**
     * 娑撳﹥濯洪崝鐘烘祰閺囨潙顧嬮弮鑸垫▔缁�櫣娈慺ooter view
     */
    protected View mFooterView;

    /**
     * 閺堫剚顐肩憴锔芥嚋濠婃垵濮﹜閸ф劖鐖ｆ稉濠勬畱閸嬪繒些闁诧拷     */
    protected int mYOffset;

    /**
     * 閸愬懎顔愮憴鍡楁禈, 閸楀磭鏁ら幋鐤曢幗绋款嚤閼风繝绗呴幏澶婂煕閺傝埇锟芥稉濠冨閸旂姾娴囬惃鍕瘜鐟欏棗娴� 濮ｆ柨顪哃istView, GridView缁涳拷
     */
    protected T mContentView;

    /**
     * 閺堬拷鍨甸惃鍕泊閸斻劋缍呯純锟界粭顑跨濞嗏�绔风仦锟芥濠婃艾濮﹉eader閻ㄥ嫰鐝惔锔炬畱鐠烘繄顪�     */
    protected int mInitScrollY = 0;
    /**
     * 閺堬拷鎮楁稉锟筋偧鐟欙附鎳滄禍瀣╂閻ㄥ墢鏉炴潙娼楅弽锟�    */
    protected int mLastY = 0;

    /**
     * 缁屾椽妫介悩鑸碉拷
     */
    public static final int STATUS_IDLE = 0;

    /**
     * 娑撳濯洪幋鏍拷娑撳﹥濯洪悩鑸碉拷, 鏉╂ɑ鐥呴張澶婂煂鏉堟儳褰查崚閿嬫煀閻ㄥ嫮濮搁幀锟�    */
    public static final int STATUS_PULL_TO_REFRESH = 1;

    /**
     * 娑撳濯洪幋鏍拷娑撳﹥濯洪悩鑸碉拷
     */
    public static final int STATUS_RELEASE_TO_REFRESH = 2;
    /**
     * 閸掗攱鏌婃稉锟�    */
    public static final int STATUS_REFRESHING = 3;

    /**
     * LOADING娑擄拷     */
    public static final int STATUS_LOADING = 4;

    /**
     * 瑜版挸澧犻悩鑸碉拷
     */
    protected int mCurrentStatus = STATUS_IDLE;

    /**
     * 娑撳濯洪崚閿嬫煀閻╂垵鎯夐崳锟�    */
    protected OnRefreshListener mOnRefreshListener;

    /**
     * header娑擃厾娈戠粻顓炪仈閸ョ偓鐖�     */
    private ImageView mArrowImageView;
    /**
     * 缁狀厼銇旈弰顖氭儊閸氭垳绗�     */
    private boolean isArrowUp;
    /**
     * header 娑擃厾娈戦弬鍥ㄦ拱閺嶅洨顒�     */
    private TextView mTipsTextView;
    /**
     * header娑擃厾娈戦弮鍫曟？閺嶅洨顒�     */
    private TextView mTimeTextView;
    /**
     * header娑擃厾娈戞潻娑樺閺夛拷     */
    private ProgressBar mProgressBar;
    /**
     * 
     */
    private int mScreenHeight;
    /**
     * 
     */
    private int mHeaderHeight;
    /**
     * 
     */
    protected OnLoadListener mLoadListener;

    /**
     * @param context
     */
    public RefreshLayoutBase(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public RefreshLayoutBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public RefreshLayoutBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);

        // 閸掓繂顬婇崠鏈roller鐎电钖�        mScroller = new Scroller(context);

        // 閼惧嘲褰囩仦蹇撶妤傛ê瀹�        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        // header 閻ㄥ嫰鐝惔锔胯礋鐏炲繐绠锋妯哄閻拷1/4
        mHeaderHeight = mScreenHeight / 4;

        // 閸掓繂顬婇崠鏍ㄦ殻娑擃亜绔风仦锟�       initLayout(context);
    }

    /**
     * 閸掓繂顬婇崠鏍ㄦ殻娑擃亜绔风仦锟�    * 
     * @param context
     */
    private final void initLayout(Context context) {

        // header view
        setupHeaderView(context);

        // 鐠佸墽鐤嗛崘鍛啇鐟欏棗娴�        setupContentView(context);
        // 鐠佸墽鐤嗙敮鍐ㄧ湰閸欏倹鏆�        setDefaultContentLayoutParams();
        //
        addView(mContentView);

        // footer view
        setupFooterView(context);

    }

    /**
     * 閸掓繂顬婇崠锟絟eader view
     */
    protected void setupHeaderView(Context context) {
        mHeaderView = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, this,
                false);
        mHeaderView
                .setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                        mHeaderHeight));
        mHeaderView.setBackgroundColor(Color.RED);
        mHeaderView.setPadding(0, mHeaderHeight - 100, 0, 0);
        addView(mHeaderView);

        // HEADER VIEWS
        mArrowImageView = (ImageView) mHeaderView.findViewById(R.id.pull_to_arrow_image);
        mTipsTextView = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_text);
        mTimeTextView = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_updated_at);
        mProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.pull_to_refresh_progress);
    }

    /**
     * 閸掓繂顬婇崠鏈噊ntent View, 鐎涙劗琚憰鍡楀晸.
     */
    protected abstract void setupContentView(Context context);

    /**
     * 閸掓繂顬婇崠鏉卭oter view
     */
    protected void setupFooterView(Context context) {
        /**
         * 
         */
        mFooterView = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_footer,
                this, false);
        addView(mFooterView);
    }

    /**
     * 鐠佸墽鐤咰ontent View閻ㄥ嫰绮拋銈呯鐏烇拷寮弫锟�    */
    protected void setDefaultContentLayoutParams() {
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    /**
     * 娑撳洞croller閸氬牅缍�鐎圭偟骞囬獮铏拨濠婃艾濮╅妴鍌氭躬鐠囥儲鏌熷▔鏇氳厬鐠嬪啰鏁croller閻ㄥ垻omputeScrollOffset閺夈儱鍨介弬顓熺泊閸斻劍妲搁崥锔剧波閺夌喆锟芥俊鍌涚亯濞屸剝婀佺紒鎾存将閿涳拷     * 闁絼绠炲姘З閸掓壆娴夋惔鏃傛畱娴ｅ秶鐤嗛敍灞借嫙娑撴棁鐨熼悽鈺琽stInvalidate閺傝纭堕柌宥囩帛閻ｅ矂娼伴敍灞肩矤閼板苯鍟�▎陇绻橀崗銉ュ煂鏉╂瑤閲渃omputeScroll濞翠胶鈻奸敍宀�纯閸掔増绮撮崝銊х波閺夌喆锟�     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
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

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // int yDistance = (int) ev.getRawY() - mYDown;
                mYOffset = (int) ev.getRawY() - mLastY;
                // 婵″倹鐏夐幏澶婂煂娴滃棝銆婇柈锟�楠炴湹绗栭弰顖欑瑓閹凤拷閸掓瑦瀚ら幋顏囆曢幗闀愮皑娴狅拷娴犲氦锟芥潪顒�煂onTouchEvent閺夈儱顦甸悶鍡曠瑓閹峰鍩涢弬棰佺皑娴狅拷              
                if (isTop() && mYOffset > 0) {
                    return true;
                }
                break;

        }

        // Do not intercept touch event, let the child handle it
        return false;
    }
    /**
     * 鏄惁宸茬粡鍒颁簡鏈�《閮�瀛愮被闇�鍐欒鏂规硶,浣垮緱mContentView婊戝姩鍒版渶椤剁鏃惰繑鍥瀟rue, 濡傛灉鍒拌揪鏈�《绔敤鎴风户缁笅鎷夊垯鎷︽埅浜嬩欢;
     * 
     * @return
     */
	protected abstract boolean isTop();

    /**
     * 閺勵垰鎯佸鑼病閸掗绨￠張锟界俺闁拷鐎涙劗琚棁锟筋湂閸愭瑨顕氶弬瑙勭《,娴ｅ灝绶眒ContentView濠婃垵濮╅崚鐗堟付鎼存洜顏弮鎯扮箲閸ョ�rue;娴犲氦锟界憴锕�絺閼奉亜濮╅崝鐘烘祰閺囨潙顧嬮惃鍕惙娴ｏ拷     * 
     * @return
     */
    protected abstract boolean isBottom();

    /**
     * 閺勫墽銇歠ooter view
     */
    private void showFooterView() {
        startScroll(mFooterView.getMeasuredHeight());
        mCurrentStatus = STATUS_LOADING;
    }

    /**
     * 鐠佸墽鐤嗗姘З閻ㄥ嫬寮弫锟�    * 
     * @param yOffset
     */
    private void startScroll(int yOffset) {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, yOffset);
        invalidate();
    }

    /*
     * 閸︺劏绻栭柌灞筋樀閻炲棜袝閹介晲绨ㄦ禒鏈典簰鏉堟儳鍩屾稉瀣閸掗攱鏌婇幋鏍拷娑撳﹥濯洪懛顏勫З閸旂姾娴囬惃鍕６妫帮拷     * @see android.view.View#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d(VIEW_LOG_TAG, "@@@ onTouchEvent : action = " + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                int currentY = (int) event.getRawY();
                mYOffset = currentY - mLastY;
                if (mCurrentStatus != STATUS_LOADING) {
                    //
                    changeScrollY(mYOffset);
                }

                rotateHeaderArrow();
                changeTips();
                mLastY = currentY;
                break;

            case MotionEvent.ACTION_UP:
                // 娑撳濯洪崚閿嬫煀閻ㄥ嫬鍙挎担鎾存惙娴ｏ拷                doRefresh();
                break;
            default:
                break;

        }

        return true;
    }

    /**
     * @param distance
     * @return
     */
    protected void changeScrollY(int distance) {
        // 閺堬拷銇囬崐闂磋礋 scrollY(header 闂呮劘妫�, 閺堬拷鐨崐闂磋礋0 ( header 鐎瑰苯鍙忛弰鍓с仛).
        int curY = getScrollY();
        // 娑撳濯�        
        if (distance > 0 && curY - distance > getPaddingTop()) {
            scrollBy(0, -distance);
        } else if (distance < 0 && curY - distance <= mInitScrollY) {
            // 娑撳﹥濯烘潻鍥┾柤
            scrollBy(0, -distance);
        }

        curY = getScrollY();
        int slop = mInitScrollY / 2;
        //
        if (curY > 0 && curY < slop) {
            mCurrentStatus = STATUS_RELEASE_TO_REFRESH;
        } else if (curY > 0 && curY > slop) {
            mCurrentStatus = STATUS_PULL_TO_REFRESH;
        }
    }

    /**
     * 閺冨娴嗙粻顓炪仈閸ョ偓鐖�     */
    protected void rotateHeaderArrow() {

        if (mCurrentStatus == STATUS_REFRESHING) {
            return;
        } else if (mCurrentStatus == STATUS_PULL_TO_REFRESH && !isArrowUp) {
            return;
        } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH && isArrowUp) {
            return;
        }

        mProgressBar.setVisibility(View.GONE);
        mArrowImageView.setVisibility(View.VISIBLE);
        float pivotX = mArrowImageView.getWidth() / 2f;
        float pivotY = mArrowImageView.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        if (mCurrentStatus == STATUS_PULL_TO_REFRESH) {
            fromDegrees = 180f;
            toDegrees = 360f;
        } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            fromDegrees = 0f;
            toDegrees = 180f;
        }

        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setDuration(100);
        animation.setFillAfter(true);
        mArrowImageView.startAnimation(animation);

        if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            isArrowUp = true;
        } else {
            isArrowUp = false;
        }
    }

    /**
     * 閺嶈宓佽ぐ鎾冲閻樿埖锟芥穱顔芥暭header view娑擃厾娈戦弬鍥ㄦ拱閺嶅洨顒�     */
    protected void changeTips() {
        if (mCurrentStatus == STATUS_PULL_TO_REFRESH) {
            mTipsTextView.setText(R.string.pull_to_refresh_pull_label);
        } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            mTipsTextView.setText(R.string.pull_to_refresh_release_label);
        }
    }

    /**
     * 鐠佸墽鐤嗘稉瀣閸掗攱鏌婇惄鎴濇儔閸ｏ拷     * 
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    /**
     * 鐠佸墽鐤嗗鎴濆З閸掓澘绨抽柈銊︽閼奉亜濮╅崝鐘烘祰閺囨潙顧嬮惃鍕磧閸氼剙娅�     * 
     * @param listener
     */
    public void setOnLoadListener(OnLoadListener listener) {
        mLoadListener = listener;
    }

    /**
     * 閸掗攱鏌婄紒鎾存将閿涘本浠径宥囧Ц閹拷     */
    public void refreshComplete() {
        mCurrentStatus = STATUS_IDLE;

        mScroller.startScroll(getScrollX(), getScrollY(), 0, mInitScrollY - getScrollY());
        invalidate();
        updateHeaderTimeStamp();

        // 200濮ｎ偆顬戦崥搴☆樀閻炲摳rrow閸滃rogressbar,閸忓秴绶辨径顏嗙崐閸忥拷       
        this.postDelayed(new Runnable() {

            @Override
            public void run() {
                mArrowImageView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        }, 100);

    }

    /**
     * 閸旂姾娴囩紒鎾存将閿涘本浠径宥囧Ц閹拷     */
    public void loadCompelte() {
        // 闂呮劘妫宖ooter
        startScroll(mInitScrollY - getScrollY());
        mCurrentStatus = STATUS_IDLE;
    }

    /**
     * 閹靛瀵氶幎顒冩崳閺冿拷閺嶈宓侀悽銊﹀煕娑撳濯洪惃鍕彯鎼达附娼甸崚銈嗘焽閺勵垰鎯侀弰顖涙箒閺佸牏娈戞稉瀣閸掗攱鏌婇幙宥勭稊閵嗗倸顪嗛弸婊�瑓閹峰娈戠捄婵堫湩鐡掑懓绻僪eader view閻拷     * 1/2闁絼绠為崚娆掝吇娑撶儤妲搁張澶嬫櫏閻ㄥ嫪绗呴幏澶婂煕閺傜増鎼锋担婊愮礉閸氾箑鍨幁銏狀樉閸樼喐娼甸惃鍕瀰閸ュ墽濮搁幀锟�     */
    private void changeHeaderViewStaus() {
        int curScrollY = getScrollY();
        // 鐡掑懓绻�/2閸掓瑨顓绘稉鐑樻Ц閺堝鏅ラ惃鍕瑓閹峰鍩涢弬锟�閸氾箑鍨潻妯哄斧
        if (curScrollY < mInitScrollY / 2) {
            mScroller.startScroll(getScrollX(), curScrollY, 0, mHeaderView.getPaddingTop()
                    - curScrollY);
            mCurrentStatus = STATUS_REFRESHING;
            mTipsTextView.setText(R.string.pull_to_refresh_refreshing_label);
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mScroller.startScroll(getScrollX(), curScrollY, 0, mInitScrollY - curScrollY);
            mCurrentStatus = STATUS_IDLE;
        }

        invalidate();
    }

    /**
     * 閹笛嗩攽娑撳濯洪崚閿嬫煀
     */
    protected void doRefresh() {
        changeHeaderViewStaus();
        // 閹笛嗩攽閸掗攱鏌婇幙宥勭稊
        if (mCurrentStatus == STATUS_REFRESHING && mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh();
        }
    }

    /**
     * 閹笛嗩攽娑撳濯�閼奉亜濮�閸旂姾娴囬弴鏉戭檵閻ㄥ嫭鎼锋担锟�    */
    protected void doLoadMore() {
        if (mLoadListener != null) {
            mLoadListener.onLoadMore();
        }
    }

    /**
     * 娣囶喗鏁糷eader娑撳﹦娈戦張锟界箮閺囧瓨鏌婇弮鍫曟？
     */
    private void updateHeaderTimeStamp() {
        // 鐠佸墽鐤嗛弴瀛樻煀閺冨爼妫�        mTimeTextView.setText(R.string.pull_to_refresh_update_time_label);
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        mTimeTextView.append(sdf.format(new Date()));
    }

    /**
     * 鏉╂柨娲朇ontent View
     * 
     * @return
     */
    public T getContentView() {
        return mContentView;
    }

    /**
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     * @return
     */
    public View getFooterView() {
        return mFooterView;
    }

    /*
     * 娑撳牓鍣虹憴鍡楁禈閻ㄥ嫬顔旈妴渚�彯閵嗗倸顔旀惔锔胯礋閻劍鍩涚拋鍓х枂閻ㄥ嫬顔旀惔锔肩礉妤傛ê瀹抽崚娆庤礋header, content view, footer鏉╂瑤绗佹稉顏勭摍閹貉傛閻ㄥ嫰鐝惔锕�涧閸滃被锟�     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);

        int childCount = getChildCount();

        int finalHeight = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // measure
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 鐠囶殦iew閹碉拷娓剁憰浣烘畱閹鐝惔锟�           finalHeight += child.getMeasuredHeight();
        }

        setMeasuredDimension(width, finalHeight);
    }

    /*
     * 鐢啫鐪崙鑺ユ殶閿涘苯鐨eader, content view,
     * footer鏉╂瑤绗佹稉鐛�ew娴犲簼绗傞崚棰佺瑓鐢啫鐪妴鍌氱鐏烇拷鐣幋鎰倵闁俺绻僑croller濠婃艾濮╅崚鐧秂ader閻ㄥ嫬绨抽柈顭掔礉閸楄櫕绮撮崝銊ㄧ獩缁傝璐焗eader閻ㄥ嫰鐝惔锟�
     * 閺堫剝顬呴崶鍓ф畱paddingTop閿涘奔绮犻懓宀冩彧閸掍即娈ｉ挊寤籩ader閻ㄥ嫭鏅ラ弸锟�     * @see android.view.ViewGroup#onLayout(boolean, int, int, int, int)
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        int top = getPaddingTop();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(0, top, child.getMeasuredWidth(), child.getMeasuredHeight() + top);
            top += child.getMeasuredHeight();
        }

        // 鐠侊紕鐣婚崚婵嗩瀶閸栨牗绮﹂崝銊ф畱y鏉炵绐涚粋锟�       mInitScrollY = mHeaderView.getMeasuredHeight() + getPaddingTop();
        // 濠婃垵濮╅崚鐧秂ader view妤傛ê瀹抽惃鍕秴缂冿拷 娴犲氦锟芥潏鎯у煂闂呮劘妫宧eader view閻ㄥ嫭鏅ラ弸锟�       scrollTo(0, mInitScrollY);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /*
     * 濠婃艾濮╅惄鎴濇儔閿涘苯缍嬪姘З閸掔増娓舵惔鏇㈠劥閿涘奔绗栭悽銊﹀煕鐠佸墽鐤嗘禍鍡楀鏉炶姤娲挎径姘辨畱閻╂垵鎯夐崳銊︽鐟欙箑褰傞崝鐘烘祰閺囨潙顧嬮幙宥勭稊.
     * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.
     * AbsListView, int, int, int)
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {
        // 閻劍鍩涚拋鍓х枂娴滃棗濮炴潪鑺ユ纯婢舵氨娲冮崥顒�珤閿涘奔绗栭崚棰佺啊閺堬拷绨抽柈顭掔礉楠炴湹绗栭弰顖欑瑐閹峰鎼锋担婊愮礉闁絼绠為幍褑顢戦崝鐘烘祰閺囨潙顧�
        if (mLoadListener != null && isBottom() && mScroller.getCurrY() <= mInitScrollY
                && mYOffset <= 0
                && mCurrentStatus == STATUS_IDLE) {
            showFooterView();
            doLoadMore();
        }
    }

}
