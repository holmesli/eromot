/**
 *
 *	created by Mr.Simple, Sep 10, 20146:16:09 PM.
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

package com.uit.pullrefresh.base;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.tomore.R;
import com.uit.pullrefresh.listener.OnLoadListener;
import com.uit.pullrefresh.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mrsimple
 * @param <T>
 */
public abstract class PullRefreshBase<T extends View> extends LinearLayout implements
        OnScrollListener {

    /**
     * 閸愬懎顔愮憴鍡楁禈, 濮ｆ柨顪哃istView, GridView缁涳拷     */
    protected T mContentView;

    /**
     * 娑撳濯烘径纾嬵瀰閸ワ拷閸ョ姳璐熺憰浣锋叏閺�懓鍙緋adding,閹碉拷浜掔猾璇茬�娑撶iewGroup缁鐎�     */
    protected ViewGroup mHeaderView;

    /**
     * footer鐟欏棗娴�閸ョ姳璐熺憰浣锋叏閺�懓鍙緋adding,閹碉拷浜掔猾璇茬�娑撶iewGroup缁鐎�     */
    protected ViewGroup mFooterView;
    /**
     * 娑撳濯洪崚閿嬫煀閻╂垵鎯夐崳锟�    */
    protected OnRefreshListener mPullRefreshListener;
    /**
     * 濠婃垵濮╅崚鏉跨俺闁劌鍨懛顏勫З閸旂姾娴囬惃鍕磧閸氼剙娅�     */
    protected OnLoadListener mLoadMoreListener;

    /**
     * LayoutInflater
     */
    protected LayoutInflater mInflater;

    /**
     * Header 閻ㄥ嫰鐝惔锟�    */
    protected int mHeaderViewHeight;

    /**
     * 缁屾椽妫介悩鑸碉拷
     */
    public static final int STATUS_IDLE = 0;

    /**
     * 娑撳濯洪幋鏍拷娑撳﹥濯洪悩鑸碉拷
     */
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
     * Y鏉炵繝绗傚鎴濆З閻ㄥ嫯绐涚粋锟�    */
    protected int mYDistance = 0;
    /**
     * 濠婃垵濮╅惃鍕獩缁傚姒鹃崐锟界搾鍛扮箖鏉╂瑤閲滈梼锟斤拷閸掓瑨顓绘稉鐑樻Ц閺堝鏅ュ鎴濆З
     */
    protected int mTouchSlop = 0;
    /**
     * 鐟欙附鎳滄禍瀣╂閹稿绗呴惃鍓忛崸鎰垼
     */
    protected int mYDown = 0;

    /**
     * header view闁插矂娼伴惃鍕箻鎼达附娼�     */
    protected ProgressBar mHeaderProgressBar;
    /**
     * 娑撳濯烘径瀵告畱缁狀厼銇�     */
    protected ImageView mArrowImageView;
    /**
     * 缁狀厼銇旈崶鐐垼閺勵垰鎯侀弰顖氭倻娑撳﹦娈戦悩鑸碉拷
     */
    protected boolean isArrowUp = false;
    /**
     * 娑撳濯洪崚閿嬫煀閻ㄥ嫭鏋冪�妗緀xtView
     */
    protected TextView mTipsTextView;
    /**
     * 閺囧瓨鏌婇弮鍫曟？閻ㄥ嫭鏋冪�妗緀xtView
     */
    protected TextView mTimeTextView;
    /**
     * footer view's progress bar
     */
    protected ProgressBar mFooterProgressBar;
    /**
     * footer view's text
     */
    protected TextView mFooterTextView;
    /**
     * footer view's height
     */
    protected int mFooterHeight;
    /**
     * 鐏炲繐绠锋妯哄
     */
    protected int mScrHeight = 0;

    /**
     * @param context
     */
    public PullRefreshBase(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public PullRefreshBase(Context context, AttributeSet attrs) {

        super(context, attrs);

        mInflater = LayoutInflater.from(context);
        setOrientation(LinearLayout.VERTICAL);
        initLayout(context);
    }

    /**
     * 閸掓繂顬婇崠鏍ㄦ殻娴ｆ挸绔风仦锟�header view閺�儳婀粭顑跨娑擃亷绱濋悞璺烘倵閺勭棗ontent view 閸滐拷footer view .閸忔湹鑵慶ontent view閻拷     * 鐎硅棄瀹抽崪宀勭彯鎼达箓鍏樻稉绨僡tch parent .
     */
    protected final void initLayout(Context context) {

        // 閸掓繂顬婇崠鏉奺ader view
        initHeaderView();

        // 閸掓繂顬婇崠锟絚ontent view
        initContentView();
        setContentView(mContentView);

        // 閸掓繂顬婇崠锟絝ooter
        initFooterView();

        //
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //
        mScrHeight = context.getResources().getDisplayMetrics().heightPixels;

    }

    /**
     * 閸掓繂顬婇崠鏉奺ader view
     */
    protected void initHeaderView() {
        //
        mHeaderView = (ViewGroup) mInflater.inflate(R.layout.pull_to_refresh_header,
                null);
        mHeaderView.setBackgroundColor(Color.RED);

        mHeaderProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.pull_to_refresh_progress);
        mArrowImageView = (ImageView) mHeaderView.findViewById(R.id.pull_to_arrow_image);
        mTipsTextView = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_text);
        mTimeTextView = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_updated_at);
        // add header view to parent
        this.addView(mHeaderView, 0);

    }

    /**
     * 閸掓繂顬婇崠鏉卭oter view
     */
    protected void initFooterView() {

        mFooterView = (ViewGroup) mInflater.inflate(R.layout.pull_to_refresh_footer, null);

        mFooterProgressBar = (ProgressBar) mFooterView.findViewById(R.id.pull_to_loading_progress);
        mFooterTextView = (TextView) mFooterView.findViewById(R.id.pull_to_loading_text);
        this.addView(mFooterView, 2);
    }

    /*
     * 閼惧嘲褰噃eader view, footer view閻ㄥ嫰鐝惔锟�    * @see android.widget.LinearLayout#onLayout(boolean, int, int, int, int)
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed && mHeaderViewHeight <= 0) {
            mHeaderViewHeight = mHeaderView.getHeight();
            // padding
            adjustHeaderPadding(-mHeaderViewHeight);

            mFooterHeight = mFooterView.getHeight();
            adjustFooterPadding(-mFooterHeight);
        }
    }

    /**
     * 鐎涙劗琚箛鍛淬�鐎圭偟骞囨潻娆庨嚋閺傝纭堕敍灞借嫙娑撴柨婀拠銉︽煙濞夋洑鑵戦崚婵嗩瀶閸栨潟ContentView鐎涙顔岄敍灞藉祮娴ｇ姾顪呴弰鍓с仛閻ㄥ嫪瀵岀憴鍡楁禈.
     * 娓氬顪哖ullRefreshListView閻ㄥ埓ContentView鐏忚鲸妲窵istView
     */
    protected abstract void initContentView();

    /**
     * @param view
     */
    public void setContentView(T view) {
        mContentView = view;
        LinearLayout.LayoutParams lvLayoutParams = (LinearLayout.LayoutParams) mContentView
                .getLayoutParams();
        if (lvLayoutParams == null) {
            lvLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        lvLayoutParams.bottomMargin = 0;
        lvLayoutParams.weight = 1.0f;
        mContentView.setLayoutParams(lvLayoutParams);
        this.addView(mContentView, 1);
    }

    /**
     * @return
     */
    public T getContentView() {
        return mContentView;
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
                mYDown = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // int yDistance = (int) ev.getRawY() - mYDown;
                mYDistance = (int) ev.getRawY() - mYDown;
                showStatus(mCurrentStatus);
                Log.d(VIEW_LOG_TAG, "%%% isBottom : " + isBottom() + ", isTop : " + isTop()
                        + ", mYDistance : " + mYDistance);
                // 婵″倹鐏夐幏澶婂煂娴滃棝銆婇柈锟�楠炴湹绗栭弰顖欑瑓閹凤拷閸掓瑦瀚ら幋顏囆曢幗闀愮皑娴狅拷娴犲氦锟芥潪顒�煂onTouchEvent閺夈儱顦甸悶鍡曠瑓閹峰鍩涢弬棰佺皑娴狅拷              
                if ((isTop() && mYDistance > 0)
                        || (mYDistance > 0 && mCurrentStatus == STATUS_REFRESHING)) {
                    Log.d(VIEW_LOG_TAG, "--------- mYDistance : " + mYDistance);
                    return true;
                }
                break;

        }

        // Do not intercept touch event, let the child handle it
        return false;
    }

    /**
     * @param status
     */
    private void showStatus(int status) {
        String statusString = "";
        if (status == STATUS_IDLE) {
            statusString = "idle";
        } else if (status == STATUS_PULL_TO_REFRESH) {
            statusString = "pull to refresh";
        } else if (status == STATUS_RELEASE_TO_REFRESH) {
            statusString = "release to refresh";
        }
        else if (status == STATUS_REFRESHING) {
            statusString = "refreshing";
        }
        Log.d(VIEW_LOG_TAG, "### status = " + statusString);
    }

    /*
     * 閸︺劏绻栭柌灞筋樀閻炲棜袝閹介晲绨ㄦ禒鏈典簰鏉堟儳鍩屾稉瀣閸掗攱鏌婇幋鏍拷娑撳﹥濯洪懛顏勫З閸旂姾娴囬惃鍕６妫帮拷     * @see android.view.View#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d(VIEW_LOG_TAG, "@@@ onTouchEvent : action = " + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) event.getRawY();
                Log.d(VIEW_LOG_TAG, "#### ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d(VIEW_LOG_TAG, "#### ACTION_MOVE");
                int currentY = (int) event.getRawY();
                mYDistance = currentY - mYDown;

                Log.d(VIEW_LOG_TAG, "### touch slop = " + mTouchSlop + ", distance = " + mYDistance);
                showStatus(mCurrentStatus);
                // 妤傛ê瀹虫径褌绨琱eader view閻ㄥ嫰鐝惔锔藉閸欘垯浜掗崚閿嬫煀
                if (Math.abs(mYDistance) >= mTouchSlop) {
                    if (mCurrentStatus != STATUS_REFRESHING) {
                        //
                        if (mHeaderView.getPaddingTop() > mHeaderViewHeight * 0.7f) {
                            mCurrentStatus = STATUS_RELEASE_TO_REFRESH;
                            mTipsTextView.setText(R.string.pull_to_refresh_release_label);
                        } else {
                            mCurrentStatus = STATUS_PULL_TO_REFRESH;
                            mTipsTextView.setText(R.string.pull_to_refresh_pull_label);
                        }
                    }

                    rotateHeaderArrow();
                    // 鐎佃绮﹂崝銊ㄧ獩缁傝褰囨禍锟�%
                    int scaleHeight = (int) (mYDistance * 0.8f);
                    // 濠婃垵濮╅惃鍕獩缁傝鐨禍搴＄潌楠炴洟鐝惔锟介崚鍡曠娑擄拷妞傞幍宥嗗娴肩珦eader, 閸氾箑鍨穱婵囧瘮娑撳秴褰�                   
                    if (scaleHeight <= mScrHeight / 4) {
                        adjustHeaderPadding(scaleHeight);
                    }
                }

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
     * 閹笛嗩攽閸掗攱鏌婇幙宥勭稊
     */
    private final void doRefresh() {
        if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            // 鐠佸墽鐤嗘稉鐑橆劀閸︺劌鍩涢弬鎵畱閻樿埖锟�           
        	mCurrentStatus = STATUS_REFRESHING;
            mArrowImageView.clearAnimation();
            // 闂呮劘妫宧eader娑擃厾娈戠粻顓炪仈閸ョ偓鐖�           
            mArrowImageView.setVisibility(View.GONE);
            // 鐠佸墽鐤唄eader娑擃厾娈戞潻娑樺閺夆�褰茬憴锟�          
            mHeaderProgressBar.setVisibility(View.VISIBLE);
            // 鐠佸墽鐤嗘稉锟界昂閺傚洦婀伴崣鍌涙殶
            mTimeTextView.setText(R.string.pull_to_refresh_update_time_label);
            SimpleDateFormat sdf = new SimpleDateFormat();
            mTimeTextView.append(sdf.format(new Date()));
            //
            mTipsTextView.setText(R.string.pull_to_refresh_refreshing_label);

            // 閹笛嗩攽閸ョ偠鐨�          
            if (mPullRefreshListener != null) {
                mPullRefreshListener.onRefresh();
            }
            // 娴ｇ福eadview 濮濓絽鐖堕弰鍓с仛, 閻╂潙鍩岀拫鍐暏娴滃敃efreshComplete閸氬骸鍟�梾鎰
            new HeaderViewHideTask().execute(0);

        } else if (mCurrentStatus == STATUS_REFRESHING) {
            // 娴ｇ福eadview 濮濓絽鐖堕弰鍓с仛, 閻╂潙鍩岀拫鍐暏娴滃敃efreshComplete閸氬骸鍟�梾鎰
            new HeaderViewHideTask().execute(0);
        } else {
            // 闂呮劘妫宧eader view
            hideHeaderView();
        }
    }

    /**
     * 娑撳濯洪崚鏉跨俺闁劍妞傞崝鐘烘祰閺囨潙顧�     */
    private void loadmore() {
        if (isShowFooterView() && mLoadMoreListener != null) {
            mFooterTextView.setText(R.string.pull_to_refresh_refreshing_label);
            mFooterProgressBar.setVisibility(View.VISIBLE);
            adjustFooterPadding(0);
            mCurrentStatus = STATUS_LOADING;
            mLoadMoreListener.onLoadMore();
        }
    }

    /**
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mPullRefreshListener = listener;
    }

    /**
     * @param listener
     */
    public void setOnLoadMoreListener(OnLoadListener listener) {
        mLoadMoreListener = listener;
    }

    /**
     * 閸掗攱鏌婄紒鎾存将閿涘苯婀拫鍐暏鐎瑰nRefresh閸氬氦鐨熼悽顭掔礉閸氾箑鍨痟eader view娴兼矮绔撮惄瀛樻▔缁�拷
     */
    public void refreshComplete() {
        mCurrentStatus = STATUS_IDLE;
        mHeaderProgressBar.setVisibility(View.GONE);
        mArrowImageView.setVisibility(View.GONE);
        hideHeaderView();
    }

    /**
     * 闂呮劘妫宧eader view
     */
    protected void hideHeaderView() {
        new HeaderViewHideTask().execute(-mHeaderViewHeight);
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

        mArrowImageView.setVisibility(View.VISIBLE);
        Log.d(VIEW_LOG_TAG, "------ rotateHeaderArrow");
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
     * 闂呮劘妫宖ooter view
     */
    protected void hideFooterView() {
        adjustFooterPadding(-mFooterHeight);
    }

    /**
     * 娑撳﹥濯洪崝鐘烘祰缂佹挻娼�     */
    public void loadMoreComplete() {
        mCurrentStatus = STATUS_IDLE;
        mFooterTextView.setText(R.string.pull_to_refresh_load_label);
        mFooterProgressBar.setVisibility(View.GONE);
        // adjustFooterPadding(-mFooterHeight);
        // hideFooterView();
        new FooterViewTask().execute(-mFooterHeight);
    }

    /**
     * 鐠嬪啯鏆eader view閻ㄥ垺ottom padding
     * 
     * @param bottomPadding
     */
    private void adjustFooterPadding(int bottomPadding) {
        mFooterView.setPadding(mFooterView.getPaddingLeft(), 0,
                mFooterView.getPaddingRight(), bottomPadding);
    }

    /**
     * 鐠嬪啯鏆eader view閻ㄥ墖op padding
     * 
     * @param topPadding
     */
    private void adjustHeaderPadding(int topPadding) {
        mHeaderView.setPadding(mHeaderView.getPaddingLeft(), topPadding,
                mHeaderView.getPaddingRight(), 0);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /*
     * 濠婃艾濮╂禍瀣╂閿涘苯鐤勯悳鐗堢拨閸斻劌鍩屾惔鏇㈠劥閺冩湹绗傞幏澶婂鏉炶姤娲挎径锟�    * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.
     * AbsListView, int, int, int)
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {

        Log.d(VIEW_LOG_TAG, "&&& mYDistance = " + mYDistance);
        if (mFooterView == null || mYDistance >= 0 || mCurrentStatus == STATUS_LOADING
                || mCurrentStatus == STATUS_REFRESHING) {
            return;
        }

        loadmore();
    }

    /**
     * 閺勵垰鎯侀崣顖欎簰娑撳濯洪崚閿嬫煀娴滐拷 閸楄櫕妲搁崥锔藉閸掗绨℃径鎾劥
     * 
     * @return
     */
    protected abstract boolean isTop();

    /**
     * 娑撳濯洪崚鏉跨俺闁劍妞傞崝鐘烘祰閺囨潙顧�     * 
     * @return
     */
    protected boolean isBottom() {
        return false;
    }

    /**
     * 閺勵垰鎯侀崚棰佺啊閺勫墽銇歠ooter view閻ㄥ嫭妞傞崚浼欑礉鐠囥儲鏌熷▔鏇炴躬onScroll娑擃叀鐨熼悽銊ｏ拷閸︺劏绻栨稉顏嗚娑擃厼鐤勯悳棰佺啊mScroll閺傝纭堕敍锟�    * 閸︺劏顔曠純鐢緾ontentView閺冩湹绱扮亸鍞梙is鐠佸墽鐤嗙紒妾揅ontentView,娴犮儲顒濋惄鎴濇儔mContentView閻ㄥ嫭绮﹂崝銊ょ皑娴狅拷
     * 閸ョ姵顒濇俊鍌涚亯闂囷拷顪呴弨顖涘瘮娑撳﹥濯洪崝鐘烘祰閺囨潙顧嬮崚妾揅ontentView韫囧懘銆忛弨顖涘瘮setOnScrollListener閺傝纭�     * ,楠炴湹绗栭崷銊ュ灥婵瀵瞞ContentView閺冩儼鐨熼悽銊嚉閺傝纭舵潻娑滎攽濞夈劌鍞�
     * 
     * @return
     */
    protected boolean isShowFooterView() {
        return false;
    }

    /**
     * 闂呮劘妫宧eader view閻ㄥ嫬绱撳銉ゆ崲閸旓拷 鐎圭偟骞囬獮铏拨闂呮劘妫�     * 
     * @author mrsimple
     */
    class HeaderViewHideTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int curPaddingTop = mHeaderView.getPaddingTop();
            int targetPadding = params[0];
            int step = 1;
            Log.d(VIEW_LOG_TAG, "%%% new : curPaddingTop =" + curPaddingTop + ", targetPadding = "
                    + targetPadding);
            int mode = curPaddingTop % step;

            try {
                do {
                    if (mHeaderView.getPaddingTop() <= targetPadding) {
                        break;
                    }

                    if (curPaddingTop - mode == targetPadding) {
                        if (mode != 0) {
                            curPaddingTop -= mode;
                        } else {
                            curPaddingTop = targetPadding;
                        }
                    } else {
                        curPaddingTop -= step;
                    }
                    publishProgress(curPaddingTop);
                    Log.d(VIEW_LOG_TAG, "%%% curPaddingTop = " + curPaddingTop + ", mode = " + mode
                            + ", target = "
                            + targetPadding);
                    Thread.sleep(1);
                } while (true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            adjustHeaderPadding(values[0]);
        }

    } // end of HeaderViewHideTask

    /**
     * 闂呮劘妫宧eader view閻ㄥ嫬绱撳銉ゆ崲閸旓拷 鐎圭偟骞囬獮铏拨闂呮劘妫�     * 
     * @author mrsimple
     */
    class FooterViewTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            // 0
            int curPaddingBottom = mFooterView.getPaddingBottom();
            // -footer height
            int targetPadding = params[0];
            int step = 1;
            Log.d(VIEW_LOG_TAG, "%%% new : curPaddingTop =" + curPaddingBottom
                    + ", targetPadding = "
                    + targetPadding);
            try {
                do {

                    if (curPaddingBottom == targetPadding || curPaddingBottom > 0
                            || curPaddingBottom < -mFooterHeight) {
                        break;
                    }

                    if (targetPadding == 0) {
                        curPaddingBottom += step;
                    } else if (targetPadding < 0) {
                        curPaddingBottom -= step;
                    }
                    publishProgress(curPaddingBottom);

                    Log.d(VIEW_LOG_TAG, "%%% curPaddingTop = " + curPaddingBottom
                            + ", target = "
                            + targetPadding);
                    Thread.sleep(1);
                } while (true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            adjustFooterPadding(values[0]);
        }

    } // end of HeaderViewHideTask

}
