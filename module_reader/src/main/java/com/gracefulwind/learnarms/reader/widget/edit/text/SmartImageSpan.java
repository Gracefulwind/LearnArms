package com.gracefulwind.learnarms.reader.widget.edit.text;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;

import java.io.InputStream;


import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * @ClassName: SmartImageSpan
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/7
 * @Description: ---------------------------
 * 用于Html的Tag处理用，自定义的Span
 * @UpdateUser:
 * @UpdateDate: 2021/9/7
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class SmartImageSpan extends SmartDynamicDrawableSpan {
    public static final String TAG = SmartImageSpan.class.getName();
    @Nullable
    private Drawable mDrawable;
    @Nullable
    private Uri mContentUri;
    @DrawableRes
    private int mResourceId;
    @Nullable
    private Context mContext;
    @Nullable
    private String mSource;

    /**
     * @deprecated Use {@link #SmartImageSpan(Context, Bitmap)} instead.
     */
    @Deprecated
    public SmartImageSpan(@NonNull Bitmap b) {
        this(null, b, ALIGN_BOTTOM);
    }

    /**
     * @deprecated Use {@link #SmartImageSpan(Context, Bitmap, int)} instead.
     */
    @Deprecated
    public SmartImageSpan(@NonNull Bitmap b, int verticalAlignment) {
        this(null, b, verticalAlignment);
    }

    /**
     * Constructs an {@link ImageSpan} from a {@link Context} and a {@link Bitmap} with the default
     * alignment {@link DynamicDrawableSpan#ALIGN_BOTTOM}
     *
     * @param context context used to create a drawable from {@param bitmap} based on the display
     *                metrics of the resources
     * @param bitmap  bitmap to be rendered
     */
    public SmartImageSpan(@NonNull Context context, @NonNull Bitmap bitmap) {
        this(context, bitmap, ALIGN_BOTTOM);
    }

    /**
     * Constructs an {@link ImageSpan} from a {@link Context}, a {@link Bitmap} and a vertical
     * alignment.
     *
     * @param context           context used to create a drawable from {@param bitmap} based on
     *                          the display metrics of the resources
     * @param bitmap            bitmap to be rendered
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     *                          {@link DynamicDrawableSpan#ALIGN_BASELINE}
     */
    public SmartImageSpan(@NonNull Context context, @NonNull Bitmap bitmap, int verticalAlignment) {
        super(verticalAlignment);
        mContext = context;
        mDrawable = context != null
                ? new BitmapDrawable(context.getResources(), bitmap)
                : new BitmapDrawable(bitmap);
        int width = mDrawable.getIntrinsicWidth();
        int height = mDrawable.getIntrinsicHeight();
        mDrawable.setBounds(0, 0, width > 0 ? width : 0, height > 0 ? height : 0);
    }

    /**
     * Constructs an {@link ImageSpan} from a drawable with the default
     * alignment {@link DynamicDrawableSpan#ALIGN_BOTTOM}.
     *
     * @param drawable drawable to be rendered
     */
    public SmartImageSpan(@NonNull Drawable drawable) {
        this(drawable, ALIGN_BOTTOM);
    }

    /**
     * Constructs an {@link ImageSpan} from a drawable and a vertical alignment.
     *
     * @param drawable          drawable to be rendered
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     *                          {@link DynamicDrawableSpan#ALIGN_BASELINE}
     */
    public SmartImageSpan(@NonNull Drawable drawable, int verticalAlignment) {
        super(verticalAlignment);
        mDrawable = drawable;
    }

    /**
     * Constructs an {@link ImageSpan} from a drawable and a source with the default
     * alignment {@link DynamicDrawableSpan#ALIGN_BOTTOM}
     *
     * @param drawable drawable to be rendered
     * @param source   drawable's Uri source
     */
    public SmartImageSpan(@NonNull Drawable drawable, @NonNull String source) {
        this(drawable, source, ALIGN_BOTTOM);
    }

    /**
     * Constructs an {@link ImageSpan} from a drawable, a source and a vertical alignment.
     *
     * @param drawable          drawable to be rendered
     * @param source            drawable's uri source
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     *                          {@link DynamicDrawableSpan#ALIGN_BASELINE}
     */
    public SmartImageSpan(@NonNull Drawable drawable, @NonNull String source, int verticalAlignment) {
        super(verticalAlignment);
        mDrawable = drawable;
        mSource = source;
    }

    /**
     * Constructs an {@link ImageSpan} from a {@link Context} and a {@link Uri} with the default
     * alignment {@link DynamicDrawableSpan#ALIGN_BOTTOM}. The Uri source can be retrieved via
     * {@link #getSource()}
     *
     * @param context context used to create a drawable from {@param bitmap} based on the display
     *                metrics of the resources
     * @param uri     {@link Uri} used to construct the drawable that will be rendered
     */
    public SmartImageSpan(@NonNull Context context, @NonNull Uri uri) {
        this(context, uri, ALIGN_BOTTOM);
    }

    /**
     * Constructs an {@link ImageSpan} from a {@link Context}, a {@link Uri} and a vertical
     * alignment. The Uri source can be retrieved via {@link #getSource()}
     *
     * @param context           context used to create a drawable from {@param bitmap} based on
     *                          the display
     *                          metrics of the resources
     * @param uri               {@link Uri} used to construct the drawable that will be rendered.
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     *                          {@link DynamicDrawableSpan#ALIGN_BASELINE}
     */
    public SmartImageSpan(@NonNull Context context, @NonNull Uri uri, int verticalAlignment) {
        super(verticalAlignment);
        mContext = context;
        mContentUri = uri;
        mSource = uri.toString();
    }

    /**
     * Constructs an {@link ImageSpan} from a {@link Context} and a resource id with the default
     * alignment {@link DynamicDrawableSpan#ALIGN_BOTTOM}
     *
     * @param context    context used to retrieve the drawable from resources
     * @param resourceId drawable resource id based on which the drawable is retrieved
     */
    public SmartImageSpan(@NonNull Context context, @DrawableRes int resourceId) {
        this(context, resourceId, ALIGN_BOTTOM);
    }

    /**
     * Constructs an {@link ImageSpan} from a {@link Context}, a resource id and a vertical
     * alignment.
     *
     * @param context           context used to retrieve the drawable from resources
     * @param resourceId        drawable resource id based on which the drawable is retrieved.
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     *                          {@link DynamicDrawableSpan#ALIGN_BASELINE}
     */
    public SmartImageSpan(@NonNull Context context, @DrawableRes int resourceId,
                     int verticalAlignment) {
        super(verticalAlignment);
        mContext = context;
        mResourceId = resourceId;
    }

    @Override
    public Drawable getDrawable() {
        Drawable drawable = null;

        if (mDrawable != null) {
            drawable = mDrawable;
        } else if (mContentUri != null) {
            Bitmap bitmap = null;
            try {
                InputStream is = mContext.getContentResolver().openInputStream(
                        mContentUri);
                bitmap = BitmapFactory.decodeStream(is);
                drawable = new BitmapDrawable(mContext.getResources(), bitmap);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                is.close();
            } catch (Exception e) {
                Log.e("ImageSpan", "Failed to loaded content " + mContentUri, e);
            }
        } else {
            try {
                drawable = mContext.getDrawable(mResourceId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
            } catch (Exception e) {
                Log.e("ImageSpan", "Unable to find resource: " + mResourceId);
            }
        }

        return drawable;
    }

    /**
     * Returns the source string that was saved during construction.
     *
     * @return the source string that was saved during construction
     * @see #SmartImageSpan(Drawable, String) and this{@link #SmartImageSpan(Context, Uri)}
     */
    @Nullable
    public String getSource() {
        return mSource;
    }

//    @Override
//    public int getSize(@android.support.annotation.NonNull @NotNull Paint paint, CharSequence text, int start, int end, @android.support.annotation.Nullable @org.jetbrains.annotations.Nullable Paint.FontMetricsInt fontMetricsInt) {
//        Drawable drawable = getDrawable();
//        Rect rect = drawable.getBounds();
//        if (fontMetricsInt != null) {
//            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
//            int fontHeight = fmPaint.descent - fmPaint.ascent;
//            int drHeight = rect.bottom - rect.top;
//            int centerY = fmPaint.ascent + fontHeight / 2;
//
//            fontMetricsInt.ascent = centerY - drHeight / 2;
//            fontMetricsInt.top = fontMetricsInt.ascent;
//            fontMetricsInt.bottom = centerY + drHeight / 2;
//            fontMetricsInt.descent = fontMetricsInt.bottom;
//        }
//        return rect.right;
//    }
//
//    /**
//     * 覆写draw方法，去除高度变化
//     * */
//    @Override
//    public void draw(@android.support.annotation.NonNull @NotNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @android.support.annotation.NonNull @NotNull Paint paint) {
//        Drawable drawable = getCachedDrawable();
//        canvas.save();
//        Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
//        int fontHeight = fmPaint.descent - fmPaint.ascent;
//        int centerY = y + fmPaint.descent - fontHeight / 2;
//        int transY = centerY - (drawable.getBounds().bottom - drawable.getBounds().top) / 2;
//        canvas.translate(x, transY);
//        drawable.draw(canvas);
//        canvas.restore();
//    }
//
//    private WeakReference<Drawable> mDrawableRef;
//
//    private Drawable getCachedDrawable() {
//        WeakReference<Drawable> wr = mDrawableRef;
//        Drawable d = null;
//        if (wr != null) {
//            d = wr.get();
//        }
//
//        if (d == null) {
//            d = getDrawable();
//            mDrawableRef = new WeakReference<>(d);
//        }
//
//        return d;
//    }
}
