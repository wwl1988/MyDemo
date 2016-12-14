package com.example.wwl.mytestdem.handlerMN;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLoacal简单实现
 * Created by wwl on 2016/12/13.
 *
 */
public class ThreadLocal<T> {

    private Map<Thread, T> map;

    public ThreadLocal() {
        map = new HashMap<>();
    }

    public void set(T obj) {
        map.put(Thread.currentThread(), obj);
    }

    public T get() {
        return map.get(Thread.currentThread());
    }

}
