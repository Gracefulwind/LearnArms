package com.gracefulwind.learnarms.reader.entity;

import org.greenrobot.greendao.annotation.Id;

/**
 * @ClassName: AuthorBean
 * @Author: Gracefulwind
 * @CreateDate: 2022/4/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/4/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class AuthorBean {
    /**
     * _id : 553136ba70feaa764a096f6f
     * avatar : /avatar/26/eb/26ebf8ede76d7f52cd377960bd66383b
     * nickname : 九歌
     * activityAvatar :
     * type : normal
     * lv : 8
     * gender : female
     */
    @Id
    private String _id;

    private String avatar;
    private String nickname;
    private String activityAvatar;
    private String type;
    private int lv;
    private String gender;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getActivityAvatar() {
        return activityAvatar;
    }

    public void setActivityAvatar(String activityAvatar) {
        this.activityAvatar = activityAvatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
