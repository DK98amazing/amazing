package com;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Reflect {
    private int count;

    public Reflect(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private void getAll() {}

    public static void main(String[] args) {
        try {
            Class reflectClass = Class.forName("com.Reflect");
            Constructor constructor =  reflectClass.getConstructor(int.class);
            Reflect reflect = (Reflect) constructor.newInstance(22);
//            Reflect reflect = (Reflect) reflectClass.newInstance();
            Method[] methods = reflectClass.getMethods();
            Arrays.stream(methods).forEach(System.out::println);
            Method method = reflectClass.getDeclaredMethod("setCount", int.class);
            method.invoke(reflect, 33);
            System.out.println(reflect.getCount());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
