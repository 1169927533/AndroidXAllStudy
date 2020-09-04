package com.example.a11699.comp_chat.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.a11699.comp_chat.bean.ChatBean
import com.example.a11699.comp_chat.bean.ChatBeanDao
import com.example.a11699.comp_chat.bean.DaoMaster
import com.example.a11699.comp_chat.bean.DaoSession
import java.util.ArrayList

/**
 *Create time 2020/6/24
 *Create Yu
 *description:
 */
class ReadDbUtil private constructor(context: Context) {
    private var openHelper: MyOpenHelper? = null
    private var context: Context = context
    private var dbName = "char.db"

    companion object {
        fun instance(context: Context): ReadDbUtil {
            return ReadDbUtil(context)
        }
    }

    init {
        openHelper = MyOpenHelper(context, dbName, null)
    }

    /**
     * 获取可读数据库
     */
    private val readableDatabase: SQLiteDatabase by lazy {
        if (openHelper == null) {
            openHelper = MyOpenHelper(context, dbName, null)
        }
        openHelper!!.readableDatabase
    }

    /**
     * 获取可写数据库
     */
    private val writableDatabase: SQLiteDatabase by lazy {
        if (openHelper == null) {
            openHelper = MyOpenHelper(context, dbName, null)
        }
        openHelper!!.writableDatabase
    }

    /**
     * 存数据
     */
    fun inseartChat(chatBean: ChatBean) {
        val daoMaster: DaoMaster = DaoMaster(writableDatabase)
        val daoSession: DaoSession = daoMaster.newSession()
        val chatBeanDao: ChatBeanDao = daoSession.chatBeanDao
        chatBeanDao.insert(chatBean)
    }

    /**
     * 查询和某人的全部聊天数据
     */
    fun getChatInformation(receivedId: String, sendId: String): MutableList<ChatBean>? {
        val daoMaster: DaoMaster = DaoMaster(readableDatabase)
        val daoSession: DaoSession = daoMaster.newSession()
        val chatBeanDao: ChatBeanDao = daoSession.chatBeanDao

        val qb = chatBeanDao.queryRawCreate("WHERE (sendid = ? and receiveid= ? ) or (receiveid = ? and sendid= ? )", receivedId, sendId, receivedId, sendId)

        return qb.list()
    }

    /**
     * 查询全部联系人
     */
    fun getChatPeople(sendId: String): MutableList<ChatBean>? {
        val daoMaster: DaoMaster = DaoMaster(readableDatabase)
        val daoSession: DaoSession = daoMaster.newSession()
        val chatBeanDao: ChatBeanDao = daoSession.chatBeanDao
        val qb = chatBeanDao.queryRawCreate("WHERE sendid = ? GROUP BY sendid , receiveid", sendId)
        return qb.list()
    }

    /**
     * 清除所有表单数据
     */
    fun clearAllData() {
        val daoMaster = DaoMaster(readableDatabase)
        val daoSession = daoMaster.newSession()
        DaoMaster.dropAllTables(daoSession.database, true)
        DaoMaster.createAllTables(daoMaster.database, true)

    }

}