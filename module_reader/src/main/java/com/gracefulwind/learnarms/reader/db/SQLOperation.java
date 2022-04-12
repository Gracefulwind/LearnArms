package com.gracefulwind.learnarms.reader.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * @ClassName: SQLOpration
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/25
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/25
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class SQLOperation {

    public static void addRandomData(SQLiteDatabase db){
        if(!db.isOpen() || db.isReadOnly()){
            return;
        }
        String sql = "";
        db.beginTransaction();
//        db.query()
//        db.insert(sql);
//        db.in
    }

}
