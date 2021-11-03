package com.gracefulwind.learnarms.write.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.write.R;
import com.gracefulwind.learnarms.write.R2;

import org.jetbrains.annotations.NotNull;

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

    private Context mContext;
    @DrawableRes int mIndicator;
    //step 1
    OnStepSuccessCallback step1SuccessCallback;
    OnStepErrorCallback step1ErrorCallback;
    //step 2
    OnStepSuccessCallback step2SuccessCallback;
    OnStepErrorCallback step2ErrorCallback;
    //step 3
    OnStepSuccessCallback step3SuccessCallback;
    OnStepErrorCallback step3ErrorCallback;

    @BindView(R2.id.dul_iv_indicator)
    ImageView dulIvIndicator;

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
    }

    public void setIndicator(int indicator) {
        mIndicator = indicator;

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

//==================================================================================================
    public static class Builder{

        private Activity mContext;
        @DrawableRes int mIndicator;
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

        public Builder setIndicator(@DrawableRes int indicator){
            mIndicator = indicator;
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
            dialog.setIndicator(mIndicator);
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
