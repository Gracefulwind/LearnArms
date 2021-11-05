package com.gracefulwind.learnarms.write.widget.dialog;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.gracefulwind.learnarms.commonsdk.utils.GlideUtil;
import com.gracefulwind.learnarms.write.R;
import com.gracefulwind.learnarms.write.R2;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gracefulwind.learnarms.commonsdk.utils.GlideUtil.loadBitmap;
import static com.gracefulwind.learnarms.commonsdk.utils.GlideUtil.loadGifOneTime;

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

    float loadFakeProgress = 0.85f;
    float loadFakeProgress2 = 0.95f;


    @DrawableRes int mSuccessIndicator;
    @DrawableRes int mErrorIndicator;

    StatusEnum status;

    private ObjectAnimator oaIndicator1To2;
    private ObjectAnimator oaProgress1To2;
    private ObjectAnimator oaIndicator2To3;
    private ObjectAnimator oaProgress2To3;

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
    @BindView(R2.id.dul_ll_progress_container)
    LinearLayout dulLlProgressContainer;
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

    @OnClick({R2.id.dul_iv_indicator, R2.id.dul_tv_cancel, R2.id.dul_tv_retry})
    public void onViewClicked(View view){
        int id = view.getId();
        if(R.id.dul_iv_indicator == id){
            System.out.println("==============");
            System.out.println("=====test======");
            System.out.println("==============");
        }else if(R.id.dul_tv_cancel == id){
            switch (status){
                case STEP1:
                    if(null != step1ErrorCallback){
                        step1ErrorCallback.onCancelClicked();
                    }
                    break;
                case STEP2:
                    if(null != step2ErrorCallback){
                        step2ErrorCallback.onCancelClicked();
                    }
                    break;
                case STEP3:
                    if(null != step3ErrorCallback){
                        step3ErrorCallback.onCancelClicked();
                    }
                    break;
            }
        }else if(R.id.dul_tv_retry == id){
            dulRlErrorButtons.setVisibility(View.GONE);
            switch (status){
                case STEP1:
                    if(null != step1ErrorCallback){
                        step1ErrorCallback.onRetryClicked();
                    }
                    break;
                case STEP2:
                    if(null != step2ErrorCallback){
                        step2ErrorCallback.onRetryClicked();
                    }
                    break;
                case STEP3:
                    if(null != step3ErrorCallback){
                        step3ErrorCallback.onRetryClicked();
                    }
                    break;
            }
        }else if(R.id.dul_ll_status == id){

        }
    }

    public void show(){
        loadGif(mContext, mSuccessIndicator, dulIvIndicator);
        loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep1);
        super.show();
    }

    boolean step1Result, step2Result, step3Result = false;
    boolean step1Flag,step2Flag,step3Flag = false;

    public void setStep1Result(boolean isSuccess){
        step1Result = isSuccess;
        step1Flag = true;
        if(isSuccess){
            dulRlErrorButtons.setVisibility(View.GONE);
            if(null != step1SuccessCallback){
                step1SuccessCallback.onSuccess();
            }
            loadGif(mContext, mSuccessIndicator, dulIvIndicator);
            loadGifOneTime(mContext, R.drawable.gif_uld_upload_success, dulIvStep1, new GlideUtil.GifListener() {
                @Override
                public void gifPlayComplete() {
                    setStep1ResultIcon();
                }
            });
        }else {
            status = StatusEnum.STEP1;
            dulRlErrorButtons.setVisibility(View.VISIBLE);
            loadGif(mContext, mErrorIndicator, dulIvIndicator);
            loadGifOneTime(mContext, R.drawable.gif_uld_upload_error, dulIvStep1, new GlideUtil.GifListener() {
                @Override
                public void gifPlayComplete() {
                    setStep1ResultIcon();
                }
            });
        }
    }

    public void setStep2Result(boolean isSuccess){
        step2Result = isSuccess;
        step2Flag = true;
        setStep2Finished();
        if(isSuccess){
            dulRlErrorButtons.setVisibility(View.GONE);
            if(null != step2SuccessCallback){
                step2SuccessCallback.onSuccess();
            }
            loadGif(mContext, mSuccessIndicator, dulIvIndicator);
            dulPbStep2To3.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_update_loading_success));
            loadGifOneTime(mContext, R.drawable.gif_uld_upload_success, dulIvStep2, new GlideUtil.GifListener() {
                @Override
                public void gifPlayComplete() {
                    setStep2ResultIcon();
                }
            });
        }else {
            status = StatusEnum.STEP2;
            dulRlErrorButtons.setVisibility(View.VISIBLE);
            dulPbStep1To2.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_update_loading_error));
            loadGif(mContext, mErrorIndicator, dulIvIndicator);
            loadGifOneTime(mContext, R.drawable.gif_uld_upload_error, dulIvStep2, new GlideUtil.GifListener() {
                @Override
                public void gifPlayComplete() {
                    setStep2ResultIcon();
                }
            });
        }
    }

    public void setStep3Result(boolean isSuccess){
        step3Result = isSuccess;
        step3Flag = true;
        setStep2To3Finished();
        if(isSuccess){
            dulRlErrorButtons.setVisibility(View.GONE);
            if(null != step3SuccessCallback){
                step3SuccessCallback.onSuccess();
            }
            loadGif(mContext, mSuccessIndicator, dulIvIndicator);
            loadGifOneTime(mContext, R.drawable.gif_uld_upload_success, dulIvStep3, new GlideUtil.GifListener() {
                @Override
                public void gifPlayComplete() {
                    setStep3ResultIcon();
                }
            });
        }else {
            status = StatusEnum.STEP3;
            dulRlErrorButtons.setVisibility(View.VISIBLE);
            dulPbStep2To3.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_update_loading_error));
            loadGif(mContext, mErrorIndicator, dulIvIndicator);
            loadGifOneTime(mContext, R.drawable.gif_uld_upload_error, dulIvStep3, new GlideUtil.GifListener() {
                @Override
                public void gifPlayComplete() {
                    setStep3ResultIcon();
                }
            });
        }
    }

    public void setStep1ResultIcon() {
        if(!step1Flag){
            loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep1);
            return;
        }
        if (step1Result) {
            loadBitmap(mContext, R.drawable.icon_uld_upload_success, dulIvStep1);
        } else {
            loadBitmap(mContext, R.drawable.icon_uld_upload_error, dulIvStep1);
        }
    }

    public void setStep2ResultIcon() {
        if(!step2Flag){
            loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep2);
            return;
        }
        if (step2Result) {
            loadBitmap(mContext, R.drawable.icon_uld_upload_success, dulIvStep2);
        } else {
            loadBitmap(mContext, R.drawable.icon_uld_upload_error, dulIvStep2);
        }
    }

    public void setStep3ResultIcon() {
        if(!step3Flag){
            loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep3);
            return;
        }
        if (step3Result) {
            loadBitmap(mContext, R.drawable.icon_uld_upload_success, dulIvStep3);
        } else {
            loadBitmap(mContext, R.drawable.icon_uld_upload_error, dulIvStep3);
        }
    }

    public void startStep1() {
        step1Flag = false;
        loadGif(mContext, mSuccessIndicator, dulIvIndicator);
        loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep1);
    }

    public void step1Finished() {
        int containerWidth = dulLlProgressContainer.getWidth();
        int indicatorWidth = dulIvIndicator.getWidth();
        float oneStepWidth = (containerWidth - indicatorWidth) / 2f;
        dulPbStep1To2.setProgress(100);
        dulIvIndicator.setTranslationX(oneStepWidth);
    }

    public void startStep2() {
        step2Flag = false;
        loadGif(mContext, mSuccessIndicator, dulIvIndicator);
        loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep2);
        dulPbStep1To2.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_update_loading_success));
        int containerWidth = dulLlProgressContainer.getWidth();
        int indicatorWidth = dulIvIndicator.getWidth();
        float oneStepWidth = (containerWidth - indicatorWidth) / 2f;
        if(null == oaIndicator1To2){
            oaIndicator1To2 = ObjectAnimator.ofFloat(dulIvIndicator, "translationX"
                    , 0f, oneStepWidth * loadFakeProgress, oneStepWidth * loadFakeProgress2);
            oaIndicator1To2.setDuration(2000);
        }else {
            oaIndicator1To2.cancel();
        }
        oaIndicator1To2.start();
        if(null == oaProgress1To2){
            oaProgress1To2 = ObjectAnimator.ofInt(dulPbStep1To2, "progress"
                    , 0, (int) (100 * loadFakeProgress), (int) (100 * loadFakeProgress2));
            oaProgress1To2.setDuration(2000);
        }else {
            oaProgress1To2.cancel();
        }
        oaProgress1To2.start();
    }

    public void setStep2Finished() {
        int containerWidth = dulLlProgressContainer.getWidth();
        int indicatorWidth = dulIvIndicator.getWidth();
        float oneStepWidth = (containerWidth - indicatorWidth) / 2f;
        if(null != oaIndicator1To2){
            oaIndicator1To2.cancel();
        }
        if(null != oaProgress1To2){
            oaProgress1To2.cancel();
        }
        dulPbStep1To2.setProgress(100);
        dulIvIndicator.setTranslationX(oneStepWidth);
    }

    public void startStep3() {
        step3Flag = false;
        loadGif(mContext, mSuccessIndicator, dulIvIndicator);
        loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep3);
        dulPbStep1To2.setProgress(100);
        dulPbStep2To3.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_update_loading_success));
        int containerWidth = dulLlProgressContainer.getWidth();
        int indicatorWidth = dulIvIndicator.getWidth();
        float oneStepWidth = (containerWidth - indicatorWidth) / 2f;
        if(null == oaIndicator2To3){
            oaIndicator2To3 = ObjectAnimator.ofFloat(dulIvIndicator, "translationX"
                    , oneStepWidth, oneStepWidth + oneStepWidth * loadFakeProgress
                    , oneStepWidth + oneStepWidth * loadFakeProgress2);
            oaIndicator2To3.setDuration(2000);
        }else {
            oaIndicator2To3.cancel();
        }
        oaIndicator2To3.start();
        if(null == oaProgress2To3){
            oaProgress2To3 = ObjectAnimator.ofInt(dulPbStep2To3, "progress"
                    , 0, (int) (100 * loadFakeProgress), (int) (100 * loadFakeProgress2));
            oaProgress2To3.setDuration(2000);
        }else {
            oaProgress2To3.cancel();
        }
        oaProgress2To3.start();
