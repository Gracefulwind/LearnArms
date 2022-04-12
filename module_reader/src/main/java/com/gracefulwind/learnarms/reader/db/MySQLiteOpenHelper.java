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
    //----user----
    public static final String createUser = "CREATE TABLE IF NOT EXISTS user (" +
            "_id integer PRIMARY KEY AUTOINCREMENT" +
            ", id char(20) NOT NULL UNIQUE" +
            ", name nvarchar(20)" +
            ", token varchar(255)" +  //token长度限制？ 感觉token还是放在sp里好点？
            ", sex int" +  //1,男 2.女
            ", is_vip int" +
            ", icon char" +
            ", coin long" +
            ", gmt_create timestamp DEFAULT CURRENT_TIMESTAMP" +
            ", gmt_update timestamp DEFAULT CURRENT_TIMESTAMP" +
//        ", gmt_update timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
            ", deleted int)";
    /**
     *
     * ----|_id  |id              |name     |token      |sex  |is_vip |coin  |----
     * ----|主键 |用户             |用户名     |用户token   |性别 |是否VIP |金币数 |
     * ----|int |char20          |nvarchar20| varchar255|int  |int    |long  |
     * ----|    |NOT NULL UNIQUE |          |           |     |       |      |
     *
     * */
    public static final String createUserTrigger = "CREATE TRIGGER IF NOT EXISTS tag_user AFTER UPDATE" +
            " ON user" +
            " BEGIN" +
            " UPDATE user SET gmt_update = CURRENT_TIMESTAMP;" +
            " END;";
    //=========================
    //----book_category----
    public static final String createBookCategory = "CREATE TABLE IF NOT EXISTS book_category (" +
        "_id integer PRIMARY KEY AUTOINCREMENT" +
        ", id char(20) NOT NULL UNIQUE" +
        ", name nvarchar(20)" +
        ", gmt_create timestamp DEFAULT CURRENT_TIMESTAMP" +
        ", gmt_update timestamp DEFAULT CURRENT_TIMESTAMP" +
//        ", gmt_update timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
        ", deleted int)";
    /**
     *
     * ----|_id  |id              |name     |create_time|deleted|----
     * ----|主键 |书类id           |书类名     |
     * ----|int |char20          |nvarchar20|
     * ----|    |NOT NULL UNIQUE |          |
     *
     * */
    public static final String createBookCategoryTrigger = "CREATE TRIGGER IF NOT EXISTS tag_book_category AFTER UPDATE" +
            " ON book_category" +
            " BEGIN" +
            " UPDATE book_category SET gmt_update = CURRENT_TIMESTAMP;" +
            " END;";
    //----book_tag----
    public static final String createBookTag = "CREATE TABLE IF NOT EXISTS book_tag (" +
            "_id integer PRIMARY KEY AUTOINCREMENT" +
            ", id char(20) NOT NULL UNIQUE" +
            ", name nvarchar(20)" +
            ", gmt_create timestamp DEFAULT CURRENT_TIMESTAMP" +
            ", gmt_update timestamp DEFAULT CURRENT_TIMESTAMP" +
//        ", gmt_update timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
            ", deleted int)";
    /**
     *--------------------book_tag--------------------------------
     * ----|_id  |id              |name      |create_time|deleted|----
     * ----|主键 |书本标签id        |标签名     |          |       |----
     * ----|int |char20          |nvarchar20|          |       |----
     * ----|    |NOT NULL UNIQUE |          |          |       |----
     *
     * */
    public static final String createBookTagTrigger = "CREATE TRIGGER IF NOT EXISTS tag_book_tag AFTER UPDATE" +
            " ON book_tag" +
            " BEGIN" +
            " UPDATE book_tag SET gmt_update = CURRENT_TIMESTAMP;" +
            " END;";
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
            ", gmt_create timestamp DEFAULT CURRENT_TIMESTAMP" +
            ", gmt_update timestamp DEFAULT CURRENT_TIMESTAMP" +
//            ", gmt_update timestamp DEFAULT CURRENT_TIMESTAMP" +
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
    public static final String createBookInfoTrigger = "CREATE TRIGGER IF NOT EXISTS tag_book_info AFTER UPDATE" +
            " ON book_info" +
            " BEGIN" +
            " UPDATE book_info SET gmt_update = CURRENT_TIMESTAMP;" +
            " END;";
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
            ", gmt_create timestamp DEFAULT CURRENT_TIMESTAMP" +
            ", gmt_update timestamp DEFAULT CURRENT_TIMESTAMP" +
//        ", gmt_update timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
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
    public static final String createBookShelfTrigger = "CREATE TRIGGER IF NOT EXISTS tag_book_shelf AFTER UPDATE" +
            " ON book_shelf" +
            " BEGIN" +
            " UPDATE book_shelf SET gmt_update = CURRENT_TIMESTAMP;" +
            " END;";
    //----book_chapter----
    public static final String createBookChapter = "CREATE TABLE IF NOT EXISTS book_chapter (" +
            "_id integer PRIMARY KEY AUTOINCREMENT" +
//            ", id char(20) NOT NULL UNIQUE" +
            ", id char(20) UNIQUE" +  //这个是否是必须的？章节是否存在id？
            ", book_id char(20)" +
            ", chapter_number long" +
            ", chapter_content ntext" +
            ", gmt_create timestamp DEFAULT CURRENT_TIMESTAMP" +
            ", gmt_update timestamp DEFAULT CURRENT_TIMESTAMP" +
//        ", gmt_update timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
            ", deleted int)";
    /**
     *--------------------book_chapter--------------------------------
     * ----|_id  |id       |book_id|chapter_number|chapter_content|----
     * ----|主键 |章节id    |书本id  |章节号         |章节内容       |----
     * ----|int |char20   |char20  | long        |ntext        |----
     * ----|    |UNIQUE   |        |          |       |----
     *
     * */
    public static final String createBookChapterTrigger = "CREATE TRIGGER IF NOT EXISTS tag_book_chapter AFTER UPDATE" +
            " ON book_chapter" +
            " BEGIN" +
            " UPDATE book_chapter SET gmt_update = CURRENT_TIMESTAMP;" +
            " END;";

    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库创建
        //----user
        db.execSQL(createUser);
//        db.execSQL(createUserTrigger);
        //----book_category
        db.execSQL(createBookCategory);
//        db.execSQL(createBookCategoryTrigger);
        //----book_tag
        db.execSQL(createBookTag);
//        db.execSQL(createBookTagTrigger);
        //----book_info
        db.execSQL(createBookInfo);
//        db.execSQL(createBookInfoTrigger);
        //----book_shelf
        db.execSQL(createBookShelf);
//        db.execSQL(createBookShelfTrigger);
        //----book_chapter
        db.execSQL(createBookChapter);
//        db.execSQL(createBookChapterTrigger);
        //----
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级
    }
}
