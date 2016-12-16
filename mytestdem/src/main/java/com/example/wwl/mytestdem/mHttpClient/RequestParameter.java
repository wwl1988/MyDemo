package com.example.wwl.mytestdem.mHttpClient;

import java.io.Serializable;

/**
 * Created by wwl on 2016/12/14.
 */

public class RequestParameter implements Serializable, Comparable<Object> {

    private String name;//键
    private String value;//值

    public RequestParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * String.compareTo(String) 方法是比较两个字符串中每个ascii值
     * 即参与比较的两个字符串如果首字符相同，则比较下一个字符，直到有不同的为止，返回该不同的字符的ascii码差值，
     * 如果两个字符串不一样长，可以参与比较的字符又完全一样，则返回两个字符串的长度差值
     * 若a="ab",b="a",输出1；
     * 若a="abcdef",b="a"输出5；
     * 若a="abcdef",b="abc"输出3；
     * 若a="abcdef",b="ace"输出-1；
     *
     * @param another
     * @return
     */
    @Override
    public int compareTo(Object another) {
        int compared;
        RequestParameter parameter = (RequestParameter) another;
        compared = name.compareTo(parameter.name);
        if (compared == 0) {
            compared = value.compareTo(parameter.value);
        }
        return compared;
    }

    /**
     * 对象比较是否相同
     *
     * @param obj
     * @return
     */
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof RequestParameter) {
            final RequestParameter parameter = (RequestParameter) obj;
            return name.equals(parameter.name) && value.equals(parameter.value);
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
