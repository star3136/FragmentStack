package com.allen.fragmentstack;

import android.support.v4.app.Fragment;

/**
 * Created by Allen on 2016/12/5.
 */

public class KeyGenerator {
    public String getTag(RootFragment fragment){
        return fragment.hashCode() + "";
    }

    public String getType(Object object){
        if(object instanceof Class){
            return ((Class) object).getName();
        }
        return object.getClass().getName();
    }
}
