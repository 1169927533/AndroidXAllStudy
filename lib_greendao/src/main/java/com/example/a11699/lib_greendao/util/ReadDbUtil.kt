package com.example.a11699.lib_greendao.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.a11699.lib_greendao.MyOpenHelper
import com.example.a11699.lib_greendao.db.ChatBean
import com.example.a11699.lib_greendao.db.ChatBeanDao
import com.example.a11699.lib_greendao.db.DaoMaster
import com.example.a11699.lib_greendao.db.DaoSession
import org.greenrobot.greendao.query.Query


/**
 *Create time 2020/6/20
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
        openHelper =MyOpenHelper(context, dbName, null)
    }

    /**
     * 获取可读数据库
     */
    private val readableDatabase: SQLiteDatabase by lazy {
        if (openHelper == null) {
            openHelper =MyOpenHelper(context, dbName, null)
        }
        openHelper!!.readableDatabase
    }

    /**
     * 获取可写数据库
     */
    private val writableDatabase: SQLiteDatabase by lazy {
        if (openHelper == null) {
            openHelper =MyOpenHelper(context, dbName, null)
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
     * 查询数据
     */
    fun getChatInformation(receivedId: String, sendId: String): MutableList<ChatBean> {
        val daoMaster: DaoMaster = DaoMaster(readableDatabase)
        val daoSession: DaoSession = daoMaster.newSession()
        val chatBeanDao: ChatBeanDao = daoSession.chatBeanDao
    /*    val qb = chatBeanDao.queryBuilder()
        qb.where(ChatBeanDao.Properties.Receiveid.eq(sendId), ChatBeanDao.Properties.Sendid.eq(receivedId))*/
        val qb = chatBeanDao.queryRawCreate("WHERE (sandid = ? and receiveid= ? ) or (receiveid = ? and sandid= ? )", receivedId, sendId, receivedId, sendId)

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