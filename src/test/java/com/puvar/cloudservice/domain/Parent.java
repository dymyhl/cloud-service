package com.puvar.cloudservice.domain;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/9/24
 * @version : 1.0
 */
public class Parent {
    /**
     * 属性
     */
    private String parentName;
    protected String parentSex;
    String parentSource;
    public String parentType;

    /**
     * 构造方法
     */
    private Parent(String name) {
        this.parentName = name;
    }

    protected Parent() {
    }

    Parent(String name, String sex) {
        this.parentName = name;
        this.parentSex = sex;
    }

    public Parent(String name, String sex, String source) {
        this.parentName = name;
        this.parentSex = sex;
        this.parentSource = source;
    }

    /**
     * 方法
     */
    private String helloParent() {
        return "helloParent private";
    }

    protected String helloParent(String hi) {
        return "helloParent protected " + hi;
    }

    String helloParent(String hi, String hello) {
        return "helloParent default " + hi + " and " + hello;
    }

    public String helloParent(String hi, String hello, String world) {
        return "helloParent public " + hi + " and " + hello + " and " + world;
    }
}
