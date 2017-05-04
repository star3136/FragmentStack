package com.allen.fragmentstack;

import java.io.Serializable;

/**
 * Created by Allen on 2016/12/5.
 *
 */
public class FragmentRecord implements Serializable {
    String tag; //fragment的唯一标识
    String type; //fragment的类

    public FragmentRecord(String tag, String type) {
        this.tag = tag;
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof FragmentRecord)){
            return false;
        }
        FragmentRecord that = (FragmentRecord) obj;
        return (tag == that.tag || (tag != null && that.tag != null && tag.equals(that.tag))) &&
                (type == that.type || (type != null && that.type != null && type.equals(that.type)));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + tag == null ? 0 : tag.hashCode();
        result = result * 31 + type == null ? 0 : type.hashCode();
        return result;
    }
}
