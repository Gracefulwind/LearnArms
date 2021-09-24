package com.gracefulwind.learnarms.reader.widget.scroll;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.os.StrictMode;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName: SmartScrollView
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/15
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/15
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class SmartScrollView extends FrameLayout {

    static final int ANIMATED_SCROLL_GAP = 250;

    static final float MAX_SCROLL_FACTOR = 0.5f;

    private static final String TAG = "ScrollView";

    private long mLastScroll;

    private final Rect mTempRect = new Rect();
    private OverScroller mScroller;
    private EdgeEffect mEdgeGlowTop;
    private EdgeEffect mEdgeGlowBottom;

    /**
     * Position of the last motion event.
     */
    private int mLastMotionY;

    /**
     * True when the layout has changed but the traversal has not come through yet.
     * Ideally the view hierarchy would keep track of this for us.
     */
    private boolean mIsLayoutDirty = true;

    /**
     * The child to give focus to in the event that a child has requested focus while the
     * layout is dirty. This prevents the scroll from being wrong if the child has not been
     * laid out before requesting focus.
     */
    private View mChildToScrollTo = null;

    /**
     * True if the user is currently dragging this ScrollView around. This is
     * not the same as 'is being flinged', which can be checked by
     * mScroller.isFinished() (flinging begins when the user lifts his finger).
     */
    private boolean mIsBeingDragged = false;

    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker mVelocityTracker;

    /**
     * When set to true, the scroll view measure its child to make it fill the currently
     * visible area.
     */
    @ViewDebug.ExportedProperty(category = "layout")
    private boolean mFillViewport;

    /**
     * Whether arrow scrolling is animated.
     */
    private boolean mSmoothScrollingEnabled = true;

    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    private int mOverscrollDistance;
    private int mOverflingDistance;

    private float mVerticalScrollFactor;

    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    private int mActivePointerId = INVALID_POINTER;

    /**
     * Used during scrolling to retrieve the new offset within the window.
     */
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private int mNestedYOffset;

    /**
     * The StrictMode "critical time span" objects to catch animation
     * stutters.  Non-null when a time-sensitive animation is
     * in-flight.  Must call finish() on them when done animating.
     * These are no-ops on user builds.
     */
//    private StrictMode.Span mScrollStrictSpan = null;  // aka "drag"
//    private StrictMode.Span mFlingStrictSpan = null;

    /**
     * Sentinel value for no current active pointer.
     * Used by {@link #mActivePointerId}.
     */
    private static final int INVALID_POINTER = -1;

    private SavedState mSavedState;
    private Context mContext;
    private int mScrollY;
    private int mScrollX;

    public SmartScrollView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public SmartScrollView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        //todo:wd 看下能不能用 com.android.internal.R.attr.scrollViewStyle
        this(context, attrs, 0);
    }

    public SmartScrollView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public SmartScrollView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mContext = context;
        initScrollview();
        initViewAttrs(attrs, defStyleAttr, defStyleRes);
    }



    private void initScrollview() {
        mScroller = new OverScroller(getContext());
        setFocusable(true);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setWillNotDraw(false);
        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mOverscrollDistance = configuration.getScaledOverscrollDistance();
        mOverflingDistance = configuration.getScaledOverflingDistance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mVerticalScrollFactor = configuration.getScaledVerticalScrollFactor();
        }
    }

    private void initViewAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        //默认给true得了，后续再做成入参
        mFillViewport = true;
