package com.allen.fragmentstack;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Allen on 2016/12/5.
 */

public class FragmentIntent implements Parcelable {
    public static final int FLAG_SINGLE_TOP = 1;
    public static final int FLAG_SINGLE_TASK = 1 << 1;
    public static final int FLAG_SINGLE_INSTANCE = 1 << 2;
    public static final int FLAG_STANDED = 1 << 3;

    public static final int FLAG_CLEAR_ALL = 1 << 4; //清除任务栈标志

    private static final int FLAG_LAUNCH_MASK = (1 << 4) - 1;
    private int flags = FLAG_STANDED;
    private String taskAffinity; //任务栈名
    private Class<? extends RootFragment> cls;
    private Bundle args = new Bundle();
    private String action;

    public FragmentIntent(Class<? extends RootFragment> cls) {
        this(cls, FLAG_STANDED);
    }

    public FragmentIntent(Class<? extends RootFragment> cls, int launchFlag) {
        this(cls, launchFlag, null);
    }

    public FragmentIntent(Class<? extends RootFragment> cls, int launchFlag, String taskAffinity) {
        this.cls = cls;
        this.flags = launchFlag;
        this.taskAffinity = taskAffinity;
    }

    public String[] getStringArray(String key) {
        return args.getStringArray(key);
    }

    public Bundle getBundle(String key) {
        return args.getBundle(key);
    }

    public byte getByte(String key) {
        return args.getByte(key);
    }

    public Byte getByte(String key, byte defaultValue) {
        return args.getByte(key, defaultValue);
    }

    public byte[] getByteArray(String key) {
        return args.getByteArray(key);
    }

    public char getChar(String key) {
        return args.getChar(key);
    }

    public char getChar(String key, char defaultValue) {
        return args.getChar(key, defaultValue);
    }

    public char[] getCharArray(String key) {
        return args.getCharArray(key);
    }

    public CharSequence getCharSequence(String key) {
        return args.getCharSequence(key);
    }

    public CharSequence getCharSequence(String key, CharSequence defaultValue) {
        return args.getCharSequence(key, defaultValue);
    }

    public CharSequence[] getCharSequenceArray(String key) {
        return args.getCharSequenceArray(key);
    }

    public ArrayList<CharSequence> getCharSequenceArrayList(String key) {
        return args.getCharSequenceArrayList(key);
    }

    public ClassLoader getClassLoader() {
        return args.getClassLoader();
    }

    public float getFloat(String key) {
        return args.getFloat(key);
    }

    public float getFloat(String key, float defaultValue) {
        return args.getFloat(key, defaultValue);
    }

    public float[] getFloatArray(String key) {
        return args.getFloatArray(key);
    }

    public ArrayList<Integer> getIntegerArrayList(String key) {
        return args.getIntegerArrayList(key);
    }

    public <T extends Parcelable> T getParcelable(String key) {
        return args.getParcelable(key);
    }

    public Parcelable[] getParcelableArray(String key) {
        return args.getParcelableArray(key);
    }

    public <T extends Parcelable> ArrayList<T> getParcelableArrayList(String key) {
        return args.getParcelableArrayList(key);
    }

    public Serializable getSerializable(String key) {
        return args.getSerializable(key);
    }

    public short getShort(String key) {
        return args.getShort(key);
    }

    public short getShort(String key, short defaultValue) {
        return args.getShort(key, defaultValue);
    }

    public short[] getShortArray(String key) {
        return args.getShortArray(key);
    }

    public <T extends Parcelable> SparseArray<T> getSparseParcelableArray(String key) {
        return args.getSparseParcelableArray(key);
    }

    public ArrayList<String> getStringArrayList(String key) {
        return args.getStringArrayList(key);
    }

    public void putAll(Bundle bundle) {
        args.putAll(bundle);
    }

    public void putBundle(String key, Bundle value) {
        args.putBundle(key, value);
    }

    public void putByte(String key, byte value) {
        args.putByte(key, value);
    }

    public void putByteArray(String key, byte[] value) {
        args.putByteArray(key, value);
    }

    public void putChar(String key, char value) {
        args.putChar(key, value);
    }

    public void putCharArray(String key, char[] value) {
        args.putCharArray(key, value);
    }

    public void putCharSequence(String key, CharSequence value) {
        args.putCharSequence(key, value);
    }

    public void putCharSequenceArray(String key, CharSequence[] value) {
        args.putCharSequenceArray(key, value);
    }

