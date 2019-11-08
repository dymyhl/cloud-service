package com.puvar.cloudservice.dao.test;

import com.puvar.cloudservice.domain.User;

import java.util.List;

public interface HelloTestMapper {

    List<User> helloList();

    int addUser(User user);

    int updateUser(User user);
}
