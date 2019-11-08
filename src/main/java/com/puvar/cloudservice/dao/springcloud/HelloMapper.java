package com.puvar.cloudservice.dao.springcloud;

import com.puvar.cloudservice.domain.User;

import java.util.List;

public interface HelloMapper {

    List<User> helloList();

    int addUser(User user);

    int updateUser(User user);
}