//        ObjectAnimator oaIndicator = ObjectAnimator.ofFloat(dulIvIndicator, "translationX", oneStepWidth, oneStepWidth + oneStepWidth * loadFakeProgress);
//        oaIndicator.setDuration(2000);
//        oaIndicator.start();
//        ObjectAnimator oaProgress = ObjectAnimator.ofInt(dulPbStep2To3, "progress", 0, (int) (100 * loadFakeProgress));
//        oaProgress.setDuration(2000);
//        oaProgress.start();
    }

    public void setStep2To3Finished() {
        int containerWidth = dulLlProgressContainer.getWidth();
        int indicatorWidth = dulIvIndicator.getWidth();
        float twoStepWidth = (containerWidth - indicatorWidth);
        if(null != oaIndicator2To3){
            oaIndicator1To2.cancel();
        }
        if(null != oaProgress2To3){
            oaProgress2To3.cancel();
        }
        dulPbStep2To3.setProgress(100);
        dulIvIndicator.setTranslationX(twoStepWidth);
    }

    public static void loadGif(Context context, Object model, final ImageView imageView){
        Glide.with(context).asGif().load(model).into(imageView);
    }



//==================================================================================================
    public static class Builder{
        public static final int TYPE_FOX = 0x00000001;
        public static final int TYPE_TIGER = 0x00000002;
        public static final int TYPE_XIAOBAO = 0x00000003;

        private Activity mContext;
        int mIndicatorType;
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

        public Builder setIndicator(int indicatorType){
            mIndicatorType = indicatorType;
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
            setIndicatorGif(dialog);
            dialog.setStep1Callback(step1SuccessCallback, step1ErrorCallback);
            dialog.setStep2Callback(step2SuccessCallback, step2ErrorCallback);
            dialog.setStep3Callback(step3SuccessCallback, step3ErrorCallback);
            return dialog;
        }

        private void setIndicatorGif(UpdateLoadingDialog dialog) {
            switch (mIndicatorType){
                case TYPE_FOX:
                    mSuccessIndicator = R.drawable.gif_uld_fox;
                    mErrorIndicator = R.drawable.gif_uld_fox;
                    break;
                case TYPE_TIGER:
                    mSuccessIndicator = R.drawable.gif_uld_tiger;
                    mErrorIndicator = R.drawable.gif_uld_tiger;
                    break;
                case TYPE_XIAOBAO:
                default:
                    mSuccessIndicator = R.drawable.gif_uld_hxb_default;
                    mErrorIndicator = R.drawable.gif_uld_hxb_error;
                    break;
            }
            dialog.setIndicator(mSuccessIndicator, mErrorIndicator);
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