    public void putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        args.putCharSequenceArrayList(key, value);
    }

    public void putFloat(String key, float value) {
        args.putFloat(key, value);
    }

    public void putFloatArray(String key, float[] value) {
        args.putFloatArray(key, value);
    }

    public void putIntegerArrayList(String key, ArrayList<Integer> value) {
        args.putIntegerArrayList(key, value);
    }

    public void putParcelable(String key, Parcelable value) {
        args.putParcelable(key, value);
    }

    public void putParcelableArray(String key, Parcelable[] value) {
        args.putParcelableArray(key, value);
    }

    public void putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
        args.putParcelableArrayList(key, value);
    }

    public void putSerializable(String key, Serializable value) {
        args.putSerializable(key, value);
    }

    public void putShort(String key, short value) {
        args.putShort(key, value);
    }

    public void putShortArray(String key, short[] value) {
        args.putShortArray(key, value);
    }

    public void putSparseParcelableArray(String key, SparseArray<? extends Parcelable> value) {
        args.putSparseParcelableArray(key, value);
    }

    public void putStringArrayList(String key, ArrayList<String> value) {
        args.putStringArrayList(key, value);
    }

    public boolean containsKey(String key) {
        return args.containsKey(key);
    }

    public Object get(String key) {
        return args.get(key);
    }

    public boolean getBoolean(String key) {
        return args.getBoolean(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return args.getBoolean(key, defaultValue);
    }

    public boolean[] getBooleanArray(String key) {
        return args.getBooleanArray(key);
    }

    public double getDouble(String key) {
        return args.getDouble(key);
    }

    public double getDouble(String key, double defaultValue) {
        return args.getDouble(key, defaultValue);
    }

    public double[] getDoubleArray(String key) {
        return args.getDoubleArray(key);
    }

    public int getInt(String key) {
        return args.getInt(key);
    }

    public int getInt(String key, int defaultValue) {
        return args.getInt(key, defaultValue);
    }

    public int[] getIntArray(String key) {
        return args.getIntArray(key);
    }

    public long getLong(String key) {
        return args.getLong(key);
    }

    public long getLong(String key, long defaultValue) {
        return args.getLong(key, defaultValue);
    }

    public long[] getLongArray(String key) {
        return args.getLongArray(key);
    }

    public String getString(String key) {
        return args.getString(key);
    }

    public String getString(String key, String defaultValue) {
        return args.getString(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        args.putBoolean(key, value);
    }

    public void putBooleanArray(String key, boolean[] value) {
        args.putBooleanArray(key, value);
    }

    public void putDouble(String key, double value) {
        args.putDouble(key, value);
    }

    public void putDoubleArray(String key, double[] value) {
        args.putDoubleArray(key, value);
    }

    public void putInt(String key, int value) {
        args.putInt(key, value);
    }

    public void putIntArray(String key, int[] value) {
        args.putIntArray(key, value);
    }

    public void putLong(String key, long value) {
        args.putLong(key, value);
    }

    public void putLongArray(String key, long[] value) {
        args.putLongArray(key, value);
    }

    public void putString(String key, String value) {
        args.putString(key, value);
    }

    public void putStringArray(String key, String[] value) {
        args.putStringArray(key, value);
    }

    public void remove(String key) {
        args.remove(key);
    }

    /**
     * 设置启动模式
     * @param flag
     */
    public void setLaunchFlag(int flag){
        flags = flags & (~FLAG_LAUNCH_MASK) | flag;
    }

    public void setTaskAffinity(String taskAffinity) {
        this.taskAffinity = taskAffinity;
    }

    public int getLaunchFlag() {
        return flags & FLAG_LAUNCH_MASK;
    }

    public void setClearFlag(int flag){
        flags = flags | (flag & FLAG_CLEAR_ALL);
    }

    public int getClearFlag(){
        return flags & FLAG_CLEAR_ALL;
    }

    public String getTaskAffinity() {
        return taskAffinity;
    }

    public Class<? extends RootFragment> getComponet() {
        return cls;
    }

    public Bundle getArguments(){
        return args;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "FragmentIntent{" +
                "args=" + args +
                ", flags=" + flags +
                ", taskAffinity='" + taskAffinity + '\'' +
                ", cls=" + cls +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.flags);
        dest.writeString(this.taskAffinity);
        dest.writeSerializable(this.cls);
        dest.writeBundle(this.args);
        dest.writeString(this.action);
    }

    protected FragmentIntent(Parcel in) {
        this.flags = in.readInt();
        this.taskAffinity = in.readString();
        this.cls = (Class<? extends RootFragment>) in.readSerializable();
        this.args = in.readBundle();
        this.action = in.readString();
    }

    public static final Creator<FragmentIntent> CREATOR = new Creator<FragmentIntent>() {
        @Override
        public FragmentIntent createFromParcel(Parcel source) {
            return new FragmentIntent(source);
        }

        @Override
        public FragmentIntent[] newArray(int size) {
            return new FragmentIntent[size];
        }
    };
}