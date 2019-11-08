package com.puvar.cloudservice.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/***
 * 反射测试类
 * @Auther: dingyuanmeng
 * @Date: 2019/9/24
 * @version : 1.0
 */
public class ClassTest {
    public static void main(String[] args) throws IllegalAccessException {
        // 属性
        Clazz clazz = new Clazz();
        clazz.setName("dym");
        clazz.setSex("男");
        clazz.setSource("璞华");
        clazz.setType("1");
        // 本类
        Class clazzClass = clazz.getClass();
        // 获取本类和父类public修饰属性
        Field[] fields = clazzClass.getFields();
        for (Field field : fields) {
            System.out.println("field=" + field.getName() + "==" + field.get(clazz));
        }
        // 获取所有本类属性
        Field[] declaredFields = clazzClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            System.out.println("declaredField=" + declaredField.get(clazz));
        }
        // 方法
        Method[] methods = clazzClass.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            System.out.println("方法名是：" + method.getName());
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                String name = parameter.getName();
                System.out.println("参数名是：" + name);
            }
        }
        // 构造器
        Constructor[] constructors = clazzClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            constructor.setAccessible(true);
            int modifiers = constructor.getModifiers();
            System.out.println(modifiers);
        }
    }
}
