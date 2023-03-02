package com.cdvtc.news.service.impl;

import com.cdvtc.news.dao.UserDao;
import com.cdvtc.news.dao.impl.UserDaoImpl;
import com.cdvtc.news.model.User;
import com.cdvtc.news.service.UserService;

import java.util.List;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger("com.cdvtc.news");

    private UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String account, String password) {
        logger.info("账户【"+account+"】登陆...");
        return userDao.login(account, password);
    }

    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public boolean isUserExisted(String key, String value) {
        return userDao.isUserExisted(key, value);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void forbiddenUser(int userId, boolean forbidden) {
        userDao.forbiddenUser(userId, forbidden);
    }

    @Override
    public void updatePassword(String account, String password) {
        userDao.updatePassword(account, password);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
