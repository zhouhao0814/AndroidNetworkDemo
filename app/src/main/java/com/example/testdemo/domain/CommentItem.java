package com.example.testdemo.domain;

/**
 * author:tdz
 * email:tdz@geniatech.com
 * created:2020/4/23 20:54
 */
public class CommentItem {
    public CommentItem(String articleId,String commentContent) {
        this.articleId = articleId;
        this.commentContent = commentContent;
    }
    /**
     * articleId : 234123
     * commentContent : 这是评论内容
     */
    
    private String articleId;
    private String commentContent;
    
    public String getArticleId() {
        return articleId;
    }
    
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
    
    public String getCommentContent() {
        return commentContent;
    }
    
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
