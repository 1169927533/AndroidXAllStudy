package com.example.a11699.lib_save;

import android.content.SharedPreferences;
import android.os.Parcelable;

import java.util.HashSet;
import java.util.Set;

/**
 * Create time 2020/9/2
 * Create Yu
 * description:
 */
public interface CacheInterface {
    public void saveData(String key, String value);

    public void saveData(String key, String value, boolean commit);

    public void saveData(String key, int value);

    public void saveData(String key, int value, boolean commit);

    public void saveData(String key, boolean value);

    public void saveData(String key, boolean value, boolean commit);

    public void saveData(String key, long value);

    public void saveData(String key, long value, boolean commit);

    public void saveData(String key, float value);

    public void saveData(String key, float value, boolean commit);

    public void saveData(String key, Set<String> value);

    public void saveData(String key, Set<String> value, boolean commit);

    public void saveData(String key, Parcelable parcelable);

    public String readString(String key);

    public String readString(String key, String defaultValue);

    public int readInt(String key);

    public int readInt(String key, int defaultValue);

    public boolean readBoolean(String key);

    public boolean readBoolean(String key, boolean defaultValue);

    public long readLong(String key);

    public long readLong(String key, long defaultValue);

    public float readFloat(String key);

    public float readFloat(String key, float defaultValue);

    public HashSet<String> readSetString(String key);

    public HashSet<String> readSetString(String key, Set<String> defaultValue);

    public <T extends Parcelable> T readParcelable(String key, Class<T> tClass);

    public boolean contains(String key);

    public void remove(String key);

    public void remove(String key, boolean commit);

    public void clear();

    public boolean isCacheFileExists();

    public long lastModified();

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener, boolean regist) throws IllegalAccessException;

}

