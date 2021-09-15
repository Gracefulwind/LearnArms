//package com.gracefulwind.learnarms.reader.widget.scroll;
//
//import android.content.Context;
//import android.graphics.Rect;
//import android.os.Build;
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//
//import android.os.StrictMode;
//import android.util.AttributeSet;
//import android.view.VelocityTracker;
//import android.view.View;
//import android.view.ViewConfiguration;
//import android.view.ViewDebug;
//import android.widget.EdgeEffect;
//import android.widget.FrameLayout;
//import android.widget.OverScroller;
//
//import org.jetbrains.annotations.NotNull;
//
///**
// * @ClassName: SmartScrollView
// * @Author: Gracefulwind
// * @CreateDate: 2021/9/15
// * @Description: ---------------------------
// * @UpdateUser:
// * @UpdateDate: 2021/9/15
// * @UpdateRemark:
// * @Version: 1.0
// * @Email: 429344332@qq.com
// */
//public class SmartScrollView extends FrameLayout {
//
//    static final int ANIMATED_SCROLL_GAP = 250;
//
//    static final float MAX_SCROLL_FACTOR = 0.5f;
//
//    private static final String TAG = "ScrollView";
//
//    private long mLastScroll;
//
//    private final Rect mTempRect = new Rect();
//    private OverScroller mScroller;
//    private EdgeEffect mEdgeGlowTop;
//    private EdgeEffect mEdgeGlowBottom;
//
//    /**
//     * Position of the last motion event.
//     */
//    private int mLastMotionY;
//
//    /**
//     * True when the layout has changed but the traversal has not come through yet.
//     * Ideally the view hierarchy would keep track of this for us.
//     */
//    private boolean mIsLayoutDirty = true;
//
//    /**
//     * The child to give focus to in the event that a child has requested focus while the
//     * layout is dirty. This prevents the scroll from being wrong if the child has not been
//     * laid out before requesting focus.
//     */
//    private View mChildToScrollTo = null;
//
//    /**
//     * True if the user is currently dragging this ScrollView around. This is
//     * not the same as 'is being flinged', which can be checked by
//     * mScroller.isFinished() (flinging begins when the user lifts his finger).
//     */
//    private boolean mIsBeingDragged = false;
//
//    /**
//     * Determines speed during touch scrolling
//     */
//    private VelocityTracker mVelocityTracker;
//
//    /**
//     * When set to true, the scroll view measure its child to make it fill the currently
//     * visible area.
//     */
//    @ViewDebug.ExportedProperty(category = "layout")
//    private boolean mFillViewport;
//
//    /**
//     * Whether arrow scrolling is animated.
//     */
//    private boolean mSmoothScrollingEnabled = true;
//
//    private int mTouchSlop;
//    private int mMinimumVelocity;
//    private int mMaximumVelocity;
//
//    private int mOverscrollDistance;
//    private int mOverflingDistance;
//
//    private float mVerticalScrollFactor;
//
//    /**
//     * ID of the active pointer. This is used to retain consistency during
//     * drags/flings if multiple pointers are used.
//     */
//    private int mActivePointerId = INVALID_POINTER;
//
//    /**
//     * Used during scrolling to retrieve the new offset within the window.
//     */
//    private final int[] mScrollOffset = new int[2];
//    private final int[] mScrollConsumed = new int[2];
//    private int mNestedYOffset;
//
//    /**
//     * The StrictMode "critical time span" objects to catch animation
//     * stutters.  Non-null when a time-sensitive animation is
//     * in-flight.  Must call finish() on them when done animating.
//     * These are no-ops on user builds.
//     */
//    private StrictMode.Span mScrollStrictSpan = null;  // aka "drag"
//    private StrictMode.Span mFlingStrictSpan = null;
//
//    /**
//     * Sentinel value for no current active pointer.
//     * Used by {@link #mActivePointerId}.
//     */
//    private static final int INVALID_POINTER = -1;
//
//    private SavedState mSavedState;
//    private Context mContext;
//
//    public SmartScrollView(@NonNull @NotNull Context context) {
//        this(context, null);
//    }
//
//    public SmartScrollView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
//        //todo:wd 看下能不能用 com.android.internal.R.attr.scrollViewStyle
//        this(context, attrs, 0);
//    }
//
//    public SmartScrollView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context, attrs, defStyleAttr, 0);
//    }
//
//    public SmartScrollView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context, attrs, defStyleAttr, defStyleRes);
//    }
//
//    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        mContext = context;
//        initScrollview();
//    }
//
//    private void initScrollview() {
//        mScroller = new OverScroller(getContext());
//        setFocusable(true);
//        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
//        setWillNotDraw(false);
//        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
//        mTouchSlop = configuration.getScaledTouchSlop();
//        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
//        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
//        mOverscrollDistance = configuration.getScaledOverscrollDistance();
//        mOverflingDistance = configuration.getScaledOverflingDistance();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            mVerticalScrollFactor = configuration.getScaledVerticalScrollFactor();
//        }
//    }
//
//
//    static class SavedState extends BaseSavedState {
//        public int scrollPosition;
//
//        SavedState(Parcelable superState) {
//            super(superState);
//        }
//
//        public SavedState(Parcel source) {
//            super(source);
//            scrollPosition = source.readInt();
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            super.writeToParcel(dest, flags);
//            dest.writeInt(scrollPosition);
//        }
//
//        @Override
//        public String toString() {
//            return "ScrollView.SavedState{"
//                    + Integer.toHexString(System.identityHashCode(this))
//                    + " scrollPosition=" + scrollPosition + "}";
//        }
//
//        public static final Parcelable.Creator<SavedState> CREATOR
//                = new Parcelable.Creator<SavedState>() {
//            public SavedState createFromParcel(Parcel in) {
//                return new SavedState(in);
//            }
//
//            public SavedState[] newArray(int size) {
//                return new SavedState[size];
//            }
//        };
//    }
//}
