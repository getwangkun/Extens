package com.cdvtc.news.service.impl;

import com.cdvtc.news.dao.CategoryDao;
import com.cdvtc.news.dao.impl.CategoryDaoImpl;
import com.cdvtc.news.model.Category;
import com.cdvtc.news.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }
}
