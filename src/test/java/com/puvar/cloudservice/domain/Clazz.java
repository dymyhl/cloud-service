package com.puvar.cloudservice.domain;

import lombok.Data;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/9/24
 * @version : 1.0
 */
@Data
public class Clazz extends Parent{

    /**
     * 属性
     */
    private String name;
    protected String sex;
    String source;
    public String type;

    /**
     * 构造方法
     */
    private Clazz(String name) {
        this.name = name;
    }

    protected Clazz() {
    }

    Clazz(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public Clazz(String name, String sex, String source) {
        this.name = name;
        this.sex = sex;
        this.source = source;
    }

    /**
     * 方法
     */
    private String hello() {
        return "hello private";
    }

    protected String hello(String hi) {
        return "hello protected " + hi;
    }

    String hello(String hi, String hello) {
        return "hello default " + hi + " and " + hello;
    }

    public String hello(String hi, String hello, String world) {
        return "hello public " + hi + " and " + hello + " and " + world;
    }

}
