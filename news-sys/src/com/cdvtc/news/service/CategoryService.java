package com.cdvtc.news.service;

import com.cdvtc.news.model.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 获取所有新闻分类
     * @return
     */
    List<Category> getAllCategories();
}
