package com.github.sbanal.ehcache.dao;

import com.github.sbanal.ehcache.model.User;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class UserDaoImpl implements UserDao {

    static Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getCanonicalName());

    private final Map<Integer, User> userData;

    public UserDaoImpl() {
        this.userData = new HashMap<Integer, User>();
        for(int i=0;i<10;i++){
            this.userData.put(i, new User(i, "Test 1"));
        }
    }

    @Cacheable("userCache")
    public User getUser(int id) {
        if(userData.containsKey(id)) {
            LOGGER.info("Retrieve from db " + id);
            return userData.get(id);
        } else {
            throw new UserNotFoundException("User " + id + " not found!");
        }
    }

    @CachePut(value="userCache", key="#id")
    public void update(int id, User user) {
        LOGGER.info("Update from db " + id + " name=" + user.getName());
        if(userData.containsKey(id)) {
            userData.get(id).setName(user.getName());
        } else {
            throw new UserNotFoundException("User " + id + " not found!");
        }
    }

    @CacheEvict("userCache")
    public void delete(int id) {
        if(userData.containsKey(id)) {
            User user = userData.remove(id);
            LOGGER.info("Delete from db " + user);
        } else {
            throw new UserNotFoundException("User " + id + " not found!");
        }
    }

}

