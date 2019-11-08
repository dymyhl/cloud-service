package com.puvar.cloudservice.lambda;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

/***
 * lambda 测试
 * @Auther: dingyuanmeng
 * @Date: 2019/10/31
 * @version : 1.0
 */
@Slf4j
public class LambdaTest {
    public static void main(String[] args) {
        //AloneList();

        handleList();
    }

    public static void handleList(){
        List<String> list = Lists.newArrayList();
//        list.add("1");
//        list.add("1");
//        list.add("1");
        String join = String.join(",", list);
        System.out.println(join);
    }

    public static void AloneList() {
        List<User> list = Lists.newArrayList();
        list.add(new User("1", "10", "5"));
        list.add(new User("2", "8", "4"));
        list.add(new User("3", "6", "2"));
        list.add(new User("4", "5", "3"));
        Map<String, Object> map = Maps.newHashMap();
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        map.put("2","4");
        Set<String> strings = map.keySet();
        System.out.println(strings);
    }
}
