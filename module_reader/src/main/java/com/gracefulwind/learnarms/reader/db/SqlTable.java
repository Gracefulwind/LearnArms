package com.gracefulwind.learnarms.reader.db;

/**
 * @ClassName: SqlTable
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/28
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/28
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Deprecated
public class SqlTable {
    public static final String createBookInfo = "create table book_info (_id integer primary key autoincrement, " +
                    "id char(20) not null unique, name nchar(20), author nchar(20), kind char(20), origin text, update_time char(20)" +
                    ", last_chapter text)";
}
