package com.cdvtc.news.service.impl;

import com.cdvtc.news.dao.CommentDao;
import com.cdvtc.news.dao.impl.CommentDaoImpl;
import com.cdvtc.news.model.Comment;
import com.cdvtc.news.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao = new CommentDaoImpl();

    @Override
    public void addComment(Comment comment) {
        commentDao.addComment(comment);
    }

    @Override
    public List<Comment> getCommentsByNewsId(int newsId) {
        List<Comment> rows = commentDao.getCommentsByNewsId(newsId);
        //处理数据
        Map<Long, Comment> commentMap = new ConcurrentHashMap<>(); //保存数据，方便查询，去重 ，使用ConcurrentHashMap是为了解决线程安全问题，防止引发ConcurrentModificationException
        for(Comment comment: rows) {
            commentMap.put(comment.getId(), comment);
        }

        //处于所有关联
        for(Comment comment: rows) {
            if(comment.getReplyId() > 0) {
                List<Comment> replies = commentMap.get(comment.getId()).getReplies();  //注意:这里必须要以Map中数据为准（List中同一Comment对象可能存在多个）
                if(replies == null) {
                    replies = new ArrayList<>();
                    commentMap.get(comment.getId()).setReplies(replies); //注意：存入Map中的唯一对象
                }
                replies.add(commentMap.get(comment.getReplyId())); // 加入到回复列表中
            }
        }

        //移除回复的评论（只保留只评价）
        for (Long id: commentMap.keySet()) {
            Comment comment = commentMap.get(id);
            if(comment.getReplyFor().getId() > 0) {
                commentMap.remove(comment.getId());
            }
        }

        //生成最终结果数据
        List<Comment> comments = new ArrayList<>();
        for(Long id: commentMap.keySet()) {
            comments.add(commentMap.get(id));
        }

        return comments;
    }

    @Override
    public int updateLikeNum(int commentId, boolean like) {
        return commentDao.updateLikeNum(commentId, like);
    }

    @Override
    public int updateDislikeNum(int commentId, boolean dislike) {
        return commentDao.updateDislikeNum(commentId, dislike);
    }

    @Override
    public boolean deleteComment(long commentId) {
        this.commentDao.hiddenComment(commentId, true);
        return true;
    }

    @Override
    public boolean recoverComment(long commentId) {
        this.commentDao.hiddenComment(commentId, false);
        return true;
    }
}
