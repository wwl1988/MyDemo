package com.example.wwl.myrecyclerviewdemo;

/**
 * javaBean
 * Created by wwl on 2016/11/20.
 */
public class TestBean {

    private boolean isSelected;
    private String name;

    public TestBean(String name) {
        this.name = name;
    }

    public TestBean(boolean isSelected, String name) {
        this.isSelected = isSelected;
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
