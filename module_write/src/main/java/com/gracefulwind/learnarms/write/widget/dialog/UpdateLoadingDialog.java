package com.gracefulwind.learnarms.write.widget.dialog;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.commonsdk.utils.GlideUtil;
import com.gracefulwind.learnarms.write.R;
import com.gracefulwind.learnarms.write.R2;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gracefulwind.learnarms.commonsdk.utils.GlideUtil.loadBitmap;
import static com.gracefulwind.learnarms.commonsdk.utils.GlideUtil.loadGif;
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

    private static final int BASE_PROGRESS_LENGTH = 100;
    private static final int LOAD_FAKE_PROGRESS = (int) (0.85f * BASE_PROGRESS_LENGTH);
    private static final int LOAD_FAKE_PROGRESS_2 = (int) (0.95f * BASE_PROGRESS_LENGTH);
    private static final int PROGRESS_DURATION_TIME = 2000;
    private ValueAnimator vaProgress1To2;
    private ValueAnimator vaProgress2To3;

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
    @BindView(R2.id.dul_s_indicator_weight_left)
    Space dulSIndicatorWeightLeft;
    @BindView(R2.id.dul_s_indicator_weight_right)
    Space dulSIndicatorWeightRight;
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
        setContentView(R.layout.dialog_update_loading);
        ButterKnife.bind(this, this);
        status = StatusEnum.STEP1;

    }

    public void setIndicator(@DrawableRes int successIndicator, @DrawableRes int errorIndicator) {
        mSuccessIndicator = successIndicator;
        mErrorIndicator = errorIndicator;
        loadGif(mContext, successIndicator, dulIvIndicator);
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
            //do nothing
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
            //do nothing
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
        setStep1Finished();
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

    public void setStep0(){
        setUploadingProgress(0);
    }

    public void startStep1() {
        step1Flag = false;
        loadGif(mContext, mSuccessIndicator, dulIvIndicator);
        loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep1);
        setStep0();
    }

    public void setStep1Finished(){
        setUploadingProgress(0);
    }

    public void startStep2() {
        step2Flag = false;
        //set step1
        loadBitmap(mContext, R.drawable.icon_uld_upload_success, dulIvStep1);
        //set step2
        loadGif(mContext, mSuccessIndicator, dulIvIndicator);
        loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep2);
        dulPbStep1To2.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_update_loading_success));
        //set step3
        loadBitmap(mContext, R.drawable.gif_uld_upload_ing, dulIvStep3);
        //setIndicator Animator
        setStep1Finished();
        if(null == vaProgress1To2){
            vaProgress1To2 = ValueAnimator.ofInt(0, LOAD_FAKE_PROGRESS, LOAD_FAKE_PROGRESS_2);
            vaProgress1To2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    setUploadingProgress(animatedValue);
                }
            });
            vaProgress1To2.setDuration(PROGRESS_DURATION_TIME);
        }else {
            vaProgress1To2.cancel();
        }
        vaProgress1To2.start();
    }

    public void setStep2Finished() {
        if (null != vaProgress1To2) {
            vaProgress1To2.cancel();
        }
        setUploadingProgress(BASE_PROGRESS_LENGTH);

    }

    public void startStep3() {
        step3Flag = false;
        //set step1
        loadBitmap(mContext, R.drawable.icon_uld_upload_success, dulIvStep1);
        //set step2
        loadBitmap(mContext, R.drawable.icon_uld_upload_success, dulIvStep2);
//        dulPbStep1To2.setProgress(100);
        //set step3
        loadGif(mContext, mSuccessIndicator, dulIvIndicator);
        loadGif(mContext, R.drawable.gif_uld_upload_ing, dulIvStep3);
        dulPbStep2To3.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_update_loading_success));
        //=====================
        setStep2Finished();
        if(null == vaProgress2To3){
            vaProgress2To3 = ValueAnimator.ofInt(0, LOAD_FAKE_PROGRESS, LOAD_FAKE_PROGRESS_2);
            vaProgress2To3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    setUploadingProgress(BASE_PROGRESS_LENGTH + animatedValue);
                }
            });
            vaProgress2To3.setDuration(PROGRESS_DURATION_TIME);
        }else {
            vaProgress2To3.cancel();
        }
        vaProgress2To3.start();
//        //=====================
//        if(null == oaProgress2To3){
//            oaProgress2To3 = ObjectAnimator.ofInt(dulPbStep2To3, "progress"
//                    , 0, (int) (100 * loadFakeProgress), (int) (100 * loadFakeProgress2));
//            oaProgress2To3.setDuration(2000);
//        }else {
//            oaProgress2To3.cancel();
//        }
//        oaProgress2To3.start();
    }

    public void setStep2To3Finished() {
        if (null != vaProgress2To3) {
            vaProgress2To3.cancel();
        }
//        if (null != oaProgress2To3) {
//            oaProgress2To3.cancel();
//        }
        setUploadingProgress(2 * BASE_PROGRESS_LENGTH);
    }

    private void setUploadingProgress(int progress) {
        setIndicatorProgress(progress);
        setProgressProgress(progress);
    }

    private void setIndicatorProgress(int progress) {
        setIndicatorWeight(dulSIndicatorWeightLeft, progress);
        setIndicatorWeight(dulSIndicatorWeightRight, 2 * BASE_PROGRESS_LENGTH - progress);
    }

    private void setIndicatorWeight(View dulIvIndicatorWeightLeft, float i) {
        LinearLayout.LayoutParams lpLeft = (LinearLayout.LayoutParams) dulIvIndicatorWeightLeft.getLayoutParams();
        lpLeft.weight = i;
        dulIvIndicatorWeightLeft.setLayoutParams(lpLeft);
    }

    private void setProgressProgress(int progress){
        if(0 > progress){
            return;
        }else if(BASE_PROGRESS_LENGTH >= progress){
            dulPbStep1To2.setProgress(progress);
            dulPbStep2To3.setProgress(0);
        }else if((BASE_PROGRESS_LENGTH * 2) >= progress){
            dulPbStep1To2.setProgress(BASE_PROGRESS_LENGTH);
            dulPbStep2To3.setProgress(progress - BASE_PROGRESS_LENGTH);
        }else {
            dulPbStep1To2.setProgress(BASE_PROGRESS_LENGTH);
            dulPbStep2To3.setProgress(BASE_PROGRESS_LENGTH);
        }
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
