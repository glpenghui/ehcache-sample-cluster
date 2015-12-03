package com.github.sbanal.ehcache.dao;

import com.github.sbanal.ehcache.model.User;

public interface UserDao {

    User getUser(int id);
    void update(int id, User user);
    void delete(int id);

}
