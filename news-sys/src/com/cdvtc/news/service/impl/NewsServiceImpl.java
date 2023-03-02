package com.cdvtc.news.service.impl;

import com.cdvtc.news.dao.NewsDao;
import com.cdvtc.news.dao.Page;
import com.cdvtc.news.dao.impl.NewsDaoImpl;
import com.cdvtc.news.model.News;
import com.cdvtc.news.service.NewsService;

import java.util.List;
import java.util.Map;

public class NewsServiceImpl implements NewsService {
    private NewsDao newsDao = new NewsDaoImpl();

    @Override
    public List<News> getNewsByCategory(Integer categoryId) {
        return newsDao.getNewsByCategory(categoryId);
    }

    @Override
    public List<News> getNewsByTag(Integer tagId) {
        return newsDao.getNewsByTag(tagId);
    }

    @Override
    public List<News> getStickNews(int limit) {
        return newsDao.getStickNews(limit);
    }

    @Override
    public List<News> getAllNews() {
        return newsDao.getAllNews();
    }

    @Override
    public Integer addNews(News news) {
        return newsDao.addNews(news);
    }

    @Override
    public void updateNews(News news) {
        newsDao.updateNews(news);
    }

    @Override
    public News getNewsById(int newsId) {
        return newsDao.getNewsById(newsId);
    }

    @Override
    public List<News> getHotNews() {
        return newsDao.getHotNews();
    }

    @Override
    public List<News> getRecommendedNews(int newsId) {
        /**推荐算法说明：
        （1）使用随机推荐，并且限制为5条
        （2）暂未使用新闻自身的标题、标签、内容等信息（在实际中可使用ES内容检索及AI推荐算法，可自行研究）**/
        return newsDao.getRecommendedNews(newsId);
    }

    @Override
    public void updateClickCount(int newsId) {
        newsDao.updateClickCount(newsId);
    }

    @Override
    public List<Map<String, Object>> statNewsCountByCategory() {
        return newsDao.statNewsCountByCategory();
    }

    @Override
    public List<Map<String, Object>> statNewsCountByTag() {
        return newsDao.statNewsCountByTag();
    }

    @Override
    public List<Map<String, Object>> statNewsCommentCountByDate() {
        return newsDao.statNewsCommentCountByDate();
    }

    @Override
    public List<Map<String, Object>> statNewsCommentCountByUser() {
        return newsDao.statNewsCommentCountByUser();
    }

    @Override
    public Page<News> getPagedNews(int pageNumer, int pageSize, String key) {
        return newsDao.getPagedNews(pageNumer, pageSize, key);
    }
}
