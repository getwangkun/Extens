package com.cdvtc.news.service.impl;

import com.cdvtc.news.dao.TagDao;
import com.cdvtc.news.dao.impl.TagDaoImpl;
import com.cdvtc.news.model.Tag;
import com.cdvtc.news.service.TagService;

import java.util.List;
import java.util.Set;

public class TagServiceImpl implements TagService {
    private TagDao tagDao = new TagDaoImpl();

    @Override
    public List<Tag> getAllTags() {
        return tagDao.getAllTags();
    }

    @Override
    public Set<Tag> getTagsByNewsId(int newsId) {
        return tagDao.getTagsByNewsId(newsId);
    }

    @Override
    public void addTagsForNews(int newsId, Set<Integer> tagIds) {
        for(Integer tagId: tagIds){
            if(!tagDao.isNewsTagExisted(newsId,tagId)){ //不存在该组合才存库
                tagDao.addTagForNews(newsId, tagId);
            }
        }
    }

    @Override
    public void updateTagsForNews(int newsId, Set<Integer> tagIds) {
       // 算法简要思路：先删除所有，再重新增加
        //删除新闻的所有标签
        tagDao.deleteTagsForNews(newsId);
        //增加新闻标签
        this.addTagsForNews(newsId, tagIds);
    }
}
