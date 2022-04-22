package com.gracefulwind.learnarms.reader.entity;

import org.greenrobot.greendao.annotation.Id;

/**
 * @ClassName: BookRecordBean
 * @Author: Gracefulwind
 * @CreateDate: 2022/4/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/4/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class BookRecordBean {
    //所属的书的id
    @Id
    private String bookId;
    //阅读到了第几章
    private int chapter;
    //当前的页码
    private int pagePos;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPagePos() {
        return pagePos;
    }

    public void setPagePos(int pagePos) {
        this.pagePos = pagePos;
    }
}
