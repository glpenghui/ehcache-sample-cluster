package com.github.sbanal.ehcache.ws;

import com.github.sbanal.ehcache.dao.UserDao;
import com.github.sbanal.ehcache.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;


@RestController
public class UserRestController {

    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/users/{userId}",method = RequestMethod.GET)
    public User getUser(@PathVariable("userId") Integer userId) {
        log.info("userdao=" + userDao);
        return userDao.getUser(userId);
    }

    @RequestMapping(value = "/users/{userId}",method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("userId") Integer userId) {
        userDao.delete(userId);
    }

    @RequestMapping(value = "/users/{userId}",method = RequestMethod.PUT)
    public void updateUser(@PathVariable("userId") Integer userId, @RequestBody User user) {
        userDao.update(userId, user);
    }

}
