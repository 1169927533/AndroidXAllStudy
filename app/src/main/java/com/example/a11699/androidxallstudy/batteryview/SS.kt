package com.example.a11699.androidxallstudy.batteryview

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * @Author Yu
 * @Date 2021/2/1 14:09
 * @Description TODO
 */
class SS:ContentProvider() {
    override fun onCreate(): Boolean {

        return true
    }

    override fun query(uri: Uri, strings: Array<String?>?, s: String?, strings1: Array<String?>?, s1: String?): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String?>?): Int {
        return 0
    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String?>?): Int {
        return 0
    }
}