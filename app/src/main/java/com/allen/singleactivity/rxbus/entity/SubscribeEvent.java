package com.allen.singleactivity.rxbus.entity;

/**
 * Created by Allen on 2016/12/2.
 */

public class SubscribeEvent {
    private String tag;
    private Object data;
    private Boolean sticky;

    public SubscribeEvent(String tag, Object data, Boolean sticky) {
        this.tag = tag;
        this.data = data;
        this.sticky = sticky;
    }

    public SubscribeEvent(String tag, Object data) {
        this.tag = tag;
        this.data = data;
        sticky = false;
    }

    public String getTag() {
        return tag;
    }

    public Object getData() {
        return data;
    }

    public Boolean getSticky() {
        return sticky;
    }
}
