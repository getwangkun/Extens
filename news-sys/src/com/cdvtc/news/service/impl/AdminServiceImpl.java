package com.cdvtc.news.service.impl;

import com.cdvtc.news.dao.AdminDao;
import com.cdvtc.news.dao.impl.AdminDaoImpl;
import com.cdvtc.news.model.Admin;
import com.cdvtc.news.service.AdminService;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public Admin getAdminById(int adminId) {
        return adminDao.getAdminById(adminId);
    }

    @Override
    public Admin login(String account, String password) {
        return adminDao.login(account, password);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminDao.getAllAdmins();
    }

    @Override
    public boolean add(Admin admin) {
        return adminDao.add(admin);
    }

    @Override
    public void update(Admin admin) {
        adminDao.update(admin);
    }

    @Override
    public void delete(int adminId) {
        adminDao.delete(adminId);
    }
}
