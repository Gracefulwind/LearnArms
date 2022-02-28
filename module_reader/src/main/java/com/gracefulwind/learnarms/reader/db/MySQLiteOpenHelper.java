package com.gracefulwind.learnarms.reader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @ClassName: MySQLiteOpenHelper
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/24
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/24
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "reader.db";
    private static final int VERSION = 1;

    public MySQLiteOpenHelper(@Nullable @org.jetbrains.annotations.Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public MySQLiteOpenHelper(@Nullable @org.jetbrains.annotations.Nullable Context context, @Nullable @org.jetbrains.annotations.Nullable String name, @Nullable @org.jetbrains.annotations.Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库创建
        String sql = "--- \r\n" +
                "create table book_info (_id integer primary key autoincrement, " +
                "id char(20) not null unique, name nchar(20), author nchar(20), kind char(20), origin text, update_time char(20)" +
                ", last_chapter text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级
    }
}