//        final TypedArray a = mContext.obtainStyledAttributes(
//                attrs, com.android.internal.R.styleable.ScrollView, defStyleAttr, defStyleRes);//获取属性集对象
//        setFillViewport(a.getBoolean(R.styleable.ScrollView_fillViewport, false));
    }

    /**
     * Indicates whether this ScrollView's content is stretched to fill the viewport.
     *
     * @return True if the content fills the viewport, false otherwise.
     *
     * @attr ref android.R.styleable#ScrollView_fillViewport
     */
    public boolean isFillViewport() {
        return mFillViewport;
    }

    /**
     * Indicates this ScrollView whether it should stretch its content height to fill
     * the viewport or not.
     *
     * @param fillViewport True to stretch the content's height to the viewport's
     *        boundaries, false otherwise.
     *
     * @attr ref android.R.styleable#ScrollView_fillViewport
     */
    public void setFillViewport(boolean fillViewport) {
        if (fillViewport != mFillViewport) {
            mFillViewport = fillViewport;
            requestLayout();
        }
    }

    @Override
    public void requestLayout() {
        mIsLayoutDirty = true;
        super.requestLayout();
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
//        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
        final ViewGroup.LayoutParams lp = child.getLayoutParams();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                paddingLeft + paddingRight, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(MeasureSpec.UNSPECIFIED,
                paddingTop + paddingBottom, lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        if (!mFillViewport) {//如果mFillViewport为true，则子布局充满当前可见区域，宽高即不需要重新测量。
//            return;
//        }
//
//        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        if (heightMode == MeasureSpec.UNSPECIFIED) {
//            return;
//        }
//
//        if (getChildCount() > 0) {
//            final View child = getChildAt(0);
//            final int widthPadding;
//            final int heightPadding;
//            final int targetSdkVersion = getContext().getApplicationInfo().targetSdkVersion;
//            final FrameLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
//            int paddingLeft = getPaddingLeft();
//            int paddingRight = getPaddingRight();
//            int paddingTop = getPaddingTop();
//            int paddingBottom = getPaddingBottom();
//            if (targetSdkVersion >= Build.VERSION_CODES.M) {
//                widthPadding = paddingLeft + paddingRight + lp.leftMargin + lp.rightMargin;
//                heightPadding = paddingTop + paddingBottom + lp.topMargin + lp.bottomMargin;
//            } else {
//                widthPadding = paddingLeft + paddingRight;
//                heightPadding = paddingTop + paddingBottom;
//            }
//
//            final int desiredHeight = getMeasuredHeight() - heightPadding;
//            if (child.getMeasuredHeight() < desiredHeight) {
//                final int childWidthMeasureSpec = getChildMeasureSpec(
//                        widthMeasureSpec, widthPadding, lp.width);
//                final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
//                        desiredHeight, MeasureSpec.EXACTLY);
//                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
//            }
////            final int childWidthMeasureSpec = getChildMeasureSpec(
////                    widthMeasureSpec, widthPadding, lp.width);
////            final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
////                    desiredHeight, MeasureSpec.EXACTLY);
////            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
//        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        mIsLayoutDirty = false;
//        // Give a child focus if it needs it
//        if (mChildToScrollTo != null && isViewDescendantOf(mChildToScrollTo, this)) {
//            scrollToChild(mChildToScrollTo);
//        }
//        mChildToScrollTo = null;
//        int paddingBottom = getPaddingBottom();
//        int paddingTop = getPaddingTop();
//        if (!isLaidOut()) {
//            if (mSavedState != null) {
//                mScrollY = mSavedState.scrollPosition;
//                mSavedState = null;
//            } // mScrollY default value is "0"
//
//            final int childHeight = (getChildCount() > 0) ? getChildAt(0).getMeasuredHeight() : 0;
//            final int scrollRange = Math.max(0,
//                    childHeight - (bottom - top - paddingBottom - paddingTop));
//
//            // Don't forget to clamp
//            if (mScrollY > scrollRange) {
//                mScrollY = scrollRange;
//            } else if (mScrollY < 0) {
//                mScrollY = 0;
//            }
//        }
//
//        // Calling this with the present values causes it to re-claim them
//        scrollTo(mScrollX, mScrollY);
    }

    /**
     * Scrolls the view to the given child.
     *
     * @param child the View to scroll to
     */
    private void scrollToChild(View child) {
        child.getDrawingRect(mTempRect);

        /* Offset from child's local coordinates to ScrollView coordinates */
        offsetDescendantRectToMyCoords(child, mTempRect);

        int scrollDelta = computeScrollDeltaToGetChildRectOnScreen(mTempRect);

        if (scrollDelta != 0) {
            scrollBy(0, scrollDelta);
        }
    }

    /**
     * Compute the amount to scroll in the Y direction in order to get
     * a rectangle completely on the screen (or, if taller than the screen,
     * at least the first screen size chunk of it).
     *
     * @param rect The rect.
     * @return The scroll delta.
     */
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (getChildCount() == 0) return 0;

        int height = getHeight();
        int screenTop = getScrollY();
        int screenBottom = screenTop + height;

        int fadingEdge = getVerticalFadingEdgeLength();

        // leave room for top fading edge as long as rect isn't at very top
        if (rect.top > 0) {
            screenTop += fadingEdge;
        }

        // leave room for bottom fading edge as long as rect isn't at very bottom
        if (rect.bottom < getChildAt(0).getHeight()) {
            screenBottom -= fadingEdge;
        }

        int scrollYDelta = 0;

        if (rect.bottom > screenBottom && rect.top > screenTop) {
            // need to move down to get it in view: move down just enough so
            // that the entire rectangle is in view (or at least the first
            // screen size chunk).

            if (rect.height() > height) {
                // just enough to get screen size chunk on
                scrollYDelta += (rect.top - screenTop);
            } else {
                // get entire rect at bottom of screen
                scrollYDelta += (rect.bottom - screenBottom);
            }

            // make sure we aren't scrolling beyond the end of our content
            int bottom = getChildAt(0).getBottom();
            int distanceToBottom = bottom - screenBottom;
            scrollYDelta = Math.min(scrollYDelta, distanceToBottom);

        } else if (rect.top < screenTop && rect.bottom < screenBottom) {
            // need to move up to get it in view: move up just enough so that
            // entire rectangle is in view (or at least the first screen
            // size chunk of it).

            if (rect.height() > height) {
                // screen size chunk
                scrollYDelta -= (screenBottom - rect.bottom);
            } else {
                // entire rect at top
                scrollYDelta -= (screenTop - rect.top);
            }

            // make sure we aren't scrolling any further than the top our content
            scrollYDelta = Math.max(scrollYDelta, -getScrollY());
        }
        return scrollYDelta;
    }

    /**
     * Return true if child is a descendant of parent, (or equal to the parent).
     */
    private static boolean isViewDescendantOf(View child, View parent) {
        if (child == parent) {
            return true;
        }

        final ViewParent theParent = child.getParent();
        return (theParent instanceof ViewGroup) && isViewDescendantOf((View) theParent, parent);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        View currentFocused = findFocus();
        if (null == currentFocused || this == currentFocused)
            return;

        // If the currently-focused view was visible on the screen when the
        // screen was at the old height, then scroll the screen to make that
        // view visible with the new screen height.
        if (isWithinDeltaOfScreen(currentFocused, 0, oldh)) {
            currentFocused.getDrawingRect(mTempRect);
            offsetDescendantRectToMyCoords(currentFocused, mTempRect);
            int scrollDelta = computeScrollDeltaToGetChildRectOnScreen(mTempRect);
            doScrollY(scrollDelta);
        }
    }

    /**
     * @return whether the descendant of this scroll view is within delta
     *  pixels of being on the screen.
     */
    private boolean isWithinDeltaOfScreen(View descendant, int delta, int height) {
        descendant.getDrawingRect(mTempRect);
        offsetDescendantRectToMyCoords(descendant, mTempRect);

        return (mTempRect.bottom + delta) >= getScrollY()
                && (mTempRect.top - delta) <= (getScrollY() + height);
    }

    /**
     * Smooth scroll by a Y delta
     *
     * @param delta the number of pixels to scroll by on the Y axis
     */
    private void doScrollY(int delta) {
        if (delta != 0) {
            if (mSmoothScrollingEnabled) {
//                smoothScrollBy(0, delta);
                scrollBy(0, delta);
            } else {
                scrollBy(0, delta);
            }
        }
    }

//    /**
//     * Like {@link View#scrollBy}, but scroll smoothly instead of immediately.
//     *
//     * @param dx the number of pixels to scroll by on the X axis
//     * @param dy the number of pixels to scroll by on the Y axis
//     */
//    public final void smoothScrollBy(int dx, int dy) {
//        if (getChildCount() == 0) {
//            // Nothing to do.
//            return;
//        }
//        long duration = AnimationUtils.currentAnimationTimeMillis() - mLastScroll;
//        if (duration > ANIMATED_SCROLL_GAP) {
//            final int height = getHeight() - mPaddingBottom - mPaddingTop;
//            final int bottom = getChildAt(0).getHeight();
//            final int maxY = Math.max(0, bottom - height);
//            final int scrollY = mScrollY;
//            dy = Math.max(0, Math.min(scrollY + dy, maxY)) - scrollY;
//
//            mScroller.startScroll(mScrollX, scrollY, 0, dy);
//            postInvalidateOnAnimation();
//        } else {
//            if (!mScroller.isFinished()) {
//                mScroller.abortAnimation();
//                if (mFlingStrictSpan != null) {
//                    mFlingStrictSpan.finish();
//                    mFlingStrictSpan = null;
//                }
//            }
//            scrollBy(dx, dy);
//        }
//        mLastScroll = AnimationUtils.currentAnimationTimeMillis();
//    }



    static class SavedState extends BaseSavedState {
        public int scrollPosition;

        SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            scrollPosition = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(scrollPosition);
        }

        @Override
        public String toString() {
            return "ScrollView.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " scrollPosition=" + scrollPosition + "}";
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
