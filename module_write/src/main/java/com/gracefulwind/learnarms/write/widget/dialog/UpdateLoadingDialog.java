package com.gracefulwind.learnarms.write.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.RxTimerUtil;
import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.write.R;
import com.gracefulwind.learnarms.write.R2;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: UpdateLoadingDialog
 * @Author: Gracefulwind
 * @CreateDate: 2021/11/3
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/11/3
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class UpdateLoadingDialog extends Dialog {
    public static final String TAG = "UpdateLoadingDialog";

    private Context mContext;
    @DrawableRes int mSuccessIndicator;
    @DrawableRes int mErrorIndicator;
    StatusEnum status;
    enum StatusEnum{
        STEP1,
        STEP2,
        STEP3;
    }
    //step 1
    OnStepSuccessCallback step1SuccessCallback;
    OnStepErrorCallback step1ErrorCallback;
    //step 2
    OnStepSuccessCallback step2SuccessCallback;
    OnStepErrorCallback step2ErrorCallback;
    //step 3
    OnStepSuccessCallback step3SuccessCallback;
    OnStepErrorCallback step3ErrorCallback;

    @BindView(R2.id.dul_tv_title)
    TextView dulTvTitle;
    @BindView(R2.id.dul_iv_indicator)
    ImageView dulIvIndicator;
    @BindView(R2.id.dul_iv_step1)
    ImageView dulIvStep1;
    @BindView(R2.id.dul_iv_step2)
    ImageView dulIvStep2;
    @BindView(R2.id.dul_iv_step3)
    ImageView dulIvStep3;
    @BindView(R2.id.dul_pb_step1_2)
    ProgressBar dulPbStep1To2;
    @BindView(R2.id.dul_pb_step2_3)
    ProgressBar dulPbStep2To3;
    @BindView(R2.id.dul_rl_error_buttons)
    RelativeLayout dulRlErrorButtons;

    public UpdateLoadingDialog(@NonNull @NotNull Context context) {
        this(context, R.style.public_dialog_progress);
    }

    public UpdateLoadingDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected UpdateLoadingDialog(@NonNull @NotNull Context context, boolean cancelable, @Nullable @org.jetbrains.annotations.Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(@NotNull Context context) {
        mContext = context;
//        View inflate = UiUtil.inflate(R.layout.dialog_update_loading);
        setContentView(R.layout.dialog_update_loading);
        ButterKnife.bind(this, this);
        status = StatusEnum.STEP1;

    }

    public void setIndicator(@DrawableRes int successIndicator, @DrawableRes int errorIndicator) {
        mSuccessIndicator = successIndicator;
        mErrorIndicator = errorIndicator;
        Glide.with(mContext).asGif().load(successIndicator).into(dulIvIndicator);
    }

    public void setStep1Callback(OnStepSuccessCallback successCallback, OnStepErrorCallback errorCallback){
        step1SuccessCallback = successCallback;
        step1ErrorCallback = errorCallback;
    }

    public void setStep2Callback(OnStepSuccessCallback successCallback, OnStepErrorCallback errorCallback){
        step2SuccessCallback = successCallback;
        step2ErrorCallback = errorCallback;
    }

    public void setStep3Callback(OnStepSuccessCallback successCallback, OnStepErrorCallback errorCallback){
        step3SuccessCallback = successCallback;
        step3ErrorCallback = errorCallback;
    }

    @OnClick({R2.id.dul_iv_indicator, R2.id.dul_ll_status})
    public void onViewClicked(View view){
        int id = view.getId();
        if(R.id.dul_iv_indicator == id){
            this.cancel();
        }else if(R.id.dul_ll_status == id){

        }else if(R.id.dul_ll_status == id){

        }
    }

    public void show(){
//        Glide.with(mContext).asGif().load(R.drawable.gif_uld_upload_ing).into(dulIvStep1);
        loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep1);
        super.show();
        RxTimerUtil.timer(2000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                setStep1Success(true);
                RxTimerUtil.timer(3000, new RxTimerUtil.IRxNext() {
                    @Override
                    public void doNext(long number) {
                        setStep2Success(false);
                    }
                });
            }
        });

    }

    public void setStep1Success(boolean isSuccess){
        if(isSuccess){
            dulRlErrorButtons.setVisibility(View.GONE);
            loadOneTimeGif(mContext, R.drawable.gif_uld_upload_success, dulIvStep1, new GifListener() {
                @Override
                public void gifPlayComplete() {
                    Toast.makeText(mContext, "Gif播放完成", Toast.LENGTH_SHORT).show();
                }
            });
//            Glide.with(mContext).asGif().load(R.drawable.gif_uld_upload_ing).into(dulIvStep1);
            loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep2);
        }else {
            dulRlErrorButtons.setVisibility(View.VISIBLE);
            loadOneTimeGif(mContext, R.drawable.gif_uld_upload_error, dulIvStep1, new GifListener() {
                @Override
                public void gifPlayComplete() {
                    Toast.makeText(mContext, "Gif播放完成", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setStep2Success(boolean isSuccess){
        dulPbStep1To2.setProgress(100);
        if(isSuccess){
            dulRlErrorButtons.setVisibility(View.GONE);
            dulPbStep1To2.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_update_loading_success));
            loadOneTimeGif(mContext, R.drawable.gif_uld_upload_success, dulIvStep2, new GifListener() {
                @Override
                public void gifPlayComplete() {
                    Toast.makeText(mContext, "Gif播放完成", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            dulRlErrorButtons.setVisibility(View.VISIBLE);
            dulPbStep1To2.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_update_loading_error));
            loadOneTimeGif(mContext, R.drawable.gif_uld_upload_error, dulIvStep2, new GifListener() {
                @Override
                public void gifPlayComplete() {
                    Toast.makeText(mContext, "Gif播放完成", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        dulPbStep1To2.setProgress(100);
    }

    public static void loadGif(Context context, Object model, final ImageView imageView){
        Glide.with(context).asGif().load(model).into(imageView);
    }

    public static void loadOneTimeGif(Context context, Object model, final ImageView imageView, final GifListener gifListener) {
        Glide.with(context).asGif().load(model).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                try {
                    Field gifStateField = GifDrawable.class.getDeclaredField("state");
                    gifStateField.setAccessible(true);
                    Class gifStateClass = Class.forName("com.bumptech.glide.load.resource.gif.GifDrawable$GifState");
                    Field gifFrameLoaderField = gifStateClass.getDeclaredField("frameLoader");
                    gifFrameLoaderField.setAccessible(true);
                    Class gifFrameLoaderClass = Class.forName("com.bumptech.glide.load.resource.gif.GifFrameLoader");
                    Field gifDecoderField = gifFrameLoaderClass.getDeclaredField("gifDecoder");
                    gifDecoderField.setAccessible(true);
                    Class gifDecoderClass = Class.forName("com.bumptech.glide.gifdecoder.GifDecoder");
                    Object gifDecoder = gifDecoderField.get(gifFrameLoaderField.get(gifStateField.get(resource)));
                    Method getDelayMethod = gifDecoderClass.getDeclaredMethod("getDelay", int.class);
                    getDelayMethod.setAccessible(true);
                    //设置只播放一次
                    resource.setLoopCount(1);
                    //获得总帧数
                    int count = resource.getFrameCount();
                    int delay = 0;
                    for (int i = 0; i < count; i++) {
                        //计算每一帧所需要的时间进行累加
                        delay += (int) getDelayMethod.invoke(gifDecoder, i);
                    }
                    imageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (gifListener != null) {
                                gifListener.gifPlayComplete();
                            }
                        }
                    }, delay);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }).into(imageView);
    }

    /**
     * Gif播放完毕回调
     */
    public interface GifListener {
        void gifPlayComplete();
    }

//==================================================================================================
    public static class Builder{

        private Activity mContext;
        @DrawableRes int mSuccessIndicator;
        @DrawableRes int mErrorIndicator;
        //step 1
        OnStepSuccessCallback step1SuccessCallback;
        OnStepErrorCallback step1ErrorCallback;
        //step 2
        OnStepSuccessCallback step2SuccessCallback;
        OnStepErrorCallback step2ErrorCallback;
        //step 3
        OnStepSuccessCallback step3SuccessCallback;
        OnStepErrorCallback step3ErrorCallback;


        public Builder(Activity context){
            mContext = context;
        }

        public Builder setIndicator(@DrawableRes int successIndicator, @DrawableRes int errorIndicator){
            mSuccessIndicator = successIndicator;
            mErrorIndicator = errorIndicator;
            return this;
        }

        public Builder setStep1Callback(OnStepSuccessCallback successCallback, OnStepErrorCallback errorCallback){
            step1SuccessCallback = successCallback;
            step1ErrorCallback = errorCallback;
            return this;
        }

        public Builder setStep2Callback(OnStepSuccessCallback successCallback, OnStepErrorCallback errorCallback){
            step2SuccessCallback = successCallback;
            step2ErrorCallback = errorCallback;
            return this;
        }

        public Builder setStep3Callback(OnStepSuccessCallback successCallback, OnStepErrorCallback errorCallback){
            step3SuccessCallback = successCallback;
            step3ErrorCallback = errorCallback;
            return this;
        }

        public UpdateLoadingDialog build(){
            UpdateLoadingDialog dialog = new UpdateLoadingDialog(mContext);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setIndicator(mSuccessIndicator, mErrorIndicator);
            return dialog;
        }
    }


//==================================================================================================
    /**
     * 失败的回调
     * */
    public interface OnStepErrorCallback {
        void onCancelClicked();
        void onRetryClicked();
    }

    /**
     * 成功的回调
     * */
    public interface OnStepSuccessCallback {
        void onSuccess();
    }
}
