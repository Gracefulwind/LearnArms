package com.gracefulwind.learnarms.reader.mvp.ui.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.BaseLazyLoadFragment;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.app.AppLifecyclesImpl;
import com.gracefulwind.learnarms.reader.greendao.BookInfoDao;
import com.gracefulwind.learnarms.reader.greendao.DaoMaster;
import com.gracefulwind.learnarms.reader.greendao.DaoSession;
import com.gracefulwind.learnarms.reader.mvp.contract.BookActivityContract;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerBookActivityComponent;
import com.gracefulwind.learnarms.reader.mvp.presenter.BookActivityPresenter;
import com.gracefulwind.learnarms.reader.db.MySQLiteOpenHelper;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: BookActivityFragment
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/22
 * @Description: ---------------------------
 * 先用来测试下数据库
 * @UpdateUser:
 * @UpdateDate: 2022/2/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.BOOK_ACTIVITY_FRAGMENT)
public class BookActivityFragment extends BaseLazyLoadFragment<BookActivityPresenter> implements BookActivityContract.View{

    @BindView(R2.id.rfba_btn_test)
    Button rfbaBtnTest;
    @BindView(R2.id.rfba_btn_add)
    Button rfbaBtnAdd;
    @BindView(R2.id.rfba_btn_delete)
    Button rfbaBtnDelete;
    @BindView(R2.id.rfba_btn_change)
    Button rfbaBtnChange;
    @BindView(R2.id.rfba_btn_read)
    Button rfbaBtnRead;
    //-------------------
    @BindView(R2.id.rfba_et_test)
    EditText rfbaEtTest;
    @BindView(R2.id.rfba_et_add)
    EditText rfbaEtAdd;
    @BindView(R2.id.rfba_et_delete)
    EditText rfbaEtDelete;
    @BindView(R2.id.rfba_et_change)
    EditText rfbaEtChange;
    @BindView(R2.id.rfba_et_read)
    EditText rfbaEtRead;


    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerBookActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.reader_fragment_book_activity;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        System.out.println("====");
        System.out.println("====");
    }

    @Override
    public void lazyLoadData() {
        //loadData
        TextView view = mRootView.findViewById(R.id.test);
        view.setText(view.getText() + "\r\n" + this.toString());
    }

    @OnClick({R2.id.rfba_btn_test, R2.id.rfba_btn_add, R2.id.rfba_btn_delete, R2.id.rfba_btn_change, R2.id.rfba_btn_read})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.rfba_btn_test == id){
            MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(this.getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

//            db.insert();
        }else if (R.id.rfba_btn_add == id){
            DaoSession daoSession = AppLifecyclesImpl.getDaoSession();
            BookInfoDao bookInfoDao = daoSession.getBookInfoDao();
            
//            bookInfoDao.readEntity()
//            new AbstractDaoMaster()
        }else if (R.id.rfba_btn_test == id){

        }
    }

//    @Override
//    public void insertData(Thing s) {
//        DaoSession daoSession = ((AserbaoApplication) getApplication()).getDaoSession();
//        for (int i = 0; i < 1000; i++) {
//            Student student = new Student();
//            student.setStudentNo(i);
//            int age = mRandom.nextInt(10) + 10;
//            student.setAge(age);
//            student.setTelPhone(RandomValue.getTel());
//            String chineseName = RandomValue.getChineseName();
//            student.setName(chineseName);
//            if (i % 2 == 0) {
//                student.setSex("男");
//            } else {
//                student.setSex("女");
//            }
//            student.setAddress(RandomValue.getRoad());
//            student.setGrade(String.valueOf(age % 10) + "年纪");
//            student.setSchoolName(RandomValue.getSchoolName());
//            daoSession.insert(student);
//        }
//    }

}
