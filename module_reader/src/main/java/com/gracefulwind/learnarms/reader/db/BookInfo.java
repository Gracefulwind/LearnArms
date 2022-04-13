package com.gracefulwind.learnarms.reader.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @ClassName: BookInfo
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/24
 * @Description: ---------------------------
 * 利用GreenDao创建数据库
 * 优点:可视化，方便操作
 * 缺点:数据库升级时会删库重建
 * @UpdateUser:
 * @UpdateDate: 2022/2/24
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Entity
public class BookInfo {
//    @primary
    @Id(autoincrement = true)
    long id;
    @Unique
    @NotNull
    String uid;
    String name;
    String author;
    String kind;
    String origin;
    String updateTime;
    String lastChapter;
    String coverUrl;
    long chapterNumber;
    String bookDesc;
    int state;
    int delete = 0;
    @Generated(hash = 944831075)
    public BookInfo(long id, @NotNull String uid, String name, String author,
            String kind, String origin, String updateTime, String lastChapter,
            String coverUrl, long chapterNumber, String bookDesc, int state,
            int delete) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.author = author;
        this.kind = kind;
        this.origin = origin;
        this.updateTime = updateTime;
        this.lastChapter = lastChapter;
        this.coverUrl = coverUrl;
        this.chapterNumber = chapterNumber;
        this.bookDesc = bookDesc;
        this.state = state;
        this.delete = delete;
    }
    @Generated(hash = 1952025412)
    public BookInfo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getKind() {
        return this.kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getOrigin() {
        return this.origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getLastChapter() {
        return this.lastChapter;
    }
    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }
    public String getCoverUrl() {
        return this.coverUrl;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public long getChapterNumber() {
        return this.chapterNumber;
    }
    public void setChapterNumber(long chapterNumber) {
        this.chapterNumber = chapterNumber;
    }
    public String getBookDesc() {
        return this.bookDesc;
    }
    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getDelete() {
        return this.delete;
    }
    public void setDelete(int delete) {
        this.delete = delete;
    }
}
