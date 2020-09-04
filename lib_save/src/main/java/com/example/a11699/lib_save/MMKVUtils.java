package com.example.a11699.lib_save;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;

import androidx.collection.SimpleArrayMap;

import com.tencent.mmkv.MMKV;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Create time 2020/9/2
 * Create Yu
 * description:
 */


/**
 * @author lishaojie
 * https://github.com/Tencent/MMKV
 */
public class MMKVUtils implements CacheInterface {
    private static SimpleArrayMap<String, MMKVUtils> MMKV_MAP = new SimpleArrayMap<>();
    private MMKV mmkv = null;
    private String mmkvName = null;
    private String mmkvDir = null;

    public static CacheInterface get(String mkkvName, Context context) {
        MMKVUtils mmkvUtils = MMKV_MAP.get(mkkvName);
        if (mmkvUtils == null) {
            synchronized (MMKVUtils.class) {
                mmkvUtils = new MMKVUtils(mkkvName, context);
                MMKV_MAP.put(mkkvName, mmkvUtils);
            }
        }
        return mmkvUtils;
    }


    private MMKVUtils(String mkkvName, Context context) {
        this(mkkvName, Context.MODE_PRIVATE, context);
    }

    private MMKVUtils(String mmkvName, int mode, Context context) {
        mmkvDir = MMKV.initialize(context);
        this.mmkvName = mmkvName;
        this.mmkv = MMKV.mmkvWithID(mmkvName, mode);

    }

    @Override
    public void saveData(String key, String value) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, String value, boolean commit) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, int value) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, int value, boolean commit) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, boolean value) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, boolean value, boolean commit) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, long value) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, long value, boolean commit) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, float value) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, float value, boolean commit) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, Set<String> value) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, Set<String> value, boolean commit) {
        mmkv.encode(key, value);
    }

    @Override
    public void saveData(String key, Parcelable parcelable) {
        mmkv.encode(key, parcelable);
    }

    @Override
    public String readString(String key) {
        return mmkv.decodeString(key);
    }

    @Override
    public String readString(String key, String defaultValue) {
        return mmkv.decodeString(key, defaultValue);
    }

    @Override
    public int readInt(String key) {
        return mmkv.decodeInt(key);
    }

    @Override
    public int readInt(String key, int defaultValue) {
        return mmkv.decodeInt(key, defaultValue);
    }

    @Override
    public boolean readBoolean(String key) {
        return mmkv.decodeBool(key);
    }

    @Override
    public boolean readBoolean(String key, boolean defaultValue) {
        return mmkv.decodeBool(key, defaultValue);
    }

    @Override
    public long readLong(String key) {
        return mmkv.decodeLong(key);
    }

    @Override
    public long readLong(String key, long defaultValue) {
        return mmkv.decodeLong(key, defaultValue);
    }

    @Override
    public float readFloat(String key) {
        return mmkv.decodeFloat(key);
    }

    @Override
    public float readFloat(String key, float defaultValue) {
        return mmkv.decodeFloat(key, defaultValue);
    }

    @Override
    public HashSet<String> readSetString(String key) {
        return new HashSet<>(mmkv.decodeStringSet(key));
    }

    @Override
    public HashSet<String> readSetString(String key, Set<String> defaultValue) {
        return new HashSet<>(mmkv.decodeStringSet(key, defaultValue));
    }

    @Override
    public <T extends Parcelable> T readParcelable(String key, Class<T> tClass) {
        return mmkv.decodeParcelable(key, tClass);
    }


    @Override
    public boolean contains(String key) {
        return mmkv.contains(key);
    }

    @Override
    public void remove(String key) {
        mmkv.remove(key);
    }

    @Override
    public void remove(String key, boolean commit) {
        mmkv.remove(key);
    }

    @Override
    public void clear() {
        mmkv.clear();
    }

    @Override
    public boolean isCacheFileExists() {
        File file = new File(mmkvDir);
        if (file != null) {
            return file.exists();
        }
        return false;
    }

    @Override
    public long lastModified() {
        File file = new File(mmkvDir);
        if (file != null && file.exists()) {
            return file.lastModified();
        }
        return 0;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener, boolean regist) {
        if (regist) {
            mmkv.registerOnSharedPreferenceChangeListener(listener);
        } else {
            mmkv.unregisterOnSharedPreferenceChangeListener(listener);
        }

    }
}
