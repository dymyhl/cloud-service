package com.puvar.cloudservice.service.springcloud;

import com.puvar.cloudservice.domain.User;

import java.util.List;

public interface HelloService {

    List<User> helloList();

    List<User> helloTestList();

    boolean addUser(User user) throws Exception;

    boolean updateUser(User user);
}
