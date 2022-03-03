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

//=================================================================================================================================
    //----book_category----
    public static final String createBookCategory = "CREATE TABLE IF NOT EXISTS book_category (" +
        "_id INTEGER PRIMARY KEY AUTOINCREMENT" +
        ", id CHAR(20) NOT NULL UNIQUE" +
        ", name NCHAR(20)" +
        ", create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
//        ", update_time timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
        ", deleted INT)";
    /**
     *
     * ----|_id  |id              |name     |create_time|deleted|----
     * ----|主键 |书类id           |书类名     |
     * ----|int |char20          |nvarchar20|
     * ----|    |NOT NULL UNIQUE |          |
     *
     * */
    //----book_tag----
    public static final String createBookTag = "CREATE TABLE IF NOT EXISTS book_tag (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", id CHAR(20) NOT NULL UNIQUE" +
            ", name NVARCHAR(20)" +
            ", create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
//        ", update_time timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
            ", deleted INT)";
    /**
     *--------------------book_tag--------------------------------
     * ----|_id  |id              |name      |create_time|deleted|----
     * ----|主键 |书本标签id        |标签名     |          |       |----
     * ----|int |char20          |nvarchar20|          |       |----
     * ----|    |NOT NULL UNIQUE |          |          |       |----
     *
     * */
    //----book_info----
    public static final String createBookInfo = "CREATE TABLE IF NOT EXISTS book_info (" +
            "_id integer PRIMARY KEY AUTOINCREMENT" +
            ", id char(20) NOT NULL UNIQUE" +
            ", name nvarchar(20)" +
            ", author nvarchar(20)" +
            ", category_id char(20)" + //category表
            ", tag_id char(20)" +  //type表
//            ", kind CHAR(20)" +
            ", origin varchar(255)" +
            ", read_count long" +
            ", store_count long" +
            ", positive_rating decimal" +
            ", last_chapter nvarchar(255)" +
            ", last_update_time nvarchar(20)" +
            ", create_time timestamp DEFAULT CURRENT_TIMESTAMP" +
//            ", update_time timestamp DEFAULT CURRENT_TIMESTAMP" +
            ", deleted int)";
    /**
     *
     * --------------------book_info------------------------------
     * ----|_id  |id              |name      |author    |category_id|tag_id|origin    |read_count|store_count|positive_rating|last_chapter|last_update_time|----
     * ----|主键 |书本标签id        |标签名     |作者       |分类id      |标签id |来源       |阅读次数    |收藏数      |好评率          |最新章节(简述)|最新章节发布时间   |----
     * ----|int |char20          |nvarchar20|nvarchar20|char20      |char20|varchar255|long      |long       |decimal        |nvarchar255|last_update_time|----
     * ----|    |NOT NULL UNIQUE |          |          |       |----
     * ----
     * last_update_time 考虑用timedate？
     * */
    //----book_shelf----
    public static final String createBookShelf = "CREATE TABLE IF NOT EXISTS book_shelf (" +
            "_id integer PRIMARY KEY AUTOINCREMENT" +
            ", id char(20) NOT NULL UNIQUE" +   //感觉用不到？
            ", user_id char(20)" +
            ", book_id char(20)" +
            ", current_chapter long" +      //是否 需要当前章节id？
//            ", current_chapter long" +      //当前章节号
            ", current_progress long" +
            ", is_local int" +
            ", is_advertisement int" +
            ", create_time timestamp DEFAULT CURRENT_TIMESTAMP" +
//        ", update_time timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
            ", deleted int)";
    /**
     *--------------------book_shelf--------------------------------
     * ----|_id  |id              |user_id|book_id |current_chapter|current_progress     |is_local  |is_advertisement|----
     * ----|主键 |书架id           |用户id  |书本id   |当前章节号       |当前观看进度(utf编码字数)|是否本地缓存|是否是广告        |----
     * ----|int |char20          |char20 |char20  |               |  long                |int      |int             |----
     * ----|    |NOT NULL UNIQUE |       |        |               |----
     *
     * 书架id关联下user表
     * */
    //----book_chapter----
    public static final String createBookChapter = "CREATE TABLE IF NOT EXISTS book_chapter (" +
            "_id integer PRIMARY KEY AUTOINCREMENT" +
//            ", id char(20) NOT NULL UNIQUE" +
            ", id char(20) UNIQUE" +  //这个是否是必须的？章节是否存在id？
            ", book_id char(20)" +
            ", chapter_number long" +
            ", chapter_content ntext" +
            ", create_time timestamp DEFAULT CURRENT_TIMESTAMP" +
//        ", update_time timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
            ", deleted int)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库创建
        //----book_category
        db.execSQL(createBookCategory);
        //----book_tag
        db.execSQL(createBookTag);
        //----book_info
        db.execSQL(createBookInfo);
        //----book_shelf
        db.execSQL(createBookShelf);
        //----book_chapter
        db.execSQL(createBookChapter);
        //----
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级
    }
}
