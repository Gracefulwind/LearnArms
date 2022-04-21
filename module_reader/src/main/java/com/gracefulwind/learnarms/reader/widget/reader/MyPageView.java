package com.gracefulwind.learnarms.reader.widget.reader;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @ClassName: PageView
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/23
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/23
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class MyPageView extends View {
    public static final String TAG = "PageView";

    public MyPageView(Context context) {
        this(context, null);
    }

    public MyPageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyPageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//==================================================================================================
    private int mViewWidth = 0; // 当前View的宽
    private int mViewHeight = 0; // 当前View的高


    private int mStartX = 0;
    private int mStartY = 0;
    private boolean isMove = false;
    // 初始化参数
    private int mBgColor = 0xFFCEC29C;
    //翻页模式
    private PageMode mPageMode = PageMode.SIMULATION;
    // 是否允许点击
    private boolean canTouch = true;
    // 唤醒菜单的区域
    private RectF mCenterRect = null;
    private boolean isPrepare;
//    // 动画类
//    private PageAnimation mPageAnim;
//    // 动画监听类
//    private PageAnimation.OnPageChangeListener mPageAnimListener = new PageAnimation.OnPageChangeListener() {
//        @Override
//        public boolean hasPrev() {
//            return PageView.this.hasPrevPage();
//        }
//
//        @Override
//        public boolean hasNext() {
//            return PageView.this.hasNextPage();
//        }
//
//        @Override
//        public void pageCancel() {
//            PageView.this.pageCancel();
//        }
//    };
//
//    //点击监听
//    private TouchListener mTouchListener;
//    //内容加载器
//    private PageLoader mPageLoader;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        isPrepare = true;

//        if (mPageLoader != null) {
//            mPageLoader.prepareDisplay(w, h);
//        }
    }

//    //设置翻页的模式
//    void setPageMode(PageMode pageMode) {
//        mPageMode = pageMode;
//        //视图未初始化的时候，禁止调用
//        if (mViewWidth == 0 || mViewHeight == 0) {
//            return;
//        }
//
//        switch (mPageMode) {
//            case SIMULATION:
//                mPageAnim = new SimulationPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
//                break;
//            case COVER:
//                mPageAnim = new CoverPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
//                break;
//            case SLIDE:
//                mPageAnim = new SlidePageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
//                break;
//            case NONE:
//                mPageAnim = new NonePageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
//                break;
//            case SCROLL:
//                mPageAnim = new ScrollPageAnim(mViewWidth, mViewHeight, 0,
//                        mPageLoader.getMarginHeight(), this, mPageAnimListener, mPageLoader.mSafeInsetTop);
//                break;
//            case LEFT_HAND:
//                mPageAnim = new LeftNonePageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
//                break;
//            default:
//                mPageAnim = new SimulationPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
//                break;
//        }
//    }



}
