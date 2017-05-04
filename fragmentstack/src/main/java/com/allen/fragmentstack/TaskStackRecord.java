package com.allen.fragmentstack;

import java.io.Serializable;

/**
 * Created by Allen on 2016/12/6.
 * 任务栈记录
 */

public class TaskStackRecord implements Serializable{
    /**
     * 任务栈的启动模式
     * see {@link FragmentIntent}
     */
    int stackMode;
    /**
     * 任务栈名
     */
    String taskStackAffinity;

    public TaskStackRecord(int stackMode, String taskStackAffinity) {
        this.stackMode = stackMode;
        this.taskStackAffinity = taskStackAffinity;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof TaskStackRecord)){
            return false;
        }
        TaskStackRecord that = (TaskStackRecord) obj;
        return taskStackAffinity == obj || (taskStackAffinity != null && that.taskStackAffinity != null && taskStackAffinity.equals(that.taskStackAffinity));
    }

    @Override
    public int hashCode() {
        int result = 17;
//        result = result * 31 + stackMode;
        result = result * 31 + taskStackAffinity == null ? 0 : taskStackAffinity.hashCode();
        return result;
    }
}
