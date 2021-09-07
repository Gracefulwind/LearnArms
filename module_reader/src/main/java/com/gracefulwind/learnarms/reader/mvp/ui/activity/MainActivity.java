package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.StringUtil;
import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.reader.widget.smart.SmartTextView;
import com.gracefulwind.learnarms.reader.widget.smart.text.SmartImageSpan;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;


//import com.gracefulwind.learnarms.module_reader.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.reader.mvp.contract.MainContract;
import com.gracefulwind.learnarms.reader.mvp.presenter.MainPresenter;
import com.gracefulwind.learnarms.reader.R;
import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;
import org.xml.sax.XMLReader;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * @ClassName: MainActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/8/27
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/8/27
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.HOME_ACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R2.id.ram_tv_clcik1)
    TextView ramBtnClick1;
    @BindView(R2.id.ram_tv_clcik2)
    Button ramBtnClick2;

    @BindView(R2.id.ram_et_test0)
    EditText ramEtTest0;
    @BindView(R2.id.ram_stv_test1)
    SmartTextView ramStvTest1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.reader_activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
//        return 0; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

//     return R.layout.reader_activity_main;


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        try{
//            this.setContentView(R.layout.reader_activity_main);
//            Unbinder mUnbinder = ButterKnife.bind(this);
//        }catch (Exception e){
//            if (e instanceof InflateException) {
//                throw e;
//            }
//            e.printStackTrace();
//        }

        //setToolBar(toolbar, "Main");
        initListener();
    }

    public void initListener() {

    }

    @Override
    public void showMessage(@NonNull @NotNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

//==================================================================================================
    @OnClick({R2.id.ram_tv_clcik1, R2.id.ram_tv_clcik2, R2.id.ram_tv_clcik3, R2.id.ram_tv_clcik4, R2.id.ram_tv_clcik5})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.ram_tv_clcik1 == id){
            Editable baseText = ramEtTest0.getText();
            Editable targetText = ramStvTest1.getText();
            if(StringUtil.isEmpty(baseText.toString())){
                targetText.append("testetttttttttt=========================testeeeteeteee\r\n");
            }else {
                targetText.append(baseText);
            }
//            targetText = "testetttttttttt=========================testeeeteeteee\r\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\ntestetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n" +
//                    "testetttttttttt=========================testeeeteeteee\n";
//            ramEtTest0.setText(targetText);
            ramStvTest1.setText(targetText);
        }else if (R.id.ram_tv_clcik2 == id){
            //
            System.out.println("=========================");
            String stvStr = ramStvTest1.getText().toString();
            Editable editText = ramStvTest1.getText();
            int selectionStart = Selection.getSelectionStart(editText);
            int selectionEnd = Selection.getSelectionEnd(editText);
            editText.append("\r\n");
            ramStvTest1.setText(editText);
            System.out.println("=========================");
        }else if (R.id.ram_tv_clcik3 == id){
            Editable editText = ramStvTest1.getText();
            //如何区分未获取焦点时和光标在0,0处？
            int selectionStart = Selection.getSelectionStart(editText);
            int selectionEnd = Selection.getSelectionEnd(editText);
            if(!ramStvTest1.hasFocus()){
                selectionStart = selectionEnd = editText.length();
            }
//            setImageWithHtml(editText, selectionStart, selectionEnd);
            setImageWithSpan(editText, selectionStart, selectionEnd);
//            ramStvTest1.setMovementMethod(LinkMovementMethod.getInstance());
            ramStvTest1.setText(editText);
            ramStvTest1.setSelection(selectionStart + 1);
        }else if (R.id.ram_tv_clcik4 == id){
            Editable editText = ramStvTest1.getText();
            //如何区分未获取焦点时和光标在0,0处？
            int selectionStart = Selection.getSelectionStart(editText);
            int selectionEnd = Selection.getSelectionEnd(editText);
            if(!ramStvTest1.hasFocus()){
                selectionStart = selectionEnd = editText.length();
            }
            setImageWithHtml(editText, selectionStart, selectionEnd);
//            setImageWithSpan(editText, selectionStart, selectionEnd);
            ramStvTest1.setText(editText);
            ramStvTest1.setSelection(selectionStart + 1);
        }else if (R.id.ram_tv_clcik5 == id){
            System.out.println("===========================");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                rebuildSpan();
            }
            System.out.println("===========================");
            System.out.println("===========================");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void rebuildSpan() {
        Editable editText = ramStvTest1.getText();
        String s = Html.toHtml(editText);
        Spanned spanned = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = getResources().getDrawable(R.drawable.test_span);
                float v = UiUtil.sp2px(15);
                drawable.setBounds(0, 0, (int) v, (int) v);
                System.out.println("====");
                return drawable;
            }
        }, new Html.TagHandler() {
            /**
             * opening:标签头还是尾
             * tag:tag,现在的问题是如何在toHtml中将自定义span转为自定义的tag？
             * */
            @Override
            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
//                if (!"drawable".equalsIgnoreCase(tag) || tag == null || isFirst) {
//                    return;
//                }
//                InsetDrawable insetDrawable = new InsetDrawable(mIconDrawable, 0, 0, (int) dipRight, (int) dipBottom);
//                insetDrawable.setBounds(0, 0, textSize, textSize);
//
//                isFirst = true;
                int len = output.length();
                output.append("\uFFFC");
                Drawable drawable = getResources().getDrawable(R.drawable.test_span);
                float v = UiUtil.sp2px(15);
                drawable.setBounds(0,0, (int)v, (int)v);
                ImageSpan imageSpan = new ImageSpan(drawable, "/test_span", ImageSpan.ALIGN_BASELINE);
//                //设置自定义ImageSpan
//                output.setSpan(new MyImageSpan(insetDrawable), len, output.length(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });
        ramStvTest1.setText(spanned);
    }

    String testHtmlStr = "<img src=\"/test_span\" align=\"baseline\" width=\"304\" height=\"228\">";

    /**
     * 这个可能没有意义了，Html源码中也是转成imageSpan来处理，同样也存在信息丢失的问题。。。有没办法存储路径呢
     * 三方软件用的也是“\uFFFC”来代替的，obj，那么他们是如何存储路径/唯一标识的呢？
     * span里存在keyValue，这个怎么存储的？
     * */
    private void setImageWithHtml(Editable editText, int selectionStart, int selectionEnd) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            editText.replace(selectionStart, selectionEnd, Html.fromHtml(testHtmlStr
                    , Html.FROM_HTML_MODE_LEGACY, new Html.ImageGetter() {
                        @Override
                        public Drawable getDrawable(String source) {
                            Drawable drawable = getResources().getDrawable(R.drawable.test_span);
                            float v = UiUtil.sp2px(15);
                            drawable.setBounds(0,0, (int)v, (int)v);
                            System.out.println("====");
                            return drawable;
                        }
                    }, null));
        }else {
            editText.replace(selectionStart, selectionEnd, Html.fromHtml(testHtmlStr));
        }
    }

    private void setImageWithSpan(Editable editText, int selectionStart, int selectionEnd) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("\uFFFC");
        Drawable drawable = getResources().getDrawable(R.drawable.test_span);
        float v = UiUtil.sp2px(15);
        drawable.setBounds(0,0, (int)v, (int)v);
        SmartImageSpan imageSpan = new SmartImageSpan(drawable, "/test_span", ImageSpan.ALIGN_BASELINE);
        imageSpan.getSource();
        ssb.setSpan(imageSpan, 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.replace(selectionStart, selectionEnd, ssb);
    }


    String h5Text = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head> \n" +
            "<meta charset=\"utf-8\"> \n" +
            "<title>菜鸟教程(runoob.com)</title> \n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<p>\n" +
            "<a href=\"/index.html\">本文本</a> 是一个指向本网站中的一个页面的链接。</p>\n" +
            "\n" +
            "<p><a href=\"//www.microsoft.com/\">本文本</a> 是一个指向万维网上的页面的链接。</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    String h5TextImg = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <title>菜鸟教程(runoob.com)</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<h2>Norwegian Mountain Trip</h2>\n" +
            "<img border=\"0\" src=\"/images/pulpit.jpg\" alt=\"Pulpit rock\" width=\"304\" height=\"228\">\n" +
            "\n" +
            "</body>\n" +
            "</html>";


}