package com.gracefulwind.learnarms.reader.entity;

import java.util.List;

/**
 * @ClassName: DetailBean
 * @Author: Gracefulwind
 * @CreateDate: 2022/4/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/4/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class DetailBean<T> {
    private T detail;
    private List<CommentBean> bestComments;
    private List<CommentBean> comments;

    public DetailBean(T details, List<CommentBean> bestComments, List<CommentBean> comments) {
        this.detail = details;
        this.bestComments = bestComments;
        this.comments = comments;
    }

    public T getDetail() {
        return detail;
    }

    public List<CommentBean> getBestComments() {
        return bestComments;
    }

    public List<CommentBean> getComments() {
        return comments;
    }
}
